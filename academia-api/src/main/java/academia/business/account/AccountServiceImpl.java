package academia.business.account;

import academia.domain.AccountType;
import academia.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        String password = account.getPassword();

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Senha deve ser preenchida");
        }

        Account existing = accountRepository.findByUsername(account.getUsername());
        if (existing != null) {
            throw new ValidationException("Nome de usuário já está em uso");
        }

        Account toSave = new Account(account.getName(), account.getUsername(), passwordEncoder.encode(account.getPassword()), account.getType(), account.getRestrictions());
        return accountRepository.save(toSave);
    }

    @Override
    public Account update(Account account) {
        Account existing = accountRepository.findById(account.getId()).get();
        // não atualizamos a senha nesse método
        Account toSave = new Account(account.getName(), account.getUsername(), existing.getPassword(), account.getType(), account.getRestrictions());
        toSave.setId(existing.getId());
        return accountRepository.save(toSave);
    }

    @Override
    public Account updatePassword(Long id, PasswordUpdateDto passwordUpdateDto) {
        Account existing = accountRepository.findById(id).get();

        if (passwordUpdateDto.getPassword() == null || passwordUpdateDto.getPassword().isEmpty()) {
            throw new ValidationException("Senha deve ser preenchida");
        }

        if (!passwordUpdateDto.getPassword().equals(passwordUpdateDto.getPasswordConfirmation())) {
            throw new ValidationException("Senha e confirmação devem ser iguais");
        }

        Account toSave = new Account(existing.getName(), existing.getUsername(), passwordEncoder.encode(passwordUpdateDto.getPassword()), existing.getType(), existing.getRestrictions());
        toSave.setId(existing.getId());
        return accountRepository.save(toSave);
    }

    @Override
    public List<Account> getTrainers() {
        return this.filterByType(AccountType.TRAINER);
    }

    @Override
    public List<Account> getCustomers() {
        return this.filterByType(AccountType.CUSTOMER);
    }

    private List<Account> filterByType(AccountType accountType) {
        return accountRepository
                .findAll()
                .stream()
                .filter(a -> a.getType() == accountType)
                .collect(Collectors.toList())
                ;
    }
}
