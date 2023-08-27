package adt;

import java.util.Iterator;

import utility.NoNoArgConstructorException;

public class HashMap<K extends Comparable<K>, V> implements MapInterface<K, V> {

    private ArrayList<K> keys;
    

    @Override
    public void put(K key, V value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public V get(K key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public K[] getKeys(Class<K> clazz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKeys'");
    }

    /**
     * Can be it's own ADT but I only use it in this hashmap to work as (buckets, for collision)
     * So this is gonna be a nerfed version of BinarySearchTree (no reordering)
     */
    @SuppressWarnings("unused")
    public class BinarySearchTree implements Iterable<BinarySearchTree.Node> {

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

        public Node find(K key) {
            for (Node n: this) {
                if (key.equals(n.getKey()))
                    return n;
            }
            return null;
        }

        // will be used recursively
        private Node delete(Node root, K key) {
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

    }
    
}
