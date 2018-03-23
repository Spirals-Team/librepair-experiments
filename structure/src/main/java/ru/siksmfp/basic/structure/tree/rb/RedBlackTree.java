package ru.siksmfp.basic.structure.tree.rb;

import ru.siksmfp.basic.structure.api.TreeStructure;
import ru.siksmfp.basic.structure.utils.SystemUtils;

/**
 * @author Artem Karnov @date 3/8/2018.
 * @email artem.karnov@t-systems.com
 */
public class RedBlackTree<K extends Comparable<K>, V> implements TreeStructure<K, V> {

    //    Rebalance
    //
    //    Black aunt - rotation
    //    Red aunt - color flip
    //
    //    Result of rotation
    //
    //        Black
    //    Red       Red
    //
    //    Result of color flip
    //
    //          Red
    //    Black     Black
    //
    //
    //    Rotations
    //
    //    Right                                Left
    //                3    --->     2                 1       --->        2
    //              2            1    3                 2              1     3
    //            1                                       3
    //
    //
    //   Left-right                            Right-left
    //
    //                5   --->     4                3      --->       4
    //              3            3   5                5            3     5
    //                4                             4
    //
    //
    //    Color flip
    //
    //            Black        <-->           Red
    //        Red      Red              Black     Black
    //    Red                        Red
    //

    /**
     * Model of node's tree
     */
    private class Node {
        private K key;
        private V value;
        private boolean isRed;
        private boolean isLeftChild;
        private Node parent;
        private Node leftChildren;
        private Node rightChildren;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.isRed = true;
            this.isLeftChild = false;
            this.parent = null;
            this.rightChildren = null;
            this.leftChildren = null;
        }

