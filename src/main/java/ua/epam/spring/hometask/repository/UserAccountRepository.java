package ua.epam.spring.hometask.repository;

import ua.epam.spring.hometask.domain.UserAccount;

import java.util.Collection;

public interface UserAccountRepository {
    boolean buy(UserAccount userAccount, double price);
    UserAccount save(UserAccount userAccount, long userId);
    boolean delete(UserAccount userAccount, long userId);
    UserAccount get(long id, long userId);
    UserAccount getByName(String name, long userId);
    Collection<UserAccount> getAll(long userId);
}
