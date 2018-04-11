package guru.bonacci.oogway.auth.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.auth.models.User;

@RestController
@RequestMapping("/users")
public class UserController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired 
	private MyUserService userService;

	@RequestMapping(value = "/current", method = GET)
    public Principal user(Principal user) {
        logger.info(user.getName() + " is under investigation");
        logger.debug("user info: " + user.toString());
        return user;
    }
	
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@RequestMapping(method = GET)
	public User getUserInfo(@RequestParam("apikey") String apiKey) {
		User u = userService.loadUserByApiKey(apiKey);
		u.setEncryptedPassword(u.getPassword()); // bit confusing...
		return u;
	}
}
