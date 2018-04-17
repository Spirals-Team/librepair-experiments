package com.isa.instaticketapi.service;

import com.isa.instaticketapi.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing current users
 *
 * @author sansajn
 */
@Service
public class ProfileService {

	private final Logger log = LoggerFactory.getLogger(ProfileService.class);

	@Autowired
	private UserRepository userRepository;

}
