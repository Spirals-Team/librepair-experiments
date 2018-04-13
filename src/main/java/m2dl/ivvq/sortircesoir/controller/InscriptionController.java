package m2dl.ivvq.sortircesoir.controller;

import m2dl.ivvq.sortircesoir.domain.User;
import m2dl.ivvq.sortircesoir.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InscriptionController {

    private UserRepository userRepository;

    @Autowired
    public InscriptionController(UserRepository userRepository) {
        this.userRepository= userRepository;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Boolean> signUp(@RequestParam(value = "mail")String mail, @RequestParam(value = "login")String login, @RequestParam(value = "password")String password, @RequestParam(value = "passwordConfirmation")String passwordConfirmation) {

        List<User> userAlreadyExists = userRepository.findUserByLogin(login);

        if (password.equals(passwordConfirmation) && (userAlreadyExists == null || userAlreadyExists.size() == 0)) {
            User user = new User(mail, login, password);
            userRepository.save(user);
            return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

}
