package academia.business.account;

import academia.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

// TODO implementar as validações adequadas nessa classe, assinaladas com TODO específico e outras necessárias
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
}
