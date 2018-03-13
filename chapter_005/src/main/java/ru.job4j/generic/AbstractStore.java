package ru.job4j.generic;

//4. После реализации проверьте можно ли избавиться от дублирования кода в вашем проекте.
// UserStore и RoleStore будут иметь один и тот же функционал.
// Общий для них функционал необходимо вынести в абстрактный класс AbstractStore.
public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> elements;

    public AbstractStore(int numberOfElements) {
        this.elements = new SimpleArray<T>(numberOfElements);
    }

//    @Override
//    public Base add(Base model) {
//        return null;
//    }

    @Override
    public Base add(Base model) {

        elements.add((T) model);

        return (Base) model;

    }

    //    3. Помните. про инкапсуляцию. В методах store нельзя использовать методы c index,
// т.е. при реализации метода update вам необходимо найти в коллекции элемент с таким же id,
// как и переданный параметр, и если найден, то заменить его
    @Override
    public Base update(Base model) {

        T baseForUpdate =  findByBaseObjectsInternalId(model.getId());

        elements.update(elements.indexOf(baseForUpdate), (T) model);

        return model;
    }

    @Override
    public boolean delete(String id) {

        boolean returnValue = false;

        T baseForDelete = (T) this.findByBaseObjectsInternalId(id);

        if (baseForDelete != null) {
            T oldValue = elements.delete(elements.indexOf(baseForDelete));
            returnValue = true;
        }

        return returnValue;
    }

    public T findByBaseObjectsInternalId(String id) {

        T returnValue = null;

        for (int ind = 0; ind < elements.size(); ind++) {
            Base currElement = (Base) elements.get(ind);
            if (currElement.getId() == id) {
                returnValue = (T) currElement;
            }
        }
        return returnValue;
    }

}