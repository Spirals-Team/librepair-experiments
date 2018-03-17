package ru.job4j.litle.testtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Bank.
 * @author Hincu Andrei (andreih1981@gmail.com) by 19.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Bank {
    /**
     * хранилище всех вкладчиков, ключь user значение список счетов.
     */
    private HashMap<User, List<Account>> bank = new HashMap<User, List<Account>>();

    /**
     * Конструктор класса.
     */
    public Bank() {
    }

    /**
     * добовляет нового вкладчика.
     * @param user user.
     */
    public void addUser(User user) {
        if (user != null) {
            if (!bank.containsKey(user)) {
                bank.put(user, new ArrayList<Account>());
            }
        }
    }

    /**
     * удаление пользователя.
     * @param user user.
     */
    public void deleteUser(User user) {
        if (user != null) {
            if (bank.containsKey(user)) {
                bank.remove(user);
            }
        }
    }

    /**
     * добовляем новый счет пользователю.
     * @param user user.
     * @param account account
     */
    public void addAccountToUser(User user, Account account) {
        if (user != null && account != null) {
            if (bank.containsKey(user)) {
                List<Account> list = bank.get(user);
                if (!list.contains(account)) {
                    list.add(account);
                }
                bank.put(user, list);
            }
        }
    }

    /**
     * getter.
     * @return bank.
     */
    public HashMap<User, List<Account>> getBank() {
        return bank;
    }

    /**
     * Удаление акаунта у юсера.
     * @param user user.
     * @param account account.
     */
    public void deleteAccountFromUser(User user, Account account) {
        if (user != null && account != null) {
            if (bank.containsKey(user)) {
                List<Account> list = bank.get(user);
                if (list.contains(account)) {
                    list.remove(account);
                }
                bank.put(user, list);
            }
        }
    }

    /**
     * Метод возвращает список счетов вкладчика.
     * @param user вкладчик.
     * @return лист.
     */
    public List<Account> getUserAccounts(User user) {
        return bank.get(user);
    }

    /**
     * Перевод денег с одного счета на другой счет.
     * @param srcUser плательщик.
     * @param srcAccount счет плательщика.
     * @param dstUser получатель.
     * @param dstAccount счет получателя.
     * @param amount сумма платежа.
     * @return true or false.
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        boolean weHappy = false;
        if (srcUser != null && dstUser != null && srcAccount.getValue() > amount) {
            if (srcUser.equals(dstUser)) {
                List<Account> list = bank.get(srcUser);
                int indexSrsAcc = list.indexOf(srcAccount);
                int indexDistAcc = list.indexOf(dstAccount);
                srcAccount.transferFromAccount(amount);
                dstAccount.addToAccount(amount);
                list.set(indexSrsAcc, srcAccount);
                list.set(indexDistAcc, dstAccount);
                bank.put(srcUser, list);
                weHappy = true;

            } else {
                List<Account> srcList = bank.get(srcUser);
                List<Account> dstList = bank.get(dstUser);
                if (srcList.contains(srcAccount) && dstList.contains(dstAccount)) {
                    int indexSrc = srcList.indexOf(srcAccount);
                    int indexDrc = dstList.indexOf(dstAccount);
                    srcAccount.transferFromAccount(amount);
                    dstAccount.addToAccount(amount);
                    srcList.set(indexSrc, srcAccount);
                    dstList.set(indexDrc, dstAccount);
                    bank.put(srcUser, srcList);
                    bank.put(dstUser, dstList);
                    weHappy = true;
                }
            }
        }
        return weHappy;
    }
}
