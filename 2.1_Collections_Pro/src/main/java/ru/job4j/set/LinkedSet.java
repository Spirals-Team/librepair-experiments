package ru.job4j.set;


/**
 * Class LinkedSet.
 * @param <E> generic
 */
public class LinkedSet<E> extends AbstractLink<E> implements SimpleSet<E> {
    /**
     * Method add.
     * @param e element for adding.
     */
    @Override
    public void add(E e) {
        if (checkDuplicate(e)) {
            super.put(e);
        }
    }

    /**
     * Method checkDuplicate.
     * @param e element for checking.
     * @return result.
     */
    private boolean checkDuplicate(E e) {
        boolean result = true;
        Node<E> temp = first;
        while (temp != null) {
            if (temp.item.equals(e)) {
                result = false;
                break;
            }
            temp = temp.next;
        }
        return result;
    }

}
