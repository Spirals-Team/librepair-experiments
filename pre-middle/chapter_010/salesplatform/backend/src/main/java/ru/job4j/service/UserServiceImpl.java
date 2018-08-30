package ru.job4j.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.repository.UserRepository;
import ru.job4j.model.User;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(final UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User findByEmailPassw(final String email, final String passw) {
        return repo.findByEmailIgnoreCaseAndPassw(email, passw)
            .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public User findByEmail(final String email) {
        return repo.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public boolean isCredential(final String email, final String passw) {
        return repo.findByEmailIgnoreCaseAndPassw(email, passw).isPresent();
    }

    @Override
    public Long create(final User user) {
        return repo.save(user).getId();
    }
}
