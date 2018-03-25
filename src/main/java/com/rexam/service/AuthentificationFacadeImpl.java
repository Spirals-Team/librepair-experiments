package com.rexam.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.rexam.model.User;

@Component
public class AuthentificationFacadeImpl implements AuthentificationFacade {

	@Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
	
	public User getAuthUser() {
		return (User) getAuthentication().getPrincipal();
	}

}
