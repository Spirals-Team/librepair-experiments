package ru.job4j.List;

/**
 * Класс для проверки цикличности списка
 */
public class Cycle {

    /**
     * VМетод для проверки цикличности списка
     * @param first начальный узел
     * @return цикдичный ли список
     */
    boolean hasCycle(SimpleNode first) {
        SimpleNode current = first;
        int flag = first.check + 1;
        do {
            current.check = flag;
            current = current.next;
        } while (current != null && current.check != flag);
        return current == null ? false : current.check == flag;
    }
}
