package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private User[] users = new User[10];
    private int index = 1;

    public synchronized boolean add(User user) {
        boolean result = false;
        if (index < users.length) {
            users[index] = user;
            result = true;
            index++;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        for (int i = 0; i < index; i++) {
            if (users[i].id == user.id) {
                users[i].amount = user.amount;
                result = true;
            }
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        for (int i = 0; i < users.length; i++) {
            if (users[i].id == user.id) {
                User[] copy = new User[users.length - 1];
                users = copy;
                result = true;
                index--;
            }
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int told, int amount) {
        boolean result = false;
        if (users[fromId].amount >= amount) {
            users[fromId].amount -= amount;
            users[told].amount += amount;
            result = true;
            System.out.println("from.amount = " + users[fromId].amount);
            System.out.println("to amount = " + users[told].amount);
        }
        return result;
    }

    /**
     * Метод вспомогательный для тестирования. Выдает сумму находящуюся на счету юзера с заданным id
     * @param id - идентификатор пользователя.
     * @return знаение amount в ячейке под номером id.
     */
    public synchronized int getAmount(int id) {
        return users[id].amount;
    }
}
