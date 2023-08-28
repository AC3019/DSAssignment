package adt;

import java.lang.reflect.Array;
import java.util.Iterator;

import utility.NoNoArgConstructorException;
import utility.NotSameInstanceException;

/**
 * @author xuanbin
 */
@SuppressWarnings("unchecked")
public class HashMap<K extends Comparable<K>, V> 
    implements MapInterface<K, V>, Iterable<HashMap<K, V>.Pair> {

    private ArrayList<K> keys;
    private BinarySearchTree<Pair>[] buckets;
    private int bucketNum;
    private static int DEFAULT_BUCKET_NUM = 10;

    // util to calculate hash of the key specific to hashmap size
    private int getHashModulo(K key) {
        return key.hashCode() % this.bucketNum;
    }

    // expands the hashmap by 2 * bucketNum, rehash everything at the same time
    private void expand() {
        BinarySearchTree<Pair>[] temp = this.buckets;

        this.bucketNum *= 2;
        this.buckets = (BinarySearchTree[]) Array.newInstance(BinarySearchTree.class, this.bucketNum);
        
        // rehash and reinsert
        for (BinarySearchTree<Pair> b: temp) {
            if (b.getNumberOfElements() == 0)
                continue;
            for (Pair p: b) {
                // recalculate the hash and put it in the new buckets
                int position = this.getHashModulo(p.getKey());
                this.buckets[position].insert(p);
            }
        }
    }

    public HashMap() { this(DEFAULT_BUCKET_NUM); }

    public HashMap(int bucketNum) {
        this.bucketNum = bucketNum;
        this.buckets = (BinarySearchTree[]) Array.newInstance(BinarySearchTree.class, this.bucketNum);
        // init all buckets
        for (int i = 0; i < this.bucketNum; i++) {
            this.buckets[i] = new BinarySearchTree<Pair>();
        }
        this.keys = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        int position = getHashModulo(key);

        // if the elements in the bucket is the same as the bucket number * 2 (since we use binary search tree to search), it is probably too crowded, need expand the bucket numbers
        // although expanding takes time it will pay off int the future during search time
        if (this.buckets[position].getNumberOfElements() >= this.bucketNum * 2) {
            this.expand();
            position = getHashModulo(key); // recalculate the new position
        }
        this.buckets[position].insert(new Pair(key, value));
        this.keys.insert(key);
    }

    @Override
    public V get(K key) {
        int position = getHashModulo(key);

        // direct search is faster than searching through the array of keys
        return this.buckets[position].find(new Pair(key, null)).getValue();
    }

    @Override
    public boolean set(K key, V value) {
        if (!this.keys.contains(key)) {
            return false;
        }

        int position = this.getHashModulo(key);
        this.buckets[position].find(new Pair(key, null)).setValue(value);

        return true;
    }

    @Override
    public V remove(K key) {
        if (!this.contains(key))
            return null;
        
        int position = this.getHashModulo(key);
        return this.buckets[position].delete(new Pair(key, null)).getValue();
    }

    @Override
    public K[] getKeys(Class<K> clazz) {
        return this.keys.toArray(clazz);
    }

    @Override
    public boolean contains(K key) {
        return this.keys.contains(key);
    }

    @Override
    public int size() {
        int size = 0;
        // the inner node type is not needed
        for (BinarySearchTree<?> bucket: this.buckets) {
            size += bucket.getNumberOfElements();
        }
        return size;
    }

    // we store key value pairs in form of Nodes
    public class Pair implements Comparable<Pair> {
        private K key;
        private V value;

        // no no arg constructor
        Pair() throws NoNoArgConstructorException {
            throw new NoNoArgConstructorException(this.getClass());
        }

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        // should not be able to cincai set the key abo the bst will mess up
        void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Pair clone() {
            return new Pair(this.key, this.value);
        }

        @Override
        public String toString() {
            return "K: " + this.key + " V: " + this.value;
        }

        @Override
        public int compareTo(HashMap<K, V>.Pair o) throws NotSameInstanceException {
            if (!(o instanceof HashMap<K, V>.Pair))
                throw new NotSameInstanceException(
                    "The instance of o (" + o.getClass().getSimpleName() + ") is not of Pair"
                );
            return this.key.compareTo(o.getKey());
        }

    }

    @Override
    public Iterator<HashMap<K, V>.Pair> iterator() {
        return new Itr();
    }

    // iterates through HashMap, with no particular order
    private class Itr implements Iterator<Pair> {

        private int currentBucket = 0;
        private Iterator<Pair> currentIterating;

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
        public Pair next() {
            Pair res = null;

            res = this.currentIterating.next();
            
            // finished looping the current bucket, advance to next
            if (!this.currentIterating.hasNext()) 
                this.findNonEmptyBucket();

            return res;
        }
        
    }

}

