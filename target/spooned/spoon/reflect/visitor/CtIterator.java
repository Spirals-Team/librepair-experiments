package spoon.reflect.visitor;


/**
 * A class to be able to iterate over the children elements in the tree of a given node, in depth-first order.
 */
public class CtIterator extends spoon.reflect.visitor.CtScanner implements java.util.Iterator<spoon.reflect.declaration.CtElement> {
    /**
     * A deque containing the elements the iterator has seen but not expanded
     */
    private java.util.ArrayDeque<spoon.reflect.declaration.CtElement> deque = new java.util.ArrayDeque<spoon.reflect.declaration.CtElement>() {
        /**
         * add a collection of elements with addFirst instead of default add() which defaults to addLast()
         *
         * @param c
         * 		Collection of CtElements
         * @return true if this deque has changed, in accordance with original method
         */
        @java.lang.Override
        public boolean addAll(java.util.Collection c) {
            for (java.lang.Object aC : c) {
                this.addFirst(((spoon.reflect.declaration.CtElement) (aC)));
            }
            return (c.size()) > 0;
        }
    };

    /**
     * A deque to be used when scanning an element so that @deque preserves the elements in dfs without complete expansion
     */
    private java.util.ArrayDeque<spoon.reflect.declaration.CtElement> current_children = new java.util.ArrayDeque<>();

    /**
     * CtIterator constructor, prepares the iterator from the @root node
     *
     * @param root
     * 		the initial node to expand
     */
    public CtIterator(spoon.reflect.declaration.CtElement root) {
        if (root != null) {
            deque.add(root);
        }
    }

    /**
     * prevent scanner from going down the tree, instead save with other CtElement children of the current node
     *
     * @param element
     * 		the next direct child of the current node being expanded
     */
    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        if (element != null) {
            current_children.addFirst(element);
        }
    }

    @java.lang.Override
    public boolean hasNext() {
        return (deque.size()) > 0;
    }

    /**
     * Dereference the "iterator"
     *
     * @return CtElement the next element in DFS order without going down the tree
     */
    @java.lang.Override
    public spoon.reflect.declaration.CtElement next() {
        spoon.reflect.declaration.CtElement next = deque.pollFirst();// get the element to expand from the deque

        current_children.clear();// clear for this scan

        next.accept(this);// call @scan for each direct child of the node

        deque.addAll(current_children);// overridden method to add all to first

        return next;
    }
}

