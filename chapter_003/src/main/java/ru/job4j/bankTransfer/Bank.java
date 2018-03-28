package ru.job4j.bankTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private Map<User, List<Account>> map = new HashMap<User, List<Account>>();

    private User getUserByPassport(String passport) {
        User result = new User();
        for (Map.Entry<User, List<Account>> entry : map.entrySet()) {
            if (entry.getKey().getPassport().equals(passport)) {
                result = entry.getKey();
            }
        }
        return result;
    }

    private boolean checkIfAccountExists(String passport) {
        boolean result = true;
        for (Map.Entry<User, List<Account>> entry : map.entrySet()) {
            if (entry.getValue() == null && entry.getKey().getPassport().equals(passport)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void makeTransfer(User user, Account account, int amount, int index) {
        for (int i = 0; i < map.get(user).size(); i++) {
            if (account.equals(map.get(user).get(i))) {
                map.get(user).get(i).setValue(map.get(user).get(i).getValue() + (index * amount));
            }
        }
    }

    public void addUser(User user) {
        map.put(user, new ArrayList<>());
    }

    public void deleteUser(User user) {
        map.remove(user);
    }

    public void addAccountToUser(String passport, Account account) {
        map.get(getUserByPassport(passport)).add(account);
    }

    public void deleteAccountFromUser(String passport, Account account) {
        map.get(getUserByPassport(passport)).remove(account);
    }

    public List<Account> getUserAccounts(String passport) {
        return map.get(getUserByPassport(passport));
    }

    public boolean transferMoney(String srcPassport, Account srcAccount, String destPassport, Account destAccount, int amount) {
        boolean result = false;
        User user1 = getUserByPassport(srcPassport);
        User user2 = getUserByPassport(destPassport);
        if  (checkIfAccountExists(srcPassport) &&
            checkIfAccountExists(destPassport) &&
            (srcAccount.getValue() - amount > 0)) {
            makeTransfer(user1, srcAccount, amount, -1);
            makeTransfer(user2, destAccount, amount, 1);
            result = true;
        }
        return result;
    }
}
