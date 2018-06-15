package ru.job4j.bank;

import ru.job4j.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Bank {
    private Map<User, ArrayList<Account>> userListMap = new TreeMap<>();

    public void addUser(User user) {
        this.userListMap.putIfAbsent(user, new ArrayList<>());

    }

    public void deleteUser(User user) {
        this.userListMap.remove(user);
    }
    public void addAccountToUser(String passport, Account account) {
           for (User user: this.userListMap.keySet()) {
               if (user.getPasport().equals(passport)) {
                   this.userListMap.get(user).add(account);
               }
           }

        }

    public void deleteAccountFromUser(User user, Account account) {
        this.userListMap.get(user).remove(account);
    }

    public List<Account> getUserAccount(String passport) {
           List<Account> list = new ArrayList<>();
           for (User key: this.userListMap.keySet()) {
              if (key.getPasport().equals(passport)) {
                  list = this.userListMap.get(key);
              }

           }
           return list;
    }

    public boolean getUserRequisites(String requisites) {
        boolean equals = false;
        for (User user: this.userListMap.keySet()) {
            if (this.userListMap.get(user).contains(getAccountActual(requisites))) {
                equals = true;
        }

        }
        return equals;
    }

    public boolean getUserPassport(String passport) {
        boolean equals = false;
        for (User user: this.userListMap.keySet()) {
            if (user.getPasport().equals(passport)) {
                equals = true;
            }

        }
        return equals;
    }

    private Account getAccountActual(String requisites) {
        Account actual = new Account();
        for (ArrayList<Account> list : this.userListMap.values()) {
            for (Account account: list) {
                if (account.getRequisites().equals(requisites)) {
                    actual = account;
            }

            }
        }
        return actual;
    }
    public boolean transferMoney(String srcPassport, String srcRequisite, String destPassport, String destRequisite, double amount) {
       return   getUserPassport(destPassport) && getUserPassport(srcPassport) && getUserRequisites(srcRequisite) && getUserRequisites(destRequisite) &&  getAccountActual(srcRequisite).transfer(getAccountActual(destRequisite), amount);
    }
}
