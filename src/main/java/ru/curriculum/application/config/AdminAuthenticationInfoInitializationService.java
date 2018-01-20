package ru.curriculum.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.curriculum.domain.admin.user.entity.Role;
import ru.curriculum.domain.admin.user.repository.RoleRepository;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;

import javax.transaction.Transactional;

/*
 * При первом старте приложения создаем пользователя с ролью "администратор".
 */
@Component
public class AdminAuthenticationInfoInitializationService implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Value("${auth.admin.username}")
    private String username;
    @Value("${auth.admin.password}")
    private String password;
    private boolean alreadySetup = false;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        User user = new User(
                username,
                password,
                "Администратор",
                "Администратор",
                null);
        Role roleAdmin = roleRepository.findOne("admin");
        user.assignRole(roleAdmin);
        userRepository.save(user);

        User ivan = new User(
                "Balalaika",
                "123",
                "Софья",
                "Павловна",
                "Ириновская");
        User revy = new User(
                "Двурукая",
                "124",
                null,
                null,
                null);
        userRepository.save(ivan);
        userRepository.save(revy);

        alreadySetup = true;
    }
}
