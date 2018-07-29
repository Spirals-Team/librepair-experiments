package com.longmaple.ttmall.address.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.ttmall.address.data.Address;
import com.longmaple.ttmall.address.data.EMallUser;

@RestController
public class AddressController {
	
	public static final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private AddressRepo addrRepo;

	@GetMapping("/address")
	@PreAuthorize("#oauth2.hasScope('user:profile:read')")
	public ResponseEntity<Address> getAddress(Authentication auth) {
		EMallUser user = (EMallUser) auth.getPrincipal();
		Address addr = addrRepo.findByUserId(user.getId());
		logger.debug("The address = " + addr);
		return ResponseEntity.ok(addr);
	}
}
