package ru.job4j.bank;

import java.util.*;

/**
 * Класс, определяющий основные функции банка.
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class Bank {

    // хранилище клиентов банка с их счетами
    private Map<User, List<Account>> users = new HashMap<>();

    // гет хранилища
    public Map<User, List<Account>> getUsers() {
        return users;
    }

    /**
     * Метод добавляет клиента в хранилище.
     * @param user клиент
     * @throws AlreadyThereSuchUserException такой клиент уже есть
     */
    public void addUser(final User user) throws AlreadyThereSuchUserException {
        if (users.containsKey(user)) {
            throw new AlreadyThereSuchUserException();
        }
        users.put(user, new LinkedList<Account>());
    }

    /**
     * Метод удаляет пользователя из хранилища.
     * @param user клиент
     * @throws NoSuchUserException нет такого клиента
     */
    public void deleteUser(final User user) throws NoSuchUserException {
        if (!users.containsKey(user)) {
            throw new NoSuchUserException();
        }
        users.remove(user);
    }

    /**
     * Метод добавляет новый счет клиенту.
     * @param passport документ клиента
     * @param account счет
     * @throws NoSuchUserException нет клиента с таким паспортом
     */
    public void addAccountToUser(final String passport, final Account account) throws NoSuchUserException {
        User key = findUserByPassport(passport);
        List<Account> temp = users.get(key);
        temp.add(account);
        users.replace(key, temp);
    }

    /**
     * Метод удаляет счет с карточки коиента.
     * @param passport документ клиента
     * @param account счет
     * @throws NoSuchUserException нет клиента с таким паспортом
     * @throws NoSuchAccountException у клиента нет такого счета
     */
    public void deleteAccountFromUser(final String passport, final Account account) throws NoSuchUserException, NoSuchAccountException {
        User key = findUserByPassport(passport);
        List<Account> temp = users.get(key);
        if (!temp.remove(account)) {
            throw new NoSuchAccountException();
        }
        users.replace(key, temp);
    }

    /**
     * Метод возвращает список счетов с карточки клиента.
     * @param passport документ клиента
     * @return список со счетами клиента
     * @throws NoSuchUserException нет клиента с таким паспортом
     */
    public List<Account> getUserAccounts(final String passport) throws NoSuchUserException {
        return users.get(findUserByPassport(passport));
    }

    /**
     * Метод, который переводит деньи от одного клиента другому.
     * @param srcPassport документ клиента который переводит деньги
     * @param srcRequisite счет с которого переводят деньги
     * @param dstPassport документ клиента которому переводят деньги
     * @param dstRequisite счет на который переводят деньги
     * @param amount количество денег ))
     * @return перевод был успешным или нет
     * @throws NoSuchUserException проблемас паспортами
     * @throws NoSuchAccountException проблема со счетами
     */
    public boolean transferMoney(final String srcPassport, final String srcRequisite, final String dstPassport, final String dstRequisite, final double amount) throws NoSuchUserException, NoSuchAccountException {
        Account srcacc = findAccountByRequisite(srcRequisite);
        Account dstacc = findAccountByRequisite(dstRequisite);
        User srcuser = findUserByPassport(srcPassport);
        User dstuser = findUserByPassport(dstPassport);
        boolean result = srcuser.equals(findUserByRequisite(srcRequisite)) && dstuser.equals(findUserByRequisite(dstRequisite));
        List<Account> srcAccounts = users.get(srcuser);
        List<Account> dstAccounts = users.get(dstuser);
        if (srcacc.getValue() >= amount) {
            srcacc.setValue(srcacc.getValue() - amount);
            dstacc.setValue(dstacc.getValue() + amount);
            srcAccounts.set(srcAccounts.indexOf(srcacc), srcacc);
            dstAccounts.set(dstAccounts.indexOf(dstacc), dstacc);
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Внутренний метод для поиска клиента в хранилище по документу.
     * @param passport документ
     * @return клиент
     * @throws NoSuchUserException клиент не найден
     */
    private User findUserByPassport(final String passport) throws NoSuchUserException {
        User result = new User();
        for (User key:  users.keySet()) {
            if (key.getPassport().equals(passport)) {
                result = key;
                break;
            }
        }
        if (result == null) {
            throw new NoSuchUserException();
        }
        return result;
    }

    /**
     * Внутренний метод для поиска клиента в хранилище по рекизитам.
     * @param requisite реквизиты счета
     * @return клиент
     * @throws NoSuchUserException клиент не найден
     */
    private User findUserByRequisite(final String requisite) throws NoSuchUserException {
        User result = new User();
        boolean exit=false;
        for (User key: users.keySet()) {
            if (exit) {
                break;
            }
            for (Account acc: users.get(key)) {
                if (acc.getRequisites().equals(requisite)) {
                    result = key;
                    exit = true;
                    break;
                }
            }
        }
        if (result == null) {
            throw new NoSuchUserException();
        }
        return result;
    }

    /**
     * Внутренний метод для поиска счета по реквизитам (паблик - для тестов).
     * @param requisite реквизиты счета
     * @return счет клиента
     * @throws NoSuchAccountException счет не найден
     */
    public Account findAccountByRequisite(final String requisite) throws NoSuchAccountException {
        Account result = new Account();
        boolean exit=false;
        for (User key: users.keySet()) {
            if (exit) {
                break;
            }
            for (Account acc: users.get(key)) {
                if (acc.getRequisites().equals(requisite)) {
                    result = acc;
                    exit=true;
                    break;
                }
            }
        }
        if (result == null) {
            throw new NoSuchAccountException();
        }
        return result;
    }
}
