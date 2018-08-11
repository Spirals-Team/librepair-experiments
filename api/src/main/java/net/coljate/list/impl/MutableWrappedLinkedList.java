package net.coljate.list.impl;

import java.io.Serializable;

/**
 *
 * @author Ollie
 */
public class MutableWrappedLinkedList<T>
        extends MutableWrappedList<T>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected MutableWrappedLinkedList(final java.util.LinkedList<T> delegate) {
        super(delegate);
    }

}
