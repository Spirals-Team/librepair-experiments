package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <T> параметр класса.
 *            <p>
 *            5.3.4. Задан связанный список. Определить цикличность.
 * @ author: Alex_Skorikov.
 * @ date: 29.10.17
 * @ version: java_kurs_standart
 */
public class Node<T> {

    /**
     * Данные экземплятра класса.
     */
    private T value;

    /**
     * Ссылка на следующий объект.
     */
    private Node<T> next;

    /**
     * Конструктор.
     *
     * @param value данные.
     */
    public Node(T value) {
        this.value = value;
    }

    /**
     * Задать следующий объект.
     *
     * @param next next Object.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Метод определения цикличности.
     *
     * @param first первый элемент.
     * @return false - цикличен.
     */
    public static boolean hasCycle(Node first) {
        boolean isCycle = true;
        // Если элемент 1.
        if (first.next == null) {
            isCycle = false;
        } else {
            Node startElement = first;
            Node endElement = first.next;
            boolean isExitFromSearch = false;
            // Идем пока не найдем.
            while (!isExitFromSearch) {
                // С первого до текущего
                while (startElement != endElement) {
                    // Если ссылка на предыдцщий элемент.
                    if (endElement.next == startElement) {
                        isExitFromSearch = true;
                        break;
                        //Следующий элемент.
                    } else {
                        startElement = startElement.next;
                    }
                }
                // Сдвигаем следующий элемент
                // Возвращаем стартовый.
                startElement = first;
                if (endElement.next == null) {
                    isCycle = false;
                    break;
                } else {
                    endElement = endElement.next;
                }
            }
        }
        return isCycle;
    }
}
