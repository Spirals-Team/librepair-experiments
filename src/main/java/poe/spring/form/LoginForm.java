package poe.spring.form;

import javax.validation.constraints.Size;

public class LoginForm {
    @Size(min = 2)
    private String login;
    @Size(min = 1)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}