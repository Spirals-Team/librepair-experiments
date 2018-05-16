package io.joaopinheiro.userservice.service;

import io.joaopinheiro.userservice.repository.UserRepository;
import io.joaopinheiro.userservice.service.errors.UserAlreadyExistsException;
import io.joaopinheiro.userservice.service.errors.UserMalformedException;
import io.joaopinheiro.userservice.service.errors.UserNotFoundException;
import io.joaopinheiro.userservice.service.errors.UserUpdateErrorException;
import io.joaopinheiro.userservice.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repo){
        this.userRepository = repo;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserByID(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    public User createUser(User user) {
        if(user.getId() != null && userRepository.findById(user.getId()).isPresent()) {
            throw new UserAlreadyExistsException(user.getId());
        }
        validateUser(user);

        User result = userRepository.save(user);
        return result;
    }

    public User updateUser(User user, Long id){
        userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        validateUser(user);

        if(user.getId() != null && !user.getId().equals(id))
            throw new UserUpdateErrorException();

        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    public void validateUser(User user){
        String email =  user.getEmail();
        if(!validateEmail(email)){
            throw new UserMalformedException(email + "is not a valid email address!");
        }
    }

    public boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

}
