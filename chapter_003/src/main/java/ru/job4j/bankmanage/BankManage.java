package ru.job4j.bankmanage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * BankManage.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class BankManage {
    /*** List of users & accounts.*/
    private Map<User, List<Account>> manager = new HashMap<>();
    /**
     * Get map.
     * @return - manager
     */
    public Map<User, List<Account>> getManager() {
        return this.manager;
    }
    /**
     * Add user.
     * @param user - user
     */
    public void addUser(User user) {
        this.manager.put(user, new ArrayList<>(Collections.singletonList(new Account(0))));
    }
    /**
     * Delete user.
     * @param user - user
     */
    public void deleteUser(User user) {
        this.manager.remove(user);
    }
    /**
     * Add account to user.
     * @param user - user
     * @param account - account
     */
    public void addAccountToUser(User user, Account account) {
        this.manager.get(user).add(account);
    }
    /**
     * Delete account from user.
     * @param user - user
     * @param account - account
     */
    public void deleteAccountFromUser(User user, Account account) {
        this.manager.get(user).remove(account);
    }
    /**
     * Get user accounts.
     * @param user - user
     * @return - list of accounts
     */
    public List<Account> getUserAccounts(User user) {
        return this.manager.get(user);
    }
    /**
     * Transfer money to user.
     * @param srcUser - source user
     * @param srcAccount - source account
     * @param dstUser - distance user
     * @param dstAccount distance account
     * @param amount - amount of money
     * @return - true or false
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        for (Account src : this.getUserAccounts(srcUser)) {
            if (src.getRequisites().equals(srcAccount.getRequisites())) {
                if (src.getValue() >= amount) {
                    src.setValue(src.getValue() - amount);
                } else {
                    return false;
                }
                for (Account dst : this.getUserAccounts(dstUser)) {
                    if (dst.getRequisites().equals(dstAccount.getRequisites())) {
                        dst.setValue(dst.getValue() + amount);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
