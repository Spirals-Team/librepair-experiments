package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.util.exception.PaymentException;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface UserAccountService {

    void buy(UserAccount userAccount, double price) throws PaymentException;

    UserAccount save(@Nonnull UserAccount object, long userId);

    void remove(@Nonnull UserAccount object, long userId);

    UserAccount getById(@Nonnull Long id, long userId);

    UserAccount getByName(String name, long userId);

    @Nonnull
    Collection<UserAccount> getAll(long userId);
}
