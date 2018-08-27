package JoaoVFG.com.github.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.dto.request.LoginDTO;
import JoaoVFG.com.github.dto.request.insert.InsertLoginDTO;
import JoaoVFG.com.github.entity.security.User;
import JoaoVFG.com.github.security.JwtTokenProvider;
import JoaoVFG.com.github.service.security.UserService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UserService userService;

	@RequestMapping
	public ResponseEntity<?> autenticarLogin(@RequestBody LoginDTO loginDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = "Bearer " + tokenProvider.generateToken(authentication);
		
		
		return ResponseEntity.ok().body(jwt);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody InsertLoginDTO insertLoginDTO) {
		User user = userService.createUser(insertLoginDTO);
		return ResponseEntity.ok(user);
	}

}
