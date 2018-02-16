package ru.job4j.bankmanage;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * BankManageTest.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class BankManageTest {
    /*** Add user.*/
    @Test
    public void whenAddUserThenAddedUserInHashMap() {
        BankManage bankManage = new BankManage();
        User user = new User("Ivan", 123);
        bankManage.addUser(user);
        assertThat(bankManage.getManager().containsKey(user), is(true));
    }
    /*** Delete user.*/
    @Test
    public void whenDeleteUserThenUserIsDeleted() {
        BankManage bankManage = new BankManage();
        User user = new User("Ivan", 123);
        bankManage.addUser(user);
        bankManage.deleteUser(user);
        assertThat(bankManage.getManager().containsKey(user), is(false));
    }
    /*** Add account to user.*/
    @Test
    public void whenAddAccountToUserThenAccountIsAdded() {
        BankManage bankManage = new BankManage();
        User user = new User("Ivan", 123);
        bankManage.addUser(user);
        Account account = new Account(1000);
        bankManage.addAccountToUser(user, account);
        assertThat(bankManage.getManager().get(user).contains(account), is(true));
    }
    /*** Delete account from user.*/
    @Test
    public void whenDeleteAccountFromUserThenAccountIsDeleted() {
        BankManage bankManage = new BankManage();
        User user = new User("Ivan", 123);
        bankManage.addUser(user);
        Account account = new Account(1000);
        bankManage.addAccountToUser(user, account);
        bankManage.deleteAccountFromUser(user, account);
        assertThat(bankManage.getManager().get(user).contains(account), is(false));
    }
    /*** Get user account.*/
    @Test
    public void whenGetUserAccountThenReturnAccount() {
        BankManage bankManage = new BankManage();
        User user = new User("Ivan", 123);
        bankManage.addUser(user);
        Account account = new Account(1000);
        bankManage.addAccountToUser(user, account);
        List<Account> result = bankManage.getUserAccounts(user);
        assertThat(result, is(bankManage.getManager().get(user)));
    }
    /*** Transfer money to user.*/
    @Test
    public void whenTransferMoneyToUserThenUserGetMoney() {
        BankManage bankManage = new BankManage();
        User user1 = new User("Ivan", 123);
        User user2 = new User("Michael", 456);
        bankManage.addUser(user1);
        bankManage.addUser(user2);
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        bankManage.addAccountToUser(user1, account1);
        bankManage.addAccountToUser(user2, account2);
        bankManage.transferMoney(user1, account1, user2, account2, 999);
        assertThat(bankManage.getManager().get(user2).get(1).getValue(), is(1999.));
    }
}
