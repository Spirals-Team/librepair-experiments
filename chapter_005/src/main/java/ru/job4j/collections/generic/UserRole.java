package ru.job4j.collections.generic;

/**
 * Хранилище UserRole.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 */
public class UserRole extends AbstractStore<Role> {
    /**
     * Констроуктор класса.
     * @param size размер хранилища.
     */
    public UserRole(int size) {
        super(size);
    }
}
