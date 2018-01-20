package ru.curriculum.domain.admin.domain.admin;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.curriculum.domain.admin.user.exceptions.IllegalPassword;
import ru.curriculum.domain.admin.user.entity.Password;

public class PasswordTest {
    private PasswordEncoder encoder;

    @Before
    public void setUp() {
        encoder = new BCryptPasswordEncoder(11);
    }

    @Test
    public void createPassword() {
        String passwordAsString = "123";
        Password password = new Password(passwordAsString);

        Assert.assertTrue(encoder.matches(passwordAsString, password.hash()));
    }

    @Test(expected = NullPointerException.class)
    public void tryCreatePasswordFromNull_mustBeException() {
        new Password(null);
    }

    @Test(expected = IllegalPassword.class)
    public void tryCreatePasswordByStringWithLessThreeCharacters_mustBeException() {
        String passwordAsString = "12";
        new Password(passwordAsString);
    }
}
