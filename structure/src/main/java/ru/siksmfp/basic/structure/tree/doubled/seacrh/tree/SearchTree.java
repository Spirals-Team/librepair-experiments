package ru.siksmfp.basic.structure.tree.doubled.seacrh.tree;

import ru.siksmfp.basic.structure.api.TreeStructure;
import ru.siksmfp.basic.structure.utils.SystemUtils;

import java.util.function.BiConsumer;

/**
 * @author Artem Karnov @date 08.12.16.
 * artem.karnov@t-systems.com
 **/
public class SearchTree<K extends Comparable<K>, V> implements TreeStructure<K, V> {
    /**
     * Model of node's tree
     */
    private class Node {
        private K key;
        private V value;
        private Node leftChild;
        private Node rightChild;
        private Node parent;
        private boolean isLeftChild;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.rightChild = null;
            this.leftChild = null;
        }

        public String toString() {
            String leftChildrenKey = leftChild == null ? "null" : leftChild.key == null ? "null" : leftChild.key.toString();
            String leftChildrenValue = leftChild == null ? "null" : leftChild.value == null ? "null" : leftChild.value.toString();
            String rightChildrenKey = rightChild == null ? "null" : rightChild.key == null ? "null" : rightChild.key.toString();
            String rightChildrenValue = rightChild == null ? "null" : rightChild.value == null ? "null" : rightChild.value.toString();

            return "Node{key " + key + ", value " + value + " isLeft " + isLeftChild
                    + ", left child (" + leftChildrenKey + ";" + leftChildrenValue + ")"
                    + "; right child (" + rightChildrenKey + ";" + rightChildrenValue + ") }";
        }
    }

    private Node root;
    private int size;

    public SearchTree() {
        root = null;
    }

    /**
     * Inserting element in tree
     *
     * @param key   key for ordering
     * @param value value for storing
     */
    @Override
    public void add(K key, V value) {
        Node nodeByKey = getNodeByKey(key);
        if (nodeByKey != null) {
            nodeByKey.value = value;
        } else {
            addNode(new Node(key, value));
        }
    }

    /**
     * Getting element by key
     *
     * @param key key for getting
     * @return value of store element, null - if element isn't in tree
     */
    @Override
    public V get(K key) {
        Node result = getNodeByKey(key);
        return result == null ? null : result.value;
    }

    /**
     * Strict inserting element in tree
     *
     * @param key   key for ordering
     * @param value value for storing
     */
    @Override
    public void strictAdd(K key, V value) {
        Node nodeByKey = getNodeByKey(key);
        if (nodeByKey != null) {
            nodeByKey.value = SystemUtils.clone(value);
        } else {
            addNode(new Node(SystemUtils.clone(key), SystemUtils.clone(value)));
        }

    }

    /**
     * Searching list's value by key
     *
     * @return true if element exists, false if doesn't
     */
    @Override
    public boolean contains(K key) {
        Node currentNode = root;
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return true;
            } else if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }
        return false;
    }

    /**
     * Searching list's value by value
     *
     * @return true if element exists, false if doesn't
     */
    @Override
    public boolean containsValue(V value) {
        return inOrder(root, value);
    }

    /**
     * Removing element with key
     *
     * @param key for key removing
     */
    @Override
    public void remove(K key) {
        Node node = getNodeByKey(key);
        if (node != null) {
            removeNodeWithBalance(node);
        }
    }

    /**
     * Removing element by value
     *
     * @param value value for removing
     */
    @Override
    public void removeValue(V value) {
        Node nodeForRemoving = getNodeByValue(root, value);
        if (nodeForRemoving != null) {
            removeNodeWithBalance(nodeForRemoving);
        }
    }

    @Override
    public int height() {
        // TODO: 3/11/2018
        return 0;
    }

    @Override
    public int size() {
        return size;
    }

    // TODO: 3/21/2018 There is bug. Tree isn't balanced. 5 -- 1 -- 4
    private void removeNodeWithBalance(Node node) {
        if (node.leftChild == null && node.rightChild == null) {
            detachCurrentNode(node);
        } else if (node.leftChild == null) {
            if (node.parent == null) {
                root = root.rightChild;
                root.parent = null;
            } else {
                if (node.isLeftChild) {
                    node.parent.leftChild = node.rightChild;
                } else {
                    node.parent.rightChild = node.rightChild;
                }
                node.rightChild.parent = node.parent;
            }
        } else if (node.rightChild == null) {
            if (node.parent == null) {
                root = root.leftChild;
                root.parent = null;
            } else {
                if (node.isLeftChild) {
                    node.parent.leftChild = node.leftChild;
                } else {
                    node.parent.rightChild = node.leftChild;
                }
                node.leftChild.parent = node.parent;
            }
        } else {
            Node replacer = findReplacer(node);
            //replacer has right child
            if (replacer.rightChild != null) {
                //delete replacer from old position
                //his child become paren't child
                if (replacer.isLeftChild) {
                    replacer.parent.leftChild = replacer.rightChild;
                } else {
                    replacer.parent.rightChild = replacer.rightChild;
                }
                //set children of deleting node to replacer
                replacer.leftChild = node.leftChild;
                replacer.rightChild = node.rightChild;
                //replace deleting node by replacer
                replaceNode(node, replacer);
            } else {
                //replacer has no children
                //delete replace from old position
                detachCurrentNode(replacer);
                //set children of deleting node to replacer
                replacer.leftChild = node.leftChild;
                replacer.rightChild = node.rightChild;
                //replace deleting node by replacer
                replaceNode(node, replacer);
            }
        }
        size--;
    }

    private void addNode(Node node) {
        Node currentNode = root, parent = root;
        while (currentNode != null) {
            parent = currentNode;
            if (node.key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.leftChild;
            } else if (node.key.compareTo(currentNode.key) > 0) {
                currentNode = currentNode.rightChild;
            }
        }
        currentNode = node;
        if (root == null) {
            root = currentNode;
            root.isLeftChild = false;
            root.parent = null;
        } else {
            if (node.key.compareTo(parent.key) < 0) {
                parent.leftChild = currentNode;
                currentNode.isLeftChild = true;
            } else {
                parent.rightChild = currentNode;
                currentNode.isLeftChild = false;
            }
            currentNode.parent = parent;
        }
        size++;
    }

    private void replaceNode(Node node, Node replacer) {
        if (node.parent == null) {
            root = replacer;
        } else {
            if (node.isLeftChild) {
                node.parent.leftChild = replacer;
            } else {
                node.parent.rightChild = replacer;
            }
        }
        replacer.parent = node.parent;
    }

    private void detachCurrentNode(Node node) {
        if (node.parent != null) {
            if (node.isLeftChild) {
                node.parent.leftChild = null;
            } else {
                node.parent.rightChild = null;
            }
        }
    }

    /**
     * Getting successor for deleting list with two children
     *
     * @param node list for getting
     * @return successor
     */
    private Node findReplacer(Node node) {
        Node replacer = node.rightChild;
        while (replacer.leftChild != null) {
            replacer = replacer.leftChild;
        }
        return replacer;
    }

    private Node getNodeByKey(K key) {
        Node currentNode = root;
        while (currentNode != null) {
            if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.leftChild;
            } else if (key.compareTo(currentNode.key) > 0) {
                currentNode = currentNode.rightChild;
            } else {
                return currentNode;
            }
        }
        return null;
    }

    private Node getNodeByValue(Node localRoot, V value) {
        if (localRoot != null) {
            if (localRoot.value.equals(value)) {
                return localRoot;
            }

            Node leftResult = getNodeByValue(localRoot.leftChild, value);
            if (leftResult != null) {
                return leftResult;
            }

            return getNodeByValue(localRoot.rightChild, value);
        } else {
            return null;
        }
    }

    private boolean inOrder(Node localRoot, V value) {
        if (localRoot == null) return false;
        if (localRoot.value == value) return true;
        if (localRoot.value != null && localRoot.value.equals(value)) return true;

        return inOrder(localRoot.leftChild, value) || inOrder(localRoot.rightChild, value);
    }

    //tree's bypassing (tree's traversals)

    /**
     * Symmetric traversal
     *
     * @param localRoot node for bypassing
     * @param function  function fro node manipulation during bypassing
     */
    private void inOrder(Node localRoot, BiConsumer<K, V> function) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild, function);
            function.accept(localRoot.key, localRoot.value);
            inOrder(localRoot.rightChild, function);
        }
    }

    /**
     * Forward traversal
     *
     * @param localRoot node for bypassing
     * @param function  function fro node manipulation during bypassing
     */
    private void fOrder(Node localRoot, BiConsumer<K, V> function) {
        if (localRoot != null) {
            function.accept(localRoot.key, localRoot.value);
            fOrder(localRoot.leftChild, function);
            fOrder(localRoot.rightChild, function);
        }
    }

    /**
     * Backward traversal
     *
     * @param localRoot node for bypassing
     * @param function  function fro node manipulation during bypassing
     */
    private void bOrder(Node localRoot, BiConsumer<K, V> function) {
        if (localRoot != null) {
            bOrder(localRoot.leftChild, function);
            bOrder(localRoot.rightChild, function);
            function.accept(localRoot.key, localRoot.value);
        }
    }
}
