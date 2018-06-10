package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractSimple<T> {

     Node<T> fstNode;
     Node<T> lstNode;
     int size = 0;

    /**
     * Метод возвращает первый элемент списка, следующий за нулевым узлом fstNode, переопределяет ссылку
     * в fstNode на следующий элемент на элемент находящийся за элементом, который возвращаем и уменьшаем длину списка на 1
     * @return первый элемент списка
     */
    public T poll() {
        Node<T> target = fstNode.next;
        fstNode.next = target.next;
        size--;
        return target.item;
    }

    public abstract void push(T value);

    /**
     * Внутренний класс реализующий структуру узлов
     */
    class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
