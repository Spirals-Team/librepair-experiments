package tech.spring.structure.auth.model.request;

import static tech.spring.structure.auth.AuthConstants.PASSWORD_MAX_LENGTH;
import static tech.spring.structure.auth.AuthConstants.PASSWORD_MIN_LENGTH;
import static tech.spring.structure.auth.AuthConstants.USERNAME_MAX_LENGTH;
import static tech.spring.structure.auth.AuthConstants.USERNAME_MIN_LENGTH;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import tech.spring.structure.scaffold.ScaffoldProperty;
import tech.spring.structure.scaffold.Scaffolding;

@Scaffolding()
public class LoginRequest {

    @ScaffoldProperty(autocomplete = "username", autofocus = true)
    @NotNull(message = "Username is required!")
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH)
    private String username;

    @ScaffoldProperty(type = "password", autocomplete = "new-password")
    @NotNull(message = "Password is required!")
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public LoginRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
