package ru.job4j.list;


/**
 * Class findCycle.
 */
public class FindCycle {
    /**
     * Method hasCycle.
     * @param first start node
     * @return result
     */
    boolean hasCycle(Node first) {
        boolean result = false;
        Node temp = first.getNext();
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.getNext();
            if (count - 1 > Node.getCount()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
