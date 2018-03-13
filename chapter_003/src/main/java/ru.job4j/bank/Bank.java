package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

//    Реализовать коллекцию Map <User, List<Account>>, обозначающую что у каждого пользователя может быть список банковских счетов.
//    Необходимо реализовать возможность перечислять деньги, как с одного счёта User на другой счёт того же User, так и на счёт другого User.
    Map<User, List<Account>> usersAccounts = new HashMap<User, List<Account>>();

    public void addUser(User user) {
        usersAccounts.put(user, new ArrayList<>());
    }

    public void deleteUser(User user) {
        usersAccounts.remove(user);
    }

//    public void addAccountToUser(User user, Account account) {} - добавить счёт пользователю.
    public void addAccountToUser(User user, Account account) {
        List<Account> userAccounts = this.getUserAccounts(user); //usersAccounts.get(user);
        userAccounts.add(account);
        //usersAccounts.put(user, userAccounts); ЛИШНЕЕ ДЕЙСТВИЕ!!!
    }

//    public void deleteAccountFromUser(User user, Account account) {} - удалить один счёт пользователя.
    public void deleteAccountFromUser(User user, Account account) {
        List<Account> userAccounts = this.getUserAccounts(user); //usersAccounts.get(user);
        userAccounts.remove(account);
        //usersAccounts.put(user, userAccounts); ЛИШНЕЕ ДЕЙСТВИЕ!!!
    }

//    public List<Accounts> getUserAccounts (User user) {} - получить список счетов для пользователя.
    public List<Account> getUserAccounts(User user) {
        List<Account> userAccounts = usersAccounts.get(user);
        return userAccounts;
    }

//    public boolean transferMoney (User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount)
//    - метод для перечисления денег с одного счёта на другой счёт:
//    если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят) должен вернуть false.
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {

        List<Account> srcUserAccounts = this.getUserAccounts(srcUser);
        List<Account> dstUserAccounts = this.getUserAccounts(dstUser);

        if (!srcUserAccounts.contains(srcAccount) || !dstUserAccounts.contains(dstAccount) || srcAccount.value < amount) {
            return false;
        } else {
            srcAccount.value = srcAccount.value - amount;
            dstAccount.value = dstAccount.value + amount;
            return true;
        }
    }
}
