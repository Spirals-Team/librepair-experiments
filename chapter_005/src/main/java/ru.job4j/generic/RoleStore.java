package ru.job4j.generic;

//2. Сделать два класса хранилища UserStore и RoleStore. Внутри для хранения данных использовать SimpleArray.
public class RoleStore<T extends Base> extends AbstractStore<T> implements Store<T> {

    public RoleStore(int numberOfElements) {
        super(numberOfElements);
    }
}
