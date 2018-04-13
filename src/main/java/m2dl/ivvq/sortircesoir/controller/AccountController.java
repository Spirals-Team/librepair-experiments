package m2dl.ivvq.sortircesoir.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import m2dl.ivvq.sortircesoir.domain.User;
import m2dl.ivvq.sortircesoir.helpers.AuthentHelper;
import m2dl.ivvq.sortircesoir.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private UserRepository userRepository;

    @Autowired
    public AccountController(UserRepository userRepository) {
        this.userRepository= userRepository;
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public String getMyInformations(@RequestHeader("Authorization") String token) {
        DecodedJWT decodedToken = AuthentHelper.verifyJWT(token);
        if (decodedToken == null)
            return "Not authorized - failed to decode authent token";

        User my = userRepository.findOne(decodedToken.getClaim("userId").asLong());
        if (my == null)
            return "Not authorized - no user corresponding to the token";
        return "My user informations : " + my.getLogin() + " ; " + my.getEmail();
    }
}
