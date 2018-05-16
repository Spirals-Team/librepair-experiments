package io.joaopinheiro.userservice;

/**
 * Utility class that generates User objects with reasonable defaults. Used for testing
 *
 * @author Joao Pedro Pinheiro
 */

public class UserBuilder {

    private Long id = 1L;
    private String username = "User";
    private String email = "user@mail.com";


    public UserBuilder(){};

    public UserBuilder withID(Long id){
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username){
        this.username = username;
        return this;
    }

    public UserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public User build(){
        return new User(id,username, email);
    }
}
