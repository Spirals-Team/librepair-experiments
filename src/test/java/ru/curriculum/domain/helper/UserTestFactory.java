package ru.curriculum.domain.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;

import java.util.UUID;

@Component
public class UserTestFactory {

    @Autowired
    private UserRepository userRepository;

    public User createTestUser() {
        return new User(
            "test",
            "123",
            "Иванов",
            "Иван",
            "Иванович");
    }

    public User createAndSaveRandomUser() {
        User user = new User(
            UUID.randomUUID().toString(),
            "123",
            "Иванов",
            "Иван",
            "Иванович"
        );

        return userRepository.save(user);
    }
}
