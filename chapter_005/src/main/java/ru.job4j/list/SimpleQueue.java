package ru.job4j.list;

public class SimpleQueue<T> extends LinkedContainer<T> {

    //Используя контейнер на базе связанного списка создать контейнер Stack и Queue.
//
//public class SimpleStack<T> {
//    public <T> poll()
//
//    public void push(T value);
//}
//
//public class SimpleQueue<T> {
//    public <T> poll()
//
//    public void push(T value);
//}
//
//    метод poll - должен возвращать значение и удалять его из коллекции.

    public T  poll() {
//        final Node<T> f =  super.first;
//        return (f == null) ? null : unlinkFirst(f);
        final Node<T> l =  super.last;
        return (l == null) ? null : super.unlinkLast(l);
    }

    public void push(T value) {
        super.addFirst(value);
    }

}
