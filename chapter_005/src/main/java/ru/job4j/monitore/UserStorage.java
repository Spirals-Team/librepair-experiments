package ru.job4j.monitore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private ArrayList<User> list = new ArrayList<>();

    synchronized boolean add(User user) {
        list.add(user);
        return true;
    }

    synchronized boolean update(User user) {
        for (User elem : list) {
            if (elem == user) {
                elem = user;
            }
        }
        return true;
    }

    synchronized boolean delete(User user) {
        list.remove(user);
        return true;
    }

    synchronized void transfer(int fromId, int toId, int amount) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == fromId) {
                list.get(i).setAmount(list.get(i).getAmount() - amount);
            }
            if (list.get(i).getId() == toId) {
                list.get(i).setAmount(list.get(i).getAmount() + amount);
            }
        }
    }
}
