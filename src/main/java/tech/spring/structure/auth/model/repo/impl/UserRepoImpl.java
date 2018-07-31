package tech.spring.structure.auth.model.repo.impl;

import static tech.spring.structure.auth.AuthConstants.PASSWORD_MAX_LENGTH;
import static tech.spring.structure.auth.AuthConstants.PASSWORD_MIN_LENGTH;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import tech.spring.structure.auth.exception.InvalidPasswordException;
import tech.spring.structure.auth.model.User;
import tech.spring.structure.auth.model.repo.UserRepo;
import tech.spring.structure.model.repo.impl.AbstractStructureRepoImpl;

public class UserRepoImpl extends AbstractStructureRepoImpl<User, UserRepo> {

    // TODO: break apart to return appropriate message
    private final static Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^]).{" + PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH + "}$");

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (isValid(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return super.create(user);
        }
        throw new InvalidPasswordException("Invalid password!");
    }

    private boolean isValid(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
