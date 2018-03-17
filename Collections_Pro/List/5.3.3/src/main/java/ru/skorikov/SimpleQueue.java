package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <T> параметр.
 * @ author: Alex_Skorikov.
 * @ date: 27.10.17
 * @ version: java_kurs_standart
 */
public class SimpleQueue<T> {

    /**
     * Контейнер на базе односвязного списка.
     */
    private SimpleLinkedContainer container = new SimpleLinkedContainer();

    /**
     * Добавить объект в контейнер.
     *
     * @param value данные.
     */
    public void push(T value) {
        container.add(value);
    }

    /**
     * Метод возвращает последний элемент и удаляет его.
     * Первый добавленный элемент будет удален первым.
     *
     * @return последний элемент.
     */
    public T poll() {
        SimpleLinkedContainer.Element<T> returnElement = container.getElementFirst();
        container.setElementFirst(container.getElementFirst().getNext());
        SimpleLinkedContainer.Element<T> element = container.getElementFirst();

        while (element != null) {
            int tempIndex = element.getIndex();
            tempIndex--;
            element.setIndex(tempIndex);
            element = element.getNext();
        }
        int containerNewIndex = container.getIndex();
        containerNewIndex--;
        container.setIndex(containerNewIndex);
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
