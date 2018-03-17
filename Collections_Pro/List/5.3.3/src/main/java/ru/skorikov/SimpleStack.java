package ru.skorikov;


/**
 * Created with IntelliJ IDEA.
 *
 * @param <T> параметр.
 * @ author: Alex_Skorikov.
 * @ date: 27.10.17
 * @ version: java_kurs_standart
 */
public class SimpleStack<T> {

    /**
     * Контейнер элементов.
     */
    private SimpleLinkedContainer container = new SimpleLinkedContainer();

    /**
     * Добавить элемент в контейнер.
     *
     * @param value данные.
     */
    public void push(T value) {
        container.add(value);
    }

    /**
     * Получить првый элемент и удалить его.
     * Первый добавленный элемент будет удален последним.
     *
     * @return первый элемент.
     */
    public T poll() {
        SimpleLinkedContainer.Element<T> returnElement = container.getElementLast();

        if (container.getElementLast() == container.getElementFirst()) {
            container.setElementLast(null);
            container.setElementFirst(null);
        } else {
            SimpleLinkedContainer.Element<T> newLast = container.getElementFirst();
            while (newLast.getNext().getNext() != null) {
                newLast = newLast.getNext();
            }
            newLast.setNext(null);
            container.setElementLast(newLast);
        }
        return returnElement.getData();
    }

    /**
     * Получить контейнер.
     * @return контейнер.
     */
    public SimpleLinkedContainer getContainer() {
        return container;
    }
}