/**
 * Can be it's own ADT but I only use it in this hashmap to work as (buckets, for collision)
 * So this is gonna be a nerfed version of BinarySearchTree (no reordering)
 */
class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {

    private BSTNode root;
    private int numberOfElements;

    public BinarySearchTree() { this.root = null; }

    public BinarySearchTree(T val) {
        this.root = new BSTNode(val);
    }

    public int getNumberOfElements() {
        return this.numberOfElements;
    }

    // will be used recursively to find the position of the new node and insert it there
    private BSTNode insert(BSTNode root, T value) throws Exception {
        if (root == null) {
            // reach base case, increment numberOfElements
            this.numberOfElements++;
            return new BSTNode(value);
        }
        
        if (value.compareTo(root.getValue()) < 0) {
            root.setLeft(this.insert(root.getLeft(), value));
        } else if (value.compareTo(root.getValue()) > 0) {
            root.setRight(this.insert(root.getRight(), value));
        } else {
            // if key is equal then violate no duplicate contract d
            // only used once no need create specific class (gua)
            throw new Exception("Cannot have duplicate values");
        }

        // root is inserted recursively, by the time it reach here, it has already been done
        return root;
    }

    public void insert(T value) { 
        try {
            this.root = this.insert(this.root, value); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private BSTNode inOrderSuccessor(BSTNode root) {
        if (root.getLeft() == null)
            return root;
        return inOrderSuccessor(root.getLeft());
    }

    private BSTNode find(BSTNode root, T value) {
        if (value.compareTo(root.getValue()) < 0)
            return this.find(root.getLeft(), value);
        else if (value.compareTo(root.getValue()) > 0)
            return this.find(root.getRight(), value);
        else
            return root;
    }

    // returns null if key not found in the current bst
    public T find(T value) {
        return this.find(this.root, value).getValue();
    }

    // will be used recursively
    private BSTNode delete(BSTNode root, T value) {
        if (root == null)
            return root;
        
        // search for node to be deleted
        if (root.getValue().compareTo(value) < 0) {
            root.setRight(delete(root.getRight(), value));
        } else if (root.getValue().compareTo(value) > 0) {
            root.setLeft(delete(root.getLeft(), value));
        } else {
            // if one of the child is null, can direct assign the child become the root
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            }

            // if both child not null
            // find the in order sucessor (will always be the leftmost node of the right subtree)
            BSTNode succ = inOrderSuccessor(root.getRight());

            // copy the key value pair to this node
            root.setValue(succ.getValue());

            root.right = delete(root.right, succ.getValue());
        }

        return root;
    }

    public T delete(T value) {
        // since in delete we will set the key and value to the node, pointing to the node is not enough, we have to clone it
        T toBeDelete = this.find(value);
        this.root = this.delete(this.root, value);
        return toBeDelete;
    }

    public Itr iterator() {
        return new Itr();
    }

    // simply performs inorder search
    private class Itr implements Iterator<T> {
        // thanks to me the arrayList can be used as a Stack as well
        private ArrayList<BSTNode> traverseList;

        // util function to traverse
        private void moveLeft(BSTNode current) {
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
        public T next() {
            BSTNode current = traverseList.removeBack();

            // push the right subtree
            if (current.getRight() != null)
                moveLeft(current.getRight());
            
            return current.getValue();
        }
    }

    @SuppressWarnings("unused")
    private class BSTNode {
        private BSTNode left;
        private BSTNode right;
        private T value;

        BSTNode() throws NoNoArgConstructorException {
            throw new NoNoArgConstructorException(this.getClass());
        }

        BSTNode(T val) {
            this.left = this.right = null;
            this.value = val;
        }

        BSTNode getLeft() {
            return this.left;
        }

        BSTNode getRight() {
            return this.right;
        }

        T getValue() { 
            return this.value; 
        }

        void setLeft(BSTNode left) {
            this.left = left;
        }

        void setRight(BSTNode right) {
            this.right = right;
        }

        void setValue(T value) {
            this.value = value;
        }

        public BSTNode clone() {
            return new BSTNode(this.value);
        }
    }

}
