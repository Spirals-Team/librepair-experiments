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
        private Node leftChildren;
        private Node rightChildren;
        private Node parent;
        private boolean isLeftChildren;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.rightChildren = null;
            this.leftChildren = null;
        }

        public String toString() {
            String leftChildrenKey = leftChildren == null ? "null" : leftChildren.key == null ? "null" : leftChildren.key.toString();
            String leftChildrenValue = leftChildren == null ? "null" : leftChildren.value == null ? "null" : leftChildren.value.toString();
            String rightChildrenKey = rightChildren == null ? "null" : rightChildren.key == null ? "null" : rightChildren.key.toString();
            String rightChildrenValue = rightChildren == null ? "null" : rightChildren.value == null ? "null" : rightChildren.value.toString();

            return "Node{key " + key + ", value " + value + " isLeft " + isLeftChildren
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
        addNode(new Node(key, value));
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
        addNode(new Node(SystemUtils.clone(key), SystemUtils.clone(value)));
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
                currentNode = currentNode.leftChildren;
            } else {
                currentNode = currentNode.rightChildren;
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
            removeNode(node);
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
            removeNode(nodeForRemoving);
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

    private void removeNode(Node node) {
        if (node.leftChildren == null && node.rightChildren == null) {
            removeChildrenNode(node);
        } else if (node.leftChildren == null) {
            if (node.parent == null) {
                root = root.rightChildren;
                root.parent = null;
            } else {
                if (node.isLeftChildren) {
                    node.parent.leftChildren = node.rightChildren;
                    node.rightChildren.parent = node.parent;
                } else {
                    node.parent.rightChildren = node.rightChildren;
                    node.rightChildren = node.parent;
                }
            }
        } else if (node.rightChildren == null) {
            if (node.parent == null) {
                root = root.leftChildren;
                root.parent = null;
            } else {
                if (node.isLeftChildren) {
                    node.parent.leftChildren = node.leftChildren;
                    node.leftChildren = node.parent;
                } else {
                    node.parent.rightChildren = node.leftChildren;
                    node.leftChildren = node.parent;
                }
            }
        } else {
            Node replacer = findReplacer(node);
            //replacer has children
            if (replacer.rightChildren != null) {
                //delete replacer from old position
                //his children become paren't children
                if (replacer.isLeftChildren) {
                    replacer.parent.leftChildren = replacer.rightChildren;
                } else {
                    replacer.parent.rightChildren = replacer.rightChildren;
                }
                //set left children
                replacer.leftChildren = node.leftChildren;
                //set right children
                if (replacer != node.rightChildren) {
                    replacer.rightChildren = node.rightChildren;
                }
                //replace deleting node by replacer
                replaceNode(node, replacer);
            } else {
                //replacer has no children
                //delete replace from old position
                removeChildrenNode(replacer);
                //set left children
                replacer.leftChildren = node.leftChildren;
                //set right children
                if (replacer != node.rightChildren) {
                    replacer.rightChildren = node.rightChildren;
                } else {
                    replacer.rightChildren = null;
                }
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
                currentNode = currentNode.leftChildren;
            } else {
                currentNode = currentNode.rightChildren;
            }
        }
        currentNode = node;
        if (root == null) {
            root = currentNode;
            root.isLeftChildren = false;
            root.parent = null;
        } else {
            if (node.key.compareTo(parent.key) < 0) {
                parent.leftChildren = currentNode;
                currentNode.isLeftChildren = true;
            } else {
                parent.rightChildren = currentNode;
                currentNode.isLeftChildren = false;
            }
            currentNode.parent = parent;
        }
        size++;
    }

    private void replaceNode(Node node, Node replacer) {
        if (node.parent == null) {
            root = replacer;
        } else {
            if (node.isLeftChildren) {
                node.parent.leftChildren = replacer;
            } else {
                node.parent.rightChildren = replacer;
            }
        }
    }

    private void removeChildrenNode(Node node) {
        if (node.isLeftChildren) {
            node.parent.leftChildren = null;
        } else {
            node.parent.rightChildren = null;
        }
    }

    /**
     * Getting successor for deleting list with two children
     *
     * @param node list for getting
     * @return successor
     */
    private Node findReplacer(Node node) {
        Node replacer = node.rightChildren;
        while (replacer.leftChildren != null) {
            replacer = replacer.leftChildren;
        }
        return replacer;
    }

    private Node getNodeByKey(K key) {
        Node currentNode = root;
        while (currentNode != null) {
            if (key.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.leftChildren;
            } else if (key.compareTo(currentNode.key) > 0) {
                currentNode = currentNode.rightChildren;
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

            Node leftResult = getNodeByValue(localRoot.leftChildren, value);
            if (leftResult != null) {
                return leftResult;
            }

            return getNodeByValue(localRoot.rightChildren, value);
        } else {
            return null;
        }
    }

    private boolean inOrder(Node localRoot, V value) {
        if (localRoot == null) return false;
        if (localRoot.value == value) return true;
        if (localRoot.value != null && localRoot.value.equals(value)) return true;

        return inOrder(localRoot.leftChildren, value) || inOrder(localRoot.rightChildren, value);
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
            inOrder(localRoot.leftChildren, function);
            function.accept(localRoot.key, localRoot.value);
            inOrder(localRoot.rightChildren, function);
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
            fOrder(localRoot.leftChildren, function);
            fOrder(localRoot.rightChildren, function);
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
            bOrder(localRoot.leftChildren, function);
            bOrder(localRoot.rightChildren, function);
            function.accept(localRoot.key, localRoot.value);
        }
    }
}
