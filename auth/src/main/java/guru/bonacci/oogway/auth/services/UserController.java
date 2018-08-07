package guru.bonacci.oogway.auth.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.auth.models.User;

@RestController
@RequestMapping("/users")
public class UserController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired 
	private CustomUserService userService;

	@GetMapping("/current")
    public Principal user(Principal user) {
        logger.info(user.getName() + " is under investigation");
        logger.debug("user info: " + user.toString());
        return user;
    }
	
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@GetMapping
	public User getUserInfo(@RequestParam("apikey") String apiKey) {
		User u = userService.loadUserByApiKey(apiKey);
		u.setEncryptedPassword(u.getPassword()); // bit confusing...
		return u;
	}
}
