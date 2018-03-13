package ru.job4j.generic;

//Сделать интерфейс Store<T extends Base>,
//        где Base - это абстрактный класс для моделей c методами String getId(); void setId(String id).

//3. Помните. про инкапсуляцию. В методах store нельзя использовать методы c index,
// т.е. при реализации метода update вам необходимо найти в коллекции элемент с таким же id
// , как и переданный параметр, и если найден, то заменить его.
public interface Store<T extends Base> {

    T add(T model);

    T update(T model);

    boolean delete(String id);

}




