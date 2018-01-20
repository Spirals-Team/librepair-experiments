package ua.com.company.store.model.dto;

/**
 * Created by Владислав on 11.12.2017.
 */
public class SignUpDto {
    private String login;
    private String email;
    private String password;

    public SignUpDto(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SignUpDto signUpDto = (SignUpDto) o;

        if (!login.equals(signUpDto.login)) return false;
        if (email != null ? !email.equals(signUpDto.email) : signUpDto.email != null) return false;
        return password.equals(signUpDto.password);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + password.hashCode();
        return result;
    }
}
