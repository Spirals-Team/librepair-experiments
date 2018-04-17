package com.isa.instaticketapi.security;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        Optional<User> userByUsernameFromDatabase = userRepository.findOneByUsername(username);
        return userByUsernameFromDatabase.map(user -> createSpringSecurityUser(user)).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " was not found in the " +
                        "database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + user.getUsername() + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
