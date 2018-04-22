package persistence.services;

import model.User;
import org.springframework.transaction.annotation.Transactional;
import persistence.repositories.UserRepository;

import java.util.List;

public class UserService extends GenericService<User> {

    private UserRepository repository;

    public UserRepository getRepository() {
        return this.repository;
    }
    public void setRepository(final UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<User> filterUser(final String pattern){
        return this.getRepository().filterUser(pattern);
    }
}
