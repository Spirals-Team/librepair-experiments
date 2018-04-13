package m2dl.ivvq.sortircesoir.controller;

import m2dl.ivvq.sortircesoir.domain.User;
import m2dl.ivvq.sortircesoir.helpers.AuthentHelper;
import m2dl.ivvq.sortircesoir.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    private UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository= userRepository;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signIn(@RequestParam(value = "login")String login, @RequestParam(value = "password")String password) {
        User user = userRepository.findByLoginAndPassword(login, password);

        if (user != null) {
            String userToken = AuthentHelper.createJWT(user.getId());
            if (userToken == null)
                return "Echec de la création du token, utilisateur non authentifié.";

            user.setToken(userToken);
            userRepository.save(user);
            return "Connecté ! Ton token : " + userToken +"\n";
        }
        return "Pas d'utilisateur correspondant à ces identifiants";
    }
}
