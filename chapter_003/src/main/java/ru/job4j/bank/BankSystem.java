package ru.job4j.bank;

import java.util.*;

public class BankSystem {
    private Map<User, List<Account>> repository = new HashMap<User, List<Account>>();


    /**
     * Метод добавляет нового пользователя.
     *
     * @param user
     */
    public void addUser(User user) {
        this.repository.putIfAbsent(user, new ArrayList<Account>());
    }

    /**
     * Метод удаляет пользователя.
     *
     * @param user
     */
    public void deleteUser(User user) {
        this.repository.remove(user);
    }

    /**
     * Вспомогательный метод, возвращает спиок пользователей по указаному ключу.
     *
     * @param passport - ключ, по которому осуществяется поиск.
     */
    public User getUser(String passport) {
        User result = new User();
        for (User user : this.repository.keySet()) {
            if (passport.equals(user.getPassport())) {
                result = user;
            }
        }
        return result;
    }

    /**
     * Метод добавляет счёт пользователю.
     *
     * @param passport - ключ, по которому добавляют счет.
     * @param account  - счет пользоваетя.
     */
    public void addAccountToUser(String passport, Account account) {
        this.repository.get(getUser(passport)).add(account);
    }

    /**
     * Метод удаляет счёт пользователя.
     *
     * @param passport - ключ, по которому удаляет счёт.
     * @param account  - счет пользоваетя.
     */
    public void deleteAccountFromUser(String passport, Account account) {
        this.repository.get(getUser(passport)).remove(account);
    }

    /**
     * Метод возвращает спиок счетов пользователя по указаному ключу.
     */
    public List<Account> getUserAccount(String passport) {
        return this.repository.get(getUser(passport));
    }

    /**
     * Метод для перечисления денег с одного счёта на другой счёт.
     *
     * @param srcPassport   - пароль пользователя со счета, с которого переводят средства.
     * @param srcRequisites - реквизиты счета, с которого переводят средства.
     * @param destPassport  - пароль пользователя, на счет которого переводят средства.
     * @param dstRequisites - реквизиты счета, на который будут переводиться средства.
     * @param amount        - кол-во переведенных средств.
     * @return Если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят) должен вернуть false.
     */
    public boolean transferMoney(String srcPassport, String srcRequisites, String destPassport, String dstRequisites, double amount) {
        User srcUser = this.getUser(srcPassport);
        User dstUser = this.getUser(destPassport);
        return this.repository.get(srcUser).get(this.repository.get(srcUser).indexOf(new Account(0.0, srcRequisites))).transferMoney(
                this.repository.get(dstUser).get(this.repository.get(dstUser).indexOf(new Account(0.0, dstRequisites))), amount);
    }

    /**
     * @return Метод возвращает кол-во пользователей.
     */
    public Set<User> getAllUser() {
        return this.repository.keySet();
    }
}
