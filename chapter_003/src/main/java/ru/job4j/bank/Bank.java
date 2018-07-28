package ru.job4j.bank;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Bank {
    private TreeMap<User, ArrayList<Account>> accounts = new TreeMap<>();

    public void addUser(User user) {
        this.accounts.put(user, new ArrayList<>());
    }

    public void deleteUser(User user) {
        this.accounts.remove(user);
    }

    public User findUserByPassport(String passport) {
        User cur = null;
        for (Map.Entry<User, ArrayList<Account>> e : accounts.entrySet()) {
            if ((e.getKey()).getPassport().equals(passport)) {
                cur = e.getKey();
                break;
            }
        }
        return cur;
    }

    public void addAccountToUser(String passport, Account account) {
        User cur = findUserByPassport(passport);
        if (cur != null) {
            this.accounts.get(cur).add(account);
        }
    }

    public void deleteAccountFromUser(String passport, Account account) {
        User cur = findUserByPassport(passport);
        if (cur != null) {
            this.accounts.get(cur).remove(account);
        }
    }

    public List<Account> getUserAccounts(String passport) {
        return accounts.getOrDefault(findUserByPassport(passport), null);
    }

    private Account findAccountByRequisite(String passport, String srcRequisite) {
        Account cur = null;
        List<Account> curList = this.getUserAccounts(passport);
        if (curList != null) {
            for (Account account : curList) {
                if (account.getRequisites().equals(srcRequisite)) {
                    cur = account;
                    break;
                }
            }
        }
        return cur;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite, String destPassport, String dstRequisite, double amount) {
        boolean result;
        User srcUser = findUserByPassport(srcPassport);
        User destUser = findUserByPassport(destPassport);
        List<Account> srcAccounts = getUserAccounts(srcUser.getPassport());
        List<Account> destAccounts = getUserAccounts(destUser.getPassport());
        Account srcAccount = this.findAccountByRequisite(srcPassport, srcRequisite);
        Account destAccount = this.findAccountByRequisite(destPassport, dstRequisite);
        if (
                srcAccounts == null
                || destAccounts == null
                || srcAccount == null
                || destAccount == null
                || srcAccount.getValue() < 0
                || srcAccount.getValue() < amount) {
            result = false;
        } else {
            srcAccount.changeBalance(-amount);
            destAccount.changeBalance(+amount);
            result = true;
        }
        return result;
    }
}
