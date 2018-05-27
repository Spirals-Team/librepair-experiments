package ru.job4j.bank;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Bank {
    HashMap<User, List<Account>> users = new HashMap<>();

    //добавление пользователя.
    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<Account>());
    }

    //удаление пользователя.
    public void deleteUser(User user) {
        this.users.remove(user);
    }

    //добавить счёт пользователю.
    public void addAccountToUser(String passport, Account account) {
        User newUser = new User("Test", passport);
        for (Map.Entry entry : users.entrySet()) {
            if (entry.getKey().hashCode() == newUser.hashCode()) {
                ArrayList<Account> list = (ArrayList<Account>) entry.getValue();
                list.add(account);
                break;
            }
        }
    }

    //удалить один счёт пользователя.
    public void deleteAccountFromUser(String passport, Account account) {
        User newUser = new User("Test", passport);
        for (Map.Entry entry : users.entrySet()) {
            if (entry.getKey().hashCode() == newUser.hashCode()) {
                ArrayList<Account> list = (ArrayList<Account>) entry.getValue();
                int index = list.indexOf(account);
                if (index != -1) {
                    list.remove(index);
                }
                break;
            }
        }
    }

    //получить список счетов для пользователя.
    public List<Account> getUserAccounts(String passport) {
        User newUser = new User("Test", passport);
        for (Map.Entry entry : users.entrySet()) {
            if (entry.getKey().hashCode() == newUser.hashCode()) {
                return (List<Account>) entry.getValue();
            }
        }
        return null;
    }

    //метод для перечисления денег с одного счёта на другой счёт:
    //если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят) вернет false.
    public boolean transferMoney(
            String srcPassport,
            String srcRequisite,
            String destPassport,
            String destRequisite,
            double amount) {
        boolean transferMoneyDone = false;
        User srcUser = new User("Test", srcPassport);
        Account srcAccount = new Account(0, srcRequisite);
        ArrayList<Account> srcList = new ArrayList<>();
        User destUser = new User("Test", destPassport);
        Account destAccount = new Account(0, destRequisite);
        ArrayList<Account> destList = new ArrayList<>();
        boolean srcFind = false;
        boolean destFind = false;
        for (Map.Entry entry : users.entrySet()) {
            if (!srcFind && entry.getKey().hashCode() == srcUser.hashCode()) {
                srcList = (ArrayList<Account>) entry.getValue();
                srcFind = true;
            } else if (!destFind && entry.getKey().hashCode() == destUser.hashCode()) {
                destList = (ArrayList<Account>) entry.getValue();
                destFind = true;
            }
            if (srcFind && destFind) {
                break;
            }
        }
        if (srcList.indexOf(srcAccount) != -1
                && destList.indexOf(destAccount) != -1
                && srcList.get(srcList.indexOf(srcAccount)).getValue() >= amount) {
            srcList.get(srcList.indexOf(srcAccount)).setValue(
                    srcList.get(srcList.indexOf(srcAccount)).getValue() - amount
            );
            destList.get(destList.indexOf(destAccount)).setValue(destList.get(
                    destList.indexOf(destAccount)).getValue() + amount
            );
            transferMoneyDone = true;
        }
        return transferMoneyDone;
    }
}
