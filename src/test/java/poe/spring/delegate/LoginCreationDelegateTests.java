package poe.spring.delegate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginCreationDelegateTests {
    @Test
    public void checkLoginOK() {
        String login = buildStringWithSizeBetween(LoginCreationDelegate.MIN_LOGIN_LENGTH);
        assertThat(LoginCreationDelegate.isSizeValid(login)).isTrue();
        login = buildStringWithSizeBetween(LoginCreationDelegate.MAX_LOGIN_LENGTH);
        assertThat(LoginCreationDelegate.isSizeValid(login)).isTrue();
    }

    @Test
    public void checkLoginSizeKO() {
        String login = buildStringWithSizeBetween(LoginCreationDelegate.MIN_LOGIN_LENGTH - 1);
        assertThat(LoginCreationDelegate.isSizeValid(login)).isFalse();
        login = buildStringWithSizeBetween(LoginCreationDelegate.MAX_LOGIN_LENGTH + 1);
        assertThat(LoginCreationDelegate.isSizeValid(login)).isFalse();
    }

    private String buildStringWithSizeBetween(int size) {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += "0";
        }
        return str;
    }
}

