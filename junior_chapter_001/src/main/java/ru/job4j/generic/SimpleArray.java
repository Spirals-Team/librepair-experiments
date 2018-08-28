package ru.job4j.generic;


public class SimpleArray<E> {
    Object[] objects;
    int index = 0;

    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    public void add(E value) {
        this.objects[index++] = value;
    }

    public E get(int position) {
        return (E) this.objects[position];
    }

    public void update(int position, E value) {
        this.objects[position] = value;
    }

    public void delete(int position) {
        System.arraycopy(this.objects, position + 1, this.objects, position, this.index - position - 1);
        this.objects[this.objects.length - 1] = 0;
        this.index--;
    }
}
