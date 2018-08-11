package net.coljate.tree.impl;

import java.util.Comparator;
import java.util.Objects;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import net.coljate.map.AbstractEntry;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.map.SortedMap;
import net.coljate.tree.AbstractTreeMap;
import net.coljate.tree.MutableBinaryTreeMap.MutableBinaryNode;
import net.coljate.tree.impl.RedBlackTreeMap.RedBlackNode;
import net.coljate.tree.navigation.TreeNavigation;
import net.coljate.tree.ImmutableBinaryTreeMap;
import net.coljate.tree.MutableBinaryTreeMap;
import net.coljate.tree.MutableSortedTreeMap;
import net.coljate.set.SequentialSet;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 * @see java.util.TreeMap
 * @see <a href="https://www.cs.auckland.ac.nz/software/AlgAnim/red_black.html">Red-Black Trees</a>
 */
public class RedBlackTreeMap<K, V>
        extends AbstractTreeMap<K, V, RedBlackNode<K, V>>
        implements MutableBinaryTreeMap<K, V, RedBlackNode<K, V>>, MutableSortedTreeMap<K, V, RedBlackNode<K, V>> {

    public static <K extends Comparable<? super K>, V> RedBlackTreeMap<K, V> keyComparing() {
        return keyComparing(Comparator.naturalOrder());
    }

    public static <K, V> RedBlackTreeMap<K, V> keyComparing(@Nonnull final Comparator<? super K> comparator) {
        return new RedBlackTreeMap<>((e1, e2) -> comparator.compare(e1.key(), e2.key()));
    }

    public static <K, V> RedBlackTreeMap<K, V> copyOf(@Nonnull final SortedMap<K, V> map) {
        final RedBlackTreeMap<K, V> redBlackTree = new RedBlackTreeMap<>(map.comparator());
        redBlackTree.putAll(map);
        return redBlackTree;
    }

    public static <K, V> RedBlackTreeMap<K, V> copyOf(@Nonnull final Map<? extends K, ? extends V> map, final Comparator<? super Entry<K, V>> comparator) {
        final RedBlackTreeMap<K, V> redBlackTree = new RedBlackTreeMap<>(comparator);
        redBlackTree.putAll(map);
        return redBlackTree;
    }

    private static final boolean BLACK = true, RED = !BLACK;
    private final Comparator<? super Entry<K, V>> comparator;
    private RedBlackNode<K, V> root;
    private int count;
    private SequentialSet<K> keys;

    public RedBlackTreeMap(final Comparator<? super Entry<K, V>> comparator) {
        this(null, comparator);
    }

    protected RedBlackTreeMap(final RedBlackNode<K, V> root, final Comparator<? super Entry<K, V>> comparator) {
        this.root = root;
        this.count = root == null ? 0 : root.countDescendents(TreeNavigation.getDefault());
        this.comparator = Objects.requireNonNull(comparator);
    }

    @Override
    public RedBlackNode<K, V> root() {
        return root;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public V put(final K key, final V value) {
        final RedBlackNode<K, V> node = new RedBlackNode<>(key, value, BLACK);
        count++;
        //Set root if there isn't one
        if (root == null) {
            root = node;
            return null;
        }
        //Find the appropriate parent for this node
        RedBlackNode<K, V> parent, current = root;
        int comparison;
        do {
            parent = current;
            comparison = comparator.compare(node, current);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return current.getAndSetValue(value);
            }
        } while (current != null);
        //Attach to the correct side
        if (comparison < 0) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }
        this.rebalance(node);
        return null;
    }

    private void rebalance(RedBlackNode<K, V> x) {
        setColour(x, RED);
        RedBlackNode<K, V> p;
        while (x != null && x != root && colour(parent(x)) == RED) {
            p = parent(x);
            if (p == left(parent(p))) {
                final RedBlackNode<K, V> y = right(parent(p));
                if (y == null || colour(y) == BLACK) {
                    if (x == right(p)) {
                        x = p;
                        p = parent(x);
                        this.rotateLeft(x);
                    }
                    setColour(p, BLACK);
                    setColour(parent(p), RED);
                    this.rotateRight(parent(p));
                } else {
                    setColour(p, BLACK);
                    setColour(y, BLACK);
                    setColour(parent(p), RED);
                    x = parent(p);
                }
            } else {
                final RedBlackNode<K, V> y = left(parent(p));
                if (y == null || colour(y) == BLACK) {
                    if (x == left(p)) {
                        x = p;
                        p = parent(x);
                        this.rotateRight(x);
                    }
                    setColour(p, BLACK);
                    setColour(parent(p), RED);
                    this.rotateLeft(parent(p));
                } else {
                    setColour(p, BLACK);
                    setColour(y, BLACK);
                    setColour(parent(p), RED);
                    x = parent(p);
                }
            }
        }
        setColour(root, BLACK);
    }

    @CheckForNull
    private static <K, V> RedBlackNode<K, V> parent(final RedBlackNode<K, V> node) {
        return node == null ? null : node.parent;
    }

    @CheckForNull
    private static <K, V> RedBlackNode<K, V> left(final RedBlackNode<K, V> node) {
        return node == null ? null : node.left;
    }

    @CheckForNull
    private static <K, V> RedBlackNode<K, V> right(final RedBlackNode<K, V> node) {
        return node == null ? null : node.right;
    }

    private static boolean colour(final RedBlackNode<?, ?> node) {
        return node == null ? BLACK : node.colour;
    }

    private static void setColour(final RedBlackNode<?, ?> node, final boolean colour) {
        if (node != null) {
            node.colour = colour;
        }
    }

    private void rotateLeft(final RedBlackNode<K, V> node) {
        if (node != null) {
            final RedBlackNode<K, V> previous = node.left;
            node.right = left(previous);
            if (previous.left != null) {
                previous.left.parent = node;
            }
            previous.parent = node.parent;
            if (node.parent == null) {
                this.root = previous;
            } else if (node == node.parent.left) {
                node.parent.left = previous;
            } else {
                node.parent.right = previous;
            }
            previous.left = node;
            node.parent = previous;
        }
    }

    private void rotateRight(final RedBlackNode<K, V> node) {
        if (node != null) {
            final RedBlackNode<K, V> right = node.right;
            node.right = node.left;
            if (right.left != null) {
                right.left.parent = node;
            }
            right.parent = node.parent;
            if (node.parent == null) {
                root = right;
            } else if (node.parent.left == node) {
                node.parent.left = right;
            } else {
                node.parent.right = right;
            }
            right.left = node;
            node.parent = right;
        }
    }

    @Override
    public boolean remove(final RedBlackNode<K, V> node) {
        if (node == null) {
            return false;
        }
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public Comparator<? super Entry<K, V>> comparator() {
        return comparator;
    }

    @Override
    public RedBlackNode<K, V> greatest() {
        return Functions.ifNonNull(root, RedBlackNode::rightMost, root);
    }

    @Override
    public RedBlackNode<K, V> least() {
        return Functions.ifNonNull(root, RedBlackNode::leftMost, root);
    }

    @Override
    public SequentialSet<K> keys() {
        return keys == null
                ? keys = MutableSortedTreeMap.super.keys()
                : keys;
    }

    @Override
    public RedBlackTreeMap<K, V> mutableCopy() {
        return new RedBlackTreeMap<>(root == null ? null : root.copy(), comparator);
    }

    @Override
    public ImmutableBinaryTreeMap<K, V, ?> immutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static final class RedBlackNode<K, V>
            extends AbstractEntry<K, V>
            implements MutableBinaryNode<K, V, RedBlackNode<K, V>> {

        private final K key;
        @CheckForNull
        private V value;
        @CheckForNull
        private RedBlackNode<K, V> parent, left, right;
        private boolean colour = BLACK;

        RedBlackNode(final K key, final V value, final boolean black) {
            this.key = key;
            this.value = value;
            this.colour = black;
        }

        void setLeft(@Nonnull final RedBlackNode<K, V> left) {
            this.left = left;
            left.parent = this;
        }

        void setRight(@Nonnull final RedBlackNode<K, V> right) {
            this.right = right;
            right.parent = this;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }

        @Override
        public void setValue(final V value) {
            this.value = value;
        }

        @Override
        public RedBlackNode<K, V> left() {
            return left;
        }

        @Override
        public RedBlackNode<K, V> right() {
            return right;
        }

        @Override
        public RedBlackNode<K, V> self() {
            return this;
        }

        public boolean isBlack() {
            return colour == BLACK;
        }

        @Nonnull
        protected RedBlackNode<K, V> copy() {
            final RedBlackNode<K, V> copy = new RedBlackNode<>(key, value, colour);
            if (left != null) {
                copy.setLeft(left.copy());
            }
            if (right != null) {
                copy.setRight(right.copy());
            }
            return copy;
        }

    }

}
