package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.repository.UserAccountRepository;
import ua.epam.spring.hometask.util.exception.NotFoundException;
import ua.epam.spring.hometask.util.exception.PaymentException;

import javax.annotation.Nonnull;
import java.util.Collection;

@Transactional(readOnly = true)
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository accountRepository;

    @Transactional
    @Override
    public void buy(UserAccount userAccount, double price) throws PaymentException {
        if (!accountRepository.buy(userAccount, price)) {
            throw new PaymentException("Account " + userAccount + " doesn't have enough money to buy tickets!");
        }
    }

    @Transactional
    @Override
    public UserAccount save(@Nonnull UserAccount object, long userId) {
        UserAccount result = accountRepository.save(object, userId);
        if (result == null) {
            throw new NotFoundException("Account " + object + " for user_id=" + userId + " wasn't created/updated.");
        }
        return result;
    }

    @Transactional
    @Override
    public void remove(@Nonnull UserAccount object, long userId) {
        boolean found = accountRepository.delete(object, userId);
        if (!found) {
            throw new NotFoundException("Account " + object + " for user_id=" + userId + " wasn't found for deleting.");
        }

    }

    @Override
    public UserAccount getById(@Nonnull Long id, long userId) {
        UserAccount result = accountRepository.get(id, userId);
        if (result == null) {
            throw new NotFoundException("Account with id=" + id + " and user_id=" + userId + " wasn't found.");
        }
        return result;

    }

    @Override
    public UserAccount getByName(String name, long userId) {
        UserAccount result = accountRepository.getByName(name, userId);
        if (result == null) {
            throw new NotFoundException("Account with name=" + name + " and user_id=" + userId + " wasn't found.");
        }
        return result;

    }


    @Nonnull
    @Override
    public Collection<UserAccount> getAll(long userId) {
        return accountRepository.getAll(userId);
    }
}
