package ru.skorikov;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 09.01.18
 * @ version: java_kurs_standart
 * <p>
 * Класс структура данных для хранение пользователей.
 */
@ThreadSafe
class UserStorage {

    /**
     * Хранилище пользователей.
     */
    @GuardedBy("this")
    private List<User> list = new LinkedList<>();

    /**
     * Добавить user.
     *
     * @param user user.
     * @return addOrNo.
     */
    synchronized boolean add(User user) {
        boolean isAdd = false;

        if (user != null) {
            list.add(user);
            isAdd = true;
        }
        return isAdd;
    }

    /**
     * Обновить user.
     *
     * @param user user.
     * @return updateOrNo.
     */
    synchronized boolean update(User user) {
        boolean isUpdate = false;

        if (user != null) {
            if (list.contains(user)) {
                list.remove(user);
                list.add(user);
                isUpdate = true;
            } else {
                throw new NoSuchElementException();
            }
        }
        return isUpdate;
    }

    /**
     * Delete user.
     *
     * @param user user.
     * @return deleteOrNo.
     */
    synchronized boolean delete(User user) {
        boolean isDelete = false;

        if (user != null) {
            if (list.contains(user)) {
                list.remove(user);
                isDelete = true;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
        return isDelete;
    }

    /**
     * Перевод одного user другому.
     *
     * @param fromId Id user1.
     * @param toId   Id user2.
     * @param amount сумма.
     */
    synchronized void transfer(int fromId, int toId, int amount) {
        User user1 = getUser(fromId);
        User user2 = getUser(toId);

        if (user1.getAmount() >= amount) {
            user1.setAmount(user1.getAmount() - amount);
            user2.setAmount(user2.getAmount() + amount);
        } else {
            System.out.println("No money");
        }
    }

    /**
     * Получить User из листа.
     *
     * @param id Id User.
     * @return User.
     */
    synchronized User getUser(int id) {
        User returnUser = list.get(list.indexOf(new User(id)));
        if (returnUser != null) {
            return returnUser;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
}
