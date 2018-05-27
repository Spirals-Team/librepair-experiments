package ru.job4j.ioc.task;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Component
public class MemoryStorage implements Storage<User> {
    private List<User> list = new ArrayList<>();

    @Override
    public boolean add(User entity) {
        System.out.println(String.format("Add %s in memory", entity));
        if (!find(entity)) {
            this.list.add(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean del(User entity) {
        System.out.println(String.format("Del %s in memory", entity));
        if (find(entity)) {
            this.list.remove(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean del(int id) {
        System.out.println(String.format("Del by id: %d in memory", id));
        User user = find(id);
        if (user != null) {
            this.list.remove(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean find(User entity) {
        System.out.println(String.format("Find %s in memory", entity));
        for (User user: list) {
            if (user.equals(entity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User find(final String name) {
        System.out.println(String.format("Find by name: %s in memory", name));
        for (User user: list) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User find(int id) {
        System.out.println(String.format("Find by id: %d in memory", id));
        for (User user: list) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        System.out.println(String.format("Get list User of memory (%s)", this.list));
        return this.list;
    }
}
