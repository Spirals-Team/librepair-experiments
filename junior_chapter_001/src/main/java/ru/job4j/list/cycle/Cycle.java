package ru.job4j.list.cycle;

/**
 * Класс Cycle.
 */
public class Cycle {
    /**
     * Метод hasCycle для проверки цикличности.
     *
     * @param node объект.
     * @return вернем true если есть цикличность.
     */
    public boolean hasCycle(Node node) {
        // Результат работы метода.
        boolean result = false;
        // Временный объект.
        Node next = node;
        // Если объект next не равен null то выполняем цикл.
        while (next != null) {
            // Если объект next равен следующему объекту то есть цикличность.
            if (next.equals(next.getNextNode())) {
                result = true;
                break;
            }
            // Присваиваем переменной next следующий объект.
            next = next.getNextNode();
        }
        return result;
    }
}
