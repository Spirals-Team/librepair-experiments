package ru.job4j.list;


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
public class SimpleStack<T> extends LinkedContainer<T> {

    public T  poll() {
        final Node<T> f =  super.first;
        return (f == null) ? null : super.unlinkFirst(f);
    }

    public void push(T value) {
        super.addFirst(value);
    }

}
