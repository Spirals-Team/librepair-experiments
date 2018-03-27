package  ru.job4j.multithreading.chash;

import java.util.concurrent.ConcurrentHashMap;


/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 15.11.17;
 * @version $Id$
 * @since 0.1
 */
public class NonBlockingCash {
    private ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();
    public void add(User user) {
      //  map.putIfAbsent(user.getId(), user);
        map.computeIfAbsent(user.getId(), integer -> user);
    }

    public void delete(User user) {
        map.remove(user.getId());
    }
    public void update(User user) {
        int v = user.getVersion();
        map.computeIfPresent(user.getId(), (integer, user1) -> {
            if ((v + 1) == user1.getVersion()) {
                throw new RuntimeException();
            }
            user1 = user;
            user1.setVersion(v + 1);
            return user1;
        });
    }
    public User get(int id) {
        return map.get(id);
    }
}

class Main {

    public static void main(String[] args) {

        User user = new User("Vasea", 1);
        NonBlockingCash non = new NonBlockingCash();
        non.add(user);
        User user1 = new User("Petea", 1);
        non.update(user1);
        System.out.println(non.get(1));

    }
}
