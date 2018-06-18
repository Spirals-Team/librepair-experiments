package academia.business.account;

import academia.model.Account;

import java.util.List;

public interface AccountService {

    Account create(Account account);

    Account update(Account account);

    Account updatePassword(Long id, PasswordUpdateDto passwordUpdateDto);

    List<Account> getTrainers();

    List<Account> getCustomers();
}
