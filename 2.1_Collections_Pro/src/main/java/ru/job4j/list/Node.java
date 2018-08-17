package ru.job4j.list;

/**
 * Class Node.
 * @param <T> generic
 */
public class Node<T> {
    /**
     * @param value saving value
     */
    private T value;
    /**
     * @next next link
     */
    private Node<T> next;
    /**
     * @param count how many object Node was create
     */
    private static int count = 0;

    /**
     * Constructor.
     * @param value saving value
     */
    public Node(T value) {
        this.value = value;
        count++;
    }

    /**
     * Getter next.
     * @return next
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Getter count.
     * @return count
     */
    public static int getCount() {
        return count;
    }

    /**
     * Setter next.
     * @param next next node.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
