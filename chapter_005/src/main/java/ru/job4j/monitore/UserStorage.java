package ru.job4j.monitore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private ArrayList<User> list = new ArrayList<>();

    public synchronized boolean add(User user) {
        return list.add(user);
    }

    public synchronized boolean update(User user) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == user.getId()) {
                list.set(i, user);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return list.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int indexFrom = list.indexOf(new User(fromId, 0));
        int indexTo = list.indexOf(new User(toId, 0));
        if (indexFrom < 0 || indexTo < 0 || list.get(indexFrom).getAmount() < amount) {
            return false;
        }
        if (!update(new User(list.get(indexFrom).getId(), list.get(indexFrom).getAmount() - amount))) {
            return false;
        }
        if (!update(new User(list.get(indexTo).getId(), list.get(indexTo).getAmount() + amount))) {
            return false;
        }
        return true;
    }

    public synchronized void seeAll() {
        for (User user: list) {
            System.out.println(String.format("id:%d $%d", user.getId(), user.getAmount()));
        }
        System.out.println();
    }
}
