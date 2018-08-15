package ru.job4j.generic;

/**
 * Class RoleStore.
 *@author ifedorenko
 *@since 25.09.2017
 *@version 1
 */
public class RoleStore extends AbstractStore {
    /**
     * @param role role for store
     */
    private Role role;

    /**
     * Constructor.
     * @param array array
     */
    public RoleStore(SimpleArray<Role> array) {
        super(array);
    }
}
