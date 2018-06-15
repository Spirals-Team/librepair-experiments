package ru.job4j.review;




import ru.job4j.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class Bank {
    private TreeMap<User, ArrayList<Account>> userArrayListTreeMap = new TreeMap<>(); // Думаю лучше использовать более
                                                                                     // развернутое название переммной

    public void addUser(User user) {
        this.userArrayListTreeMap.put(user, new ArrayList<>());
    }

    public void delete(User user) {
        this.userArrayListTreeMap.remove(user);
    }

    public void add(User user, Account account) {
        this.userArrayListTreeMap.get(user).add(account);
    }


    private Account getActualAccount(User user, Account account) {
        ArrayList<Account> list = this.userArrayListTreeMap.get(user);
        return list.get(list.indexOf(account));
    }

    public void deleteAccount(User user, Account account) {
        this.userArrayListTreeMap.get(user).remove(account);
    }

    public List<Account> getAccounts(User user) {
        return this.userArrayListTreeMap.get(user);
    }

    public boolean transfer(User user1, Account account1,
                                 User user2, Account account2, double amount) {
        return this.userArrayListTreeMap.get(user1).contains(account1)
                && this.userArrayListTreeMap.get(user2).contains(account2)
                && getActualAccount(user1, account1).transfer(
                getActualAccount(user2, account2), amount);
    }

    public String toString() {
        return "Bank{" + "accounts=" + userArrayListTreeMap + "}";
    }
}