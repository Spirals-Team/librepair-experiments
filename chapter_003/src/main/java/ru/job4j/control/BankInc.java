package ru.job4j.control;

import java.util.*;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class BankInc {

    private HashMap<User, ArrayList<AccountBank>> treemap = new HashMap<User, ArrayList<AccountBank>>();

    /**
     * Метод добавляет юзера в банковскую систему.
     * @param user юзер
     */
    public void addUser(User user) {
         this.treemap.put(user, new ArrayList<AccountBank>());
    }

    /**
     * Метод удаляет юзера из системы.
     * @param user юзер, которого нужно удалить из системы
     */
    public void deleteUser(User user) {
        this.treemap.remove(user);
    }

    /**
     * Метод добавляет аккаунт пользователю.
     * @param passport паспортные данные, по которым находим нужного юзера, account аккаунт, который доавляем найденному юзеру
     */
    public void addAccountToUser(String passport, AccountBank account) {
        for (Map.Entry<User, ArrayList<AccountBank>> users : treemap.entrySet()) {
            if (users.getKey().getPassport().equals(passport)) {
                ArrayList<AccountBank> list = users.getValue();
                list.add(account);
            }
        }
    }

    /**
     * Метод удаляет аккаунт у пользователя.
     * @param passport паспортные данные, по которым находим юзера в системе, account аккаунт, который удаляем у юзера
     */
    public void deleteAccountFromUser(String passport, AccountBank account) {
        for (Map.Entry<User, ArrayList<AccountBank>> users : treemap.entrySet()) {
            if (users.getKey().getPassport().equals(passport)) {
                ArrayList<AccountBank> list = users.getValue();
                list.remove(account);
            }
        }
    }

    /**
     * Метод выдает все аккаунты юзера.
     * @param passport паспортные данные, по которым находим список аков для пользователя
     * @return Лист, состоящий из аков.
     */
    public List<AccountBank> getUserAccounts(String passport) {
        ArrayList<AccountBank> list = new ArrayList<AccountBank>();
        for (Map.Entry<User, ArrayList<AccountBank>> users : treemap.entrySet()) {
            if (users.getKey().getPassport().equals(passport)) {
                list = users.getValue();

            }
        }
        return list;
    }

    /**
     * Вспомогательный метод, находит аккаунт в списке аккаунтов.
     * @param list - список аков для пользователя, requisites реквизиты, по которым находим нужный ак среди всех аков пользователя
     * @return Лист, состоящий из элементов массива.
     */
    private AccountBank getAccount(List<AccountBank> list, String requisites) {
        AccountBank acRes = new AccountBank();
        for (AccountBank ac : list) {
            if (ac.getRequisites().equals(requisites)) {
                acRes = ac;
            }
        }
        return acRes;
    }

    /**
     * Метод, переводящий деньги от одного пользователя к другому.
     * @param srcPassport - паспортные денные отправителя, srcRequisite реквизиты отправителя
     * @param destPassport паспортные данные получателя,  dstRequisite - его реквизиты
     * @return false, если денег на счету отправителя меньше отпраляемой суммы, или получателя не существует в банковской системе.
     */
    public boolean transferMoney(String srcPassport,
                                 String srcRequisite,
                                 String destPassport,
                                 String dstRequisite,
                                 double amount) {
        return getAccount(getUserAccounts(srcPassport), srcRequisite).transfer(getAccount(getUserAccounts(destPassport), dstRequisite), amount);
    }
}
