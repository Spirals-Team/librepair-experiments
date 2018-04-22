package com.acqua.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acqua.constants.ApiConstants;
import com.acqua.constants.PathConstants;
import com.acqua.entities.User;
import com.acqua.repositories.UserRepository;

@RestController
@RequestMapping(path = ApiConstants.API_V1_USER)
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(PathConstants.CREATE)
	public void signUp(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()) == null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		} else {

		}
	}

}
