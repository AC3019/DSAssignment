package adt;

import java.lang.reflect.Array;
import java.util.Iterator;

import utility.NoNoArgConstructorException;

@SuppressWarnings("unchecked")
public class HashMap<K extends Comparable<K>, V> 
    implements MapInterface<K, V>, Iterable<HashMap<K, V>.Node> {

    private ArrayList<K> keys;
    private BinarySearchTree[] buckets;
    private int bucketNum;
    private static int DEFAULT_BUCKET_NUM = 10;

    // util to calculate hash of the key specific to hashmap size
    private int getHashModulo(K key) {
        return key.hashCode() % this.bucketNum;
    }

    public HashMap() { this(DEFAULT_BUCKET_NUM); }

    public HashMap(int bucketNum) {
        this.bucketNum = bucketNum;
        this.buckets = (BinarySearchTree[]) Array.newInstance(BinarySearchTree.class, this.bucketNum);
        for (int i = 0; i < this.bucketNum; i++) {
            this.buckets[i] = new BinarySearchTree();
        }
        this.keys = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        int position = getHashModulo(key);
        System.out.println(position);

        // TODO: implement rehash for better performance
        this.buckets[position].insert(key, value);
        this.keys.insert(key);
    }

    @Override
    public V get(K key) {
        int position = getHashModulo(key);

        // direct search is faster than searching through the array of keys
        return this.buckets[position].find(key).getValue();
    }

    @Override
    public boolean set(K key, V value) {
        if (!this.keys.contains(key)) {
            return false;
        }

        int position = this.getHashModulo(key);
        this.buckets[position].find(key).setValue(value);

        return true;
    }

    @Override
    public V remove(K key) {
        if (!this.keys.contains(key)) {
            return null;
        }
        
        int position = this.getHashModulo(key);
        return this.buckets[position].delete(key).getValue();
    }

    @Override
    public K[] getKeys(Class<K> clazz) {
        return this.keys.toArray(clazz);
    }

    // we store key value pairs in form of Nodes
    public class Node {
        private Node left;
        private Node right;
        private K key;
        private V value;

        // no no arg constructor
        Node() throws NoNoArgConstructorException {
            throw new NoNoArgConstructorException(this.getClass());
        }

        Node(K key, V value) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.value = value;
        }

        Node getLeft() {
            return this.left;
        }

        Node getRight() {
            return this.right;
        }

        K getKey() {
            return this.key;
        }

        V getValue() {
            return this.value;
        }

        void setLeft(Node left) {
            this.left = left;
        }

        void setRight(Node right) {
            this.right = right;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node clone() {
            return new Node(this.key, this.value);
        }

        @Override
        public String toString() {
            return "K: " + this.key + " V: " + this.value;
        }

    }

    /**
     * Can be it's own ADT but I only use it in this hashmap to work as (buckets, for collision)
     * So this is gonna be a nerfed version of BinarySearchTree (no reordering)
     */
    class BinarySearchTree implements Iterable<Node> {

        private Node root;
        private int numberOfElements;

        public BinarySearchTree() { this.root = null; }

        public BinarySearchTree(K key, V value) {
            this.root = new Node(key, value);
        }

        public int getNumberOfElements() {
            return this.numberOfElements;
        }

        // will be used recursively to find the position of the new node and insert it there
        private Node insert(Node root, K key, V value) throws Exception {
            if (root == null) {
                // reach base case, increment numberOfElements
                this.numberOfElements++;
                return new Node(key, value);
            }
            
            if (key.compareTo(root.getKey()) < 0) {
                root.setLeft(this.insert(root.getLeft(), key, value));
            } else if (key.compareTo(root.getKey()) > 0) {
                root.setRight(this.insert(root.getRight(), key, value));
            } else {
                // if key is equal then violate no duplicate contract d
                // only used once no need create specific class (gua)
                throw new Exception("Cannot have duplicate keys");
            }

            // root is inserted recursively, by the time it reach here, it has already been done
            return root;
        }

        public void insert(K key, V value) { 
            try {
                this.root = this.insert(this.root, key, value); 
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private Node inOrderSuccessor(Node root) {
            if (root.getLeft() == null)
                return root;
            return inOrderSuccessor(root.getLeft());
        }

        public Node find(Node root, K key) {
            if (key.equals(root.getKey()))
                return root;
            else if (key.compareTo(root.getKey()) < 0)
                return this.find(root.getLeft(), key);
            else if (key.compareTo(root.getKey()) > 0)
                return this.find(root.getRight(), key);

            return null;
        }

        // returns null if key not found in the current bst
        public Node find(K key) {
            return this.find(this.root, key);
        }

        // will be used recursively
        private Node delete(Node root, K key) {
            System.out.println("Root: " + root + " Key: " + key);
            if (root == null)
                return root;
            
            // search for node to be deleted
            if (root.getKey().compareTo(key) < 0) {
                root.setRight(delete(root.getRight(), key));
            } else if (root.getKey().compareTo(key) > 0) {
                root.setLeft(delete(root.getLeft(), key));
            } else {
                // if one of the child is null, can direct assign the child become the root
                if (root.getLeft() == null) {
                    return root.getRight();
                } else if (root.getRight() == null) {
                    return root.getLeft();
                }

                // if both child not null
                // find the in order sucessor (will always be the leftmost node of the right subtree)
                Node succ = inOrderSuccessor(root.getRight());

                // copy the key value pair to this node
                root.setKey(succ.getKey());
                root.setValue(succ.getValue());

                root.right = delete(root.right, succ.getKey());
            }

            return root;
        }

        public Node delete(K key) {
            // since in delete we will set the key and value to the node, pointing to the node is not enough, we have to clone it
            Node toBeDelete = this.find(key).clone();
            this.root = this.delete(this.root, key);
            return toBeDelete;
        }

        public Itr iterator() {
            return new Itr();
        }

        // simply performs inorder search
        private class Itr implements Iterator<Node> {
            // thanks to me the arrayList can be used as a Stack as well
            private ArrayList<Node> traverseList;

            // util function to traverse
            private void moveLeft(Node current) {
                if (current == null)
                    return;
                traverseList.insert(current);
                moveLeft(current.getLeft());
            }

            Itr() {
                this.traverseList = new ArrayList<>();
                moveLeft(BinarySearchTree.this.root);
            }

            @Override
            public boolean hasNext() {
                return !traverseList.isEmpty();
            }

            @Override
            public Node next() {
                Node current = traverseList.removeBack();

                // push the right subtree
                if (current.getRight() != null)
                    moveLeft(current.getRight());
                
                return current;
            }
        }

    }

    @Override
    public Iterator<HashMap<K, V>.Node> iterator() {
        return new Itr();
    }

    // iterates through HashMap, with no particular order
    private class Itr implements Iterator<Node> {

        private int currentBucket = 0;
        private Iterator<Node> currentIterating;

        // util for advancing to the next bucket
        private void nextBucket() {
            // if already at the last bucket STAWP
            if (++this.currentBucket >= HashMap.this.bucketNum)
                return;
            this.currentIterating = HashMap.this.buckets[this.currentBucket].iterator();
        }

        // util to find the next non empty bucket
        private void findNonEmptyBucket() {
            while (
                !this.currentIterating.hasNext() && this.currentBucket < HashMap.this.bucketNum
            ) {
                this.nextBucket();
            }
        }

        Itr() {
            this.currentIterating = HashMap.this.buckets[this.currentBucket].iterator();
            // find the bucket that already has something
            this.findNonEmptyBucket();
        }

        @Override
        public boolean hasNext() {
            return 
                this.currentBucket < HashMap.this.bucketNum && 
                this.currentIterating.hasNext();
        }

        @Override
        public Node next() {
            Node res = null;

            res = this.currentIterating.next();
            
            // finished looping the current bucket, advance to next
            if (!this.currentIterating.hasNext()) 
                this.findNonEmptyBucket();

            return res;
        }
        
    }

}