        public String toString() {
            String leftChildrenKey = leftChildren == null ? "null" : leftChildren.key == null ? "null" : leftChildren.key.toString();
            String leftChildrenValue = leftChildren == null ? "null" : leftChildren.value == null ? "null" : leftChildren.value.toString();
            String rightChildrenKey = rightChildren == null ? "null" : rightChildren.key == null ? "null" : rightChildren.key.toString();
            String rightChildrenValue = rightChildren == null ? "null" : rightChildren.value == null ? "null" : rightChildren.value.toString();
            String color = isRed == true ? "Red" : "Black";

            return "Node{key " + key + ", value " + value + ", color " + color
                    + ", left child (" + leftChildrenKey + ";" + leftChildrenValue + ")"
                    + "; right child (" + rightChildrenKey + ";" + rightChildrenValue + ") }";
        }
    }

    private Node root;
    private int size;


    @Override
    public void add(K key, V value) {
        Node node = new Node(key, value);
        if (root == null) {
            root = node;
            root.parent = null;
            root.isRed = false;
        } else {
            add(root, node);
        }
        size++;
    }

    @Override
    public void strictAdd(K key, V value) {
        Node node = new Node(SystemUtils.clone(key), SystemUtils.clone(value));
        if (root == null) {
            root = node;
            root.parent = null;
            root.isRed = false;
        } else {
            add(root, node);
        }
        size++;
    }

    @Override
    public V get(K key) {
        Node node = root;
        while (node != null) {
            if (node.key.compareTo(key) == 0) {
                return node.value;
            } else if (node.key.compareTo(key) > 0) {
                node = node.leftChildren;
            } else {
                node = node.rightChildren;
            }
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        Node node = root;
        while (node != null) {
            if (node.key.compareTo(key) == 0) {
                return true;
            } else if (node.key.compareTo(key) > 0) {
                node = node.leftChildren;
            } else {
                node = node.rightChildren;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        return inOrder(root, value);
    }

    private boolean inOrder(Node localRoot, V value) {
        if (localRoot == null) return false;
        if (localRoot.value == value) return true;
        if (localRoot.value != null && localRoot.value.equals(value)) return true;

        return inOrder(localRoot.leftChildren, value) || inOrder(localRoot.rightChildren, value);
    }

    @Override
    public void remove(K key) {

    }

    @Override
    public void removeValue(V value) {

    }

    @Override
    public int height() {
        if (root == null) {
            return 0;
        } else {
            return height(root) - 1;
        }
    }

    @Override
    public int size() {
        return size;
    }

    public int blackHeight() {
        return blackNodes(root);
    }

    // 1) Find place for inserting (binary tree rules)
    // 2) Insert red node
    // 3) Do balancing
    //      1) If uncle is red
    //       |---1) Do color flip of parent, uncle and grand father
    //       |---2) Do color flip of grand father relative until root
    //       2) If uncle is black
    //        |---1) Do rotation
    //        |    |---1) Depends on situation we can do
    //        |         |---1) Right rotation
    //        |         |---2) Left rotation
    //        |         |---3) Left-right rotation
    //        |         |---4) Right-left rotation
    //        |---2) Do color flip

    private void add(Node root, Node node) {
        if (node.key.compareTo(root.key) > 0) {
            if (root.rightChildren == null) {
                root.rightChildren = node;
                node.parent = root;
                node.isLeftChild = false;
            } else {
                add(root.rightChildren, node);
            }
        } else if (node.key.compareTo(root.key) < 0) {
            if (root.leftChildren == null) {
                root.leftChildren = node;
                node.parent = root;
                node.isLeftChild = true;
            } else {
                add(root.leftChildren, node);
            }
        } else {
            //If node with key already exist we just update value
            root.value = node.value;
        }
        balanceTree(node);
    }

    private void balanceTree(Node node) {
        if (!node.equals(root)) {
            if (node.isRed && node.parent.isRed) {
                correctTree(node);
            }
            balanceTree(node.parent);
        }
    }

    private void correctTree(Node node) {
        //left subtree after grand father
        if (node.parent.isLeftChild) {
            // Right uncle is null or grand father is black
            if (node.parent.parent.rightChildren == null || !node.parent.parent.isRed) {
                rotate(node);
            }
            //Color flip around grand father
            if (node.parent.parent != null) {
                node.parent.parent.rightChildren.isRed = false;
                node.parent.parent.isRed = true;
                node.parent.isRed = false;
            }
            //right subtree after grand father
        } else {
            // Left uncle is null or grand father is black
            if (node.parent.parent.leftChildren == null || !node.parent.parent.isRed) {
                rotate(node);
            }
            //Color flip around grand father
            if (node.parent.parent != null) {
                node.parent.parent.leftChildren.isRed = false;
                node.parent.parent.isRed = true;
                node.parent.isRed = false;
            }
        }
    }

    private void rotate(Node node) {
        if (node.isLeftChild) {
            if (node.parent.isLeftChild) {                               //A
                rightRotation(node.parent.parent);                          //B
                node.isRed = true;                                             //C
                node.parent.isRed = false;
                if (node.parent.rightChildren != null) {
                    node.parent.rightChildren.isRed = true;
                }
            } else {                                                           //A
                rightLeftRotation(node.parent.parent);                      //B
                node.isRed = false;                                            //C
                node.rightChildren.isRed = true;   //is needed?
                node.leftChildren.isRed = false;
            }
        } else {
            if (!node.parent.isLeftChild) {                                       //A
                leftRotation(node.parent.parent);                              //B
                node.isRed = true;                                          //C
                node.parent.isRed = false;
                if (node.parent.leftChildren != null) {
                    node.parent.leftChildren.isRed = true;
                }
            } else {
                leftRightRotation(node.parent.parent);                            //A
                node.isRed = false;                                                   //B
                node.rightChildren.isRed = true;                                   //C
                node.leftChildren.isRed = false;
            }
        }
    }

    private void leftRotation(Node node) {
        Node temp = node.rightChildren;
        node.rightChildren = temp.leftChildren;
        if (node.rightChildren != null) {
            node.rightChildren.parent = node;
            node.rightChildren.isLeftChild = false;
        }
        if (node.parent == null) {
            //root
            root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (node.isLeftChild) {
                temp.isLeftChild = true;
                temp.parent.leftChildren = temp;
            } else {
                temp.isLeftChild = false;
                temp.parent.rightChildren = temp;
            }
        }
        temp.leftChildren = node;
        node.isLeftChild = true;
        node.parent = temp;
    }

    private void rightRotation(Node node) {
        Node temp = node.leftChildren;
        node.leftChildren = temp.rightChildren;
        if (node.leftChildren != null) {
            node.leftChildren.parent = node;
            node.rightChildren.isLeftChild = true;
        }
        if (node.parent == null) {
            //root
            root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (!node.isLeftChild) {
                temp.isLeftChild = false;
                temp.parent.rightChildren = temp;
            } else {
                temp.isLeftChild = true;
                temp.parent.leftChildren = temp;
            }
        }
        temp.rightChildren = node;
        node.isLeftChild = false;
        node.parent = temp;
    }

    private void leftRightRotation(Node node) {
        leftRotation(node.leftChildren);
        rightRotation(node);
    }

    private void rightLeftRotation(Node node) {
        rightRotation(node.rightChildren);
        leftRotation(node);
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = height(node.leftChildren) + 1;
            int rightHeight = height(node.rightChildren) + 1;
            if (leftHeight > rightHeight) {
                return leftHeight;
            } else {
                return rightHeight;
            }
        }
    }

    private int blackNodes(Node node) {
        if (node == null) {
            return 1;
        } else {
            int rightBlackNodes = blackNodes(node.rightChildren);
            int leftBlackNodes = blackNodes(node.leftChildren);
            if (rightBlackNodes != leftBlackNodes) {
                throw new IllegalStateException("Tree isn't balanced");
            }

            if (!node.isRed) {
                leftBlackNodes++;
            }
            return leftBlackNodes;
        }
    }
}
