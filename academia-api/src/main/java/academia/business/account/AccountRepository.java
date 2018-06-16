package academia.business.account;

import academia.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called accountRepository
// CRUD refers Create, Read, Update, Delete

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

}