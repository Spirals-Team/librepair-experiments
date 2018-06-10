package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Node<T> {

    T value;
    Node<T> next;
    static int size = 0;

    public Node(T value) {
        this.value = value;
        size++;
    }

    /**
     * Метод определяет зациклен ли список или нет используя алгорит черепахи и зайца.
     * Создаем 2 ссылки на список и перемещаем их на разных скоростях, один на 1 узнл, второй на 2 узла вперед.
     * Если связанный список имеет цикл, то они встретятся.
     * @param first- элемент списка, с которого начнется обход списка.
     * @return true при зацикленности списка.
     */
    public boolean hasCycle(Node first) {
        boolean result = false;
        Node slow, fast;
        fast = first;
        slow = fast;
        while (!result) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            }
            if (slow == null || fast == null) {
                break;
            }
            if (slow == fast) {
                 result = true;
            }
        }
        return result;
    }
}
