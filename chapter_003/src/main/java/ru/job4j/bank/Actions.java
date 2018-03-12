package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс Actions.
 */
public class Actions {
    /**
     * Коллекция Map.
     */
    private Map<User, List<Account>> clients = new HashMap<>();

    /**
     * Получим коллекцию пользователей и счетов.
     *
     * @return получим коллекцию пользователей и счетов.
     */
    public Map<User, List<Account>> getClients() {
        return clients;
    }

    /**
     * Метод добавления пользователя в коллекцию.
     *
     * @param user пользователь.
     */
    public void addUser(User user) {
        clients.put(user, new ArrayList<Account>());
    }

    /**
     * Метод удаления из коллекции.
     *
     * @param user пользователь.
     */
    public void deleteUser(User user) {
        clients.remove(user);
    }

    /**
     * Метод добавления счетов пользователю.
     *
     * @param user    пользователь.
     * @param account счет.
     */
    public void addAccountToUser(User user, Account account) {
        clients.get(user).add(account);
    }

    /**
     * Метод удаления счета у пользователя.
     *
     * @param user    пользователь.
     * @param account счет.
     */
    public void deleteAccountFromUser(User user, Account account) {
        List<Account> list = clients.get(user);
        list.remove(list.indexOf(account));
    }

    /**
     * Метод получения списка четов у пользователя.
     *
     * @param user пользователь.
     * @return вернем список счетов пользователя.
     */
    public List<Account> getUserAccounts(User user) {
        return clients.get(user);
    }

    /**
     * Метод денежных переводов между пользователями.
     *
     * @param srcUser    исходный пользователь.
     * @param srcAccount исходный счет пользователя.
     * @param dstUser    конечный пользователь.
     * @param dstAccount конечный счет пользователя.
     * @param amount     сумма перевода.
     * @return вернем true(false) в зависимости от успешности.
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        boolean transfer = false;
        if (clients.containsKey(srcUser) && clients.containsKey(dstUser)) {
            List<Account> listSrc = clients.get(srcUser);
            Account accountSrc = listSrc.get(listSrc.indexOf(srcAccount));
            List<Account> listDst = clients.get(dstUser);
            Account accountDst = listDst.get(listDst.indexOf(dstAccount));
            if (accountSrc.getValue() >= amount) {
                accountSrc.setValue(accountSrc.getValue() - amount);
                accountDst.setValue(accountDst.getValue() + amount);
            }
            transfer = true;
        }
        return transfer;
    }
}
