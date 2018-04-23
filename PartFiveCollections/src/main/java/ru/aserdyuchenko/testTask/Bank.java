package ru.aserdyuchenko.testTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anton on 05.05.17.
 */
public class Bank {
    /**
     * @param collection - коллекция всех пользователей и их счетов.
     */
    private Map<User, List<Account>> collection = new HashMap<>();

    /**
     * Добавление нового пользователя.
     * @param user - новый пользователь.
     */
    public void addUser(User user) {
        List<Account> accounts = new ArrayList<>();
        collection.put(user, accounts);
    }

    /**
     * Удаление пользователя.
     * @param user - пользователь, с которым будет расторгнут договор.
     */
    public void deleteUser(User user) {
        collection.remove(user);
    }

    /**
     * Добавить счёт пользователю.
     * @param user - Пользователь, которому будет добавлен счёт.
     * @param account - Счёт, который добавляется пользователю.
     */
    public void addAccountToUser(User user, Account account) {
        List<Account> accounts = getUserAccounts(user);
        accounts.add(account);
        collection.put(user, accounts);
    }

    /**
     * Удалить один счёт пользователя.
     * @param user - Пользователь у которого будет удален счёт.
     * @param account - Счёт, который будет удален у пользователя.
     */
    public void deleteAccountFromUser(User user, Account account) throws UserNotFoundException {
        if(collection.containsKey(user)) {
            List<Account> accounts = getUserAccounts(user);
            accounts.remove(account);
            collection.put(user, accounts);
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * Получить список счетов для пользователя.
     * @param user - Пользователь, чьи счета будут получены.
     * @return accounts - Счета, которые будут получены.
     */
    public List<Account> getUserAccounts(User user) {
        List<Account> accounts = collection.get(user);
        return accounts;
    }

    /**
     * Метод для перечисления денег с одного счёта на другой счёт.
     * @param srcUser - Пользователь, который отдает деньги.
     * @param srcAccount - Счёт, с которого списываются деньги.
     * @param dstUser - Пользователь, который получит деньги.
     * @param dstAccount - Счёт, на который будут перечислены деньги.
     * @param amount - Сумма для перевода.
     * @return result - Если счёт не найден или не хватает денег на счёте srcAccount(с которого переводят) должен вернуть false.
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) throws UserNotFoundException {
        boolean result = true;
        double newAccountValue = 0.0;
        int newAccountRequisites = 0;
        List<Account> accountsOfSrcUser = collection.get(srcUser);
        List<Account> accountsOfDstUser = collection.get(dstUser);
        if (accountsOfSrcUser.contains(srcAccount) && srcAccount.getValue() >= amount) {
            // Add money to dstUser.
            if(accountsOfDstUser.contains(dstAccount)) {
                newAccountValue = dstAccount.getValue() + amount;
                newAccountRequisites = dstAccount.getRequisites();
                accountsOfDstUser.set(accountsOfDstUser.indexOf(dstAccount), new Account(newAccountValue, newAccountRequisites));
            } else {
                newAccountValue = dstAccount.getValue() + amount;
                newAccountRequisites = dstAccount.getRequisites();
                addAccountToUser(dstUser, new Account(newAccountValue, newAccountRequisites));
            }
            // Remove money from srcUser.
            newAccountValue = srcAccount.getValue() - amount;
            newAccountRequisites = srcAccount.getRequisites();
            accountsOfSrcUser.set(accountsOfSrcUser.indexOf(srcAccount), new Account(newAccountValue, newAccountRequisites));
        } else {
            result = false;
        }
        return result;
    }

    public class UserNotFoundException extends Throwable {
    }
}
