package ru.job4j.generic;

public abstract class  AbstractStore<T extends Base>   {
    private SimpleArray<T> value;

    protected AbstractStore(int size) {
        this.value = new SimpleArray<>(size);
    }

    public void add(T model) {
        value.add(model);

    }


    public boolean replace(String id) {
        boolean result = false;
        if (findVyId(id) != null) {
            result = true;
        }
        return result;
    }


    public boolean delete(String id) {
        boolean result = false;
        if (findVyId(id) != null) {
            result = true;
        }
        return result;
    }


    public T findVyId(String id) {
        T t = null;
        for (int index = 0; index < value.size(); index++) {
            if (id.equals(value.get(index).getId())) {
                t = value.get(index);
            }
        }
        return t;
    }
}
