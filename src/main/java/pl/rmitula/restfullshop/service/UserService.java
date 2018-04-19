package pl.rmitula.restfullshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rmitula.restfullshop.exception.ConflictException;
import pl.rmitula.restfullshop.model.User;
import pl.rmitula.restfullshop.dao.UserRepository;
import pl.rmitula.restfullshop.exception.NotFoundException;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        User user = userRepository.findOne(id);

        if(user != null) {
            return user;
        } else {
            throw new NotFoundException("Not found user with id: " + id);
        }
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);

        if(user != null) {
            return user;
        } else {
            throw new NotFoundException("Not found user with username: " + username);
        }
    }

    public Long create(User user) {
        User username = userRepository.findByUsernameIgnoreCase(user.getUsername());
        User email = userRepository.findByEmailIgnoreCase(user.getEmail());

        if(username == null && email == null) {
            return userRepository.save(user).getId();
        } else {
            throw new ConflictException("This username or email is already associated with a different user account.");
        }
    }

    public void update(long id, String username, String firstName, String lastName, String email) {
        User user = userRepository.findOne(id);

        if(user != null) {
            User checkUsername = userRepository.findByUsernameIgnoreCase(username);

            if(checkUsername != null && checkUsername.getId() != id) {
                throw new ConflictException("This username is already associated with another user account.");
            }

            User checkEmail = userRepository.findByEmailIgnoreCase(email);

            if(checkEmail != null && checkEmail.getId() != id) {
                throw new ConflictException("This email is already associated with another user account.");
            }

            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            userRepository.save(user);
        } else {
            throw new NotFoundException("Not found user with id: " + id);
        }
    }

    public void delete(long id) {
        User user = userRepository.findOne(id);

        if(user != null) {
            userRepository.delete(id);
        } else {
            throw new NotFoundException("Not found user with id: " + id);
        }
    }

}
