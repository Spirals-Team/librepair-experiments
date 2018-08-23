package ru.job4j.generic;

/**
 * Class UserStore.
 *@author ifedorenko
 *@since 25.09.2017
 *@version 1
 */
public class UserStore extends AbstractStore {
    /**
     * @param user user for store
     */
    private User user;

    /**
     * Constructor.
     * @param array array
     */
    public UserStore(SimpleArray<User> array) {
        super(array);
    }
}
