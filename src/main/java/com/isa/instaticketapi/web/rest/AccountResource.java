package com.isa.instaticketapi.web.rest;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.isa.instaticketapi.service.dto.account.RequestPasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.DomainUserDetailsService;
import com.isa.instaticketapi.security.jwt.TokenProvider;
import com.isa.instaticketapi.service.MailService;
import com.isa.instaticketapi.service.AccountService;
import com.isa.instaticketapi.service.dto.account.ChangePasswordDTO;
import com.isa.instaticketapi.service.dto.account.LoginDTO;
import com.isa.instaticketapi.service.dto.account.UserDTO;
import com.isa.instaticketapi.service.mapper.UserMapper;
import com.isa.instaticketapi.web.rest.vm.AccountResource.JWTTokenResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/auth")
public class AccountResource {

	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MailService mailService;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DomainUserDetailsService domainUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	ServletContext servletContext;

	/**
	 * POST /signup : register new user
	 *
	 * @param userDTO object providing information about new user
	 * @throws HttpStatus 400 (Bad Request) if username is already used
	 * @throws HttpStatus 400 (Bad Request) if email is already used
	 */
	@ApiOperation(value = "Registration new user", response = HttpStatus.class)
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Succesfully created user"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
	@Transactional
	@PostMapping("/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public void signupAccount(@Valid @RequestBody UserDTO userDTO) {
		log.debug("REST request to sign-up User : {}", userDTO);

		if(!userDTO.getPassword().equals(userDTO.getRepassword()))
			throw new IllegalArgumentException("Password doesn't match");

		userRepository.findOneByUsername(userDTO.getUsername()).ifPresent(user -> {
			throw new IllegalArgumentException("Username is already used");
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getUsername()).ifPresent(user -> {
			throw new IllegalArgumentException("Email is already used");
		});
		User user = userMapper.userDTOToUser(userDTO);
		accountService.signupUser(user, userDTO.getPassword());
		mailService.sendActivationEmail(user);
	}

	/**
	 * POST /authenticate : Authenticate user
	 *
	 * @param loginDTO object providing information required for login
	 * @return
	 */
	@ApiOperation(value = "Authenticate user", response = JWTTokenResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Succesfully signed in"),
			@ApiResponse(code = 400, message = "Wrong credentials"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
	@PostMapping("/authenticate")
	public ResponseEntity<JWTTokenResponse> authorize(@Valid @RequestBody LoginDTO loginDTO) {
		UserDetails details = domainUserDetailsService.loadUserByUsername(loginDTO.getUsername());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDTO.getUsername(), loginDTO.getPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
		String jwt = tokenProvider.generateToken(details);
		return new ResponseEntity<>(new JWTTokenResponse(jwt, details.getUsername(), details.getAuthorities().toString()), HttpStatus.OK);
	}

	/**
	 * GET /authenticate : check if the user is authenticated, and return its
	 * login.
	 *
	 * @param request the HTTP request
	 * @return the login if the user is authenticated
	 */
	@ApiOperation(value = "Check if current user is authenticated", response = String.class)
	@ApiResponses(value = {@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
	@GetMapping("/authenticate")
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	/**
	 * GET /activate : activate the registered user.
	 *
	 * @param key the activation key
	 * @throws HttpStatus 400 (Bad Request) if the user couldn't be activated
	 */
	@ApiOperation(value = "Activate the registered user", response = HttpStatus.class)
	@ApiResponses(value = {@ApiResponse(code = 400, message = "User couldn't be activated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
	@GetMapping("/activate")
	public void activateAccount(@RequestParam(value = "key") String key) {
		Optional<User> user = accountService.activateRegistration(key);
		if (!user.isPresent()) {
			throw new IllegalArgumentException("No user was found for this reset key");
		}
	}

	/**
	 * GET /account : get the current user.
	 *
	 * @return the current user
	 * @throws HttpStatus 400 (Bad Request) if the user couldn't be returned
	 */
	@ApiOperation(value = "Get the current user", response = HttpStatus.class)
	@ApiResponses(value = {@ApiResponse(code = 400, message = "User couldn't be returned"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
	@GetMapping("/account")
	public UserDTO getAccount() {
		return accountService.getUserWithAuthorities().map(UserDTO::new)
				.orElseThrow(() -> new IllegalArgumentException("User could not be found"));
	}

	/**
	 * GET /logout : Logout current user.
	 *
	 * @throws HttpStatus 400 (Bad Request) if the user couldn't be returned
	 */
	@ApiOperation(value = "Loging out from SecurityContext", response = ResponseEntity.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Succesfully loged out"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.getContext().setAuthentication(null);
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * POST /change-password: Change password via requesting key
	 *
	 * @param key               generated key for changing password
	 * @param changePasswordDTO object with new password
	 */
	@ApiOperation(value = "Changing password after email", response = HttpStatus.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Succesfully cnagned password"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 400, message = "Link does not exist or passwords are not same")})
	@PostMapping("/change-password")
	@ResponseStatus(HttpStatus.OK)
	public void changePassword(@RequestParam(value = "key") String key,
							   @RequestBody ChangePasswordDTO changePasswordDTO) {
		if (changePasswordDTO.getPassword().equals(changePasswordDTO.getRePassword())) {
			User user = userRepository.findOneByResetKey(key);

			if (changePasswordDTO.getPassword().equals(changePasswordDTO.getRePassword())) {
				if (user != null) {
					user.setResetKey(null);
					String encryptedPassword = passwordEncoder.encode(changePasswordDTO.getPassword());
					user.setPassword(encryptedPassword);
				} else {
					throw new IllegalArgumentException("Link does not exist !");
				}
			} else {
				throw new IllegalArgumentException("Passwords are not the same !");
			}
		}
	}

	/**
	 * POST /request-password: Request for forgotten password
	 *
	 * @param requestPasswordDTO object with user's email
	 */
	@ApiOperation(value = "Requesting new password via email", response = HttpStatus.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "User with that email doesn't exist")
	})
	@PostMapping("/request-password")
	@ResponseStatus(HttpStatus.OK)
	public void requestNewPassword(@RequestBody RequestPasswordDTO requestPasswordDTO) {
		Optional<User> user = accountService.requestPasswordReset(requestPasswordDTO.getEmail());
		if (!user.isPresent())
			throw new IllegalArgumentException("User with that email doesn't exist");
		mailService.sendPasswordResetMail(user.get());
	}

	@ApiOperation(value = "Deleting user account", response = HttpStatus.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "Passwords are not matching")
	})
	@PostMapping("/delete-account")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAccount(@RequestBody ChangePasswordDTO changePasswordDTO) {
		if (changePasswordDTO.getPassword().equals(changePasswordDTO.getRePassword())) {
			accountService.deleteAccount(changePasswordDTO.getPassword());
		} else
			throw new IllegalArgumentException("Passwords are not matching");
	}


	/**
	 * PUT /edit-account: Method for updating account basic information
	 *
	 * @param UserDTO  object providing information about new user
	 */
	@ApiOperation(value = "Changing basic account information", response = HttpStatus.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok"),
			@ApiResponse(code = 400, message = "User updated failed,field validation")
	})
	@PutMapping("/edit-account")
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public void updateAccount(@RequestBody UserDTO userDTO) {
		Optional<User> user = accountService.updateUser(userDTO);
		System.out.println(user.get().getAddress());
		if(!user.isPresent())
			throw new IllegalArgumentException("User updated failed,field validation");
	}

}


