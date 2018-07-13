package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ThreadSafe
/**
 * Класс хранилища пользователей
 */
public class UserStorage {
    @GuardedBy("this")
    // хранилище
    private Set<User> users = new HashSet<>();

    /**
     * Метод добавляет пользователей в хранилище (синхронизирован)
     * @param user пользователь
     * @return успех операции
     */
    public synchronized boolean add (User user) {
        return users.add(user);
    }

    /**
     * Метод изменяет параметры пользователя в хранилище (синхронизирован)
     * @param user пользователь
     * @return успех операции
     */
    public synchronized boolean update(User user) {
        boolean result = false;
        Optional<User> temp = findById(user.getId());
        if (temp.isPresent()) {
            temp.get().setAmount(user.getAmount());
            result = true;
        }
        return result;
    }

    /**
     * Метод удаляет пользователя в хранилище (синхронизирован)
     * @param user пользователь
     * @return успех операции
     */
    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    /**
     * Метод ищет пользователя по id в хранилище
     * Не синхронизирован так как метод приват и вызывается из синх методов, а следовательно экземпляр заблокирован
     * @param id id пользователя
     * @return пользователь в обертке
     */
    private Optional<User> findById(int id) {
        return users.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }

    /**
     * Метод переводит средства от одного пользователя другому (синхронизирован)
     * @param fromId id первого пользователя
     * @param toId id вторго пользователя
     * @param amount перевод
     * @return успех операции
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<User> userA = findById(fromId);
        Optional<User> userB = findById(toId);
        if (userA.isPresent() && userB.isPresent() && userA.get().getAmount() - amount >= 0 ) {
            userA.get().setAmount(userA.get().getAmount() - amount);
            userB.get().setAmount(userB.get().getAmount() + amount);
            result = true;
        }
        return result;
    }
}
