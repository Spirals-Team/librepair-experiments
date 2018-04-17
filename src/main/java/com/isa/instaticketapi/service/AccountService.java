package com.isa.instaticketapi.service;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.account.UserDTO;
import com.isa.instaticketapi.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;



/**
 * Service class for managing account.
 */
@Service
@Transactional
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * Registrating user into database
     *
     * @param user     object with user information
     * @param password password which will be encoded
     */
    public void signupUser(User user, String password) {
        log.debug("start of reg {}", user);

        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        user.setCreatedBy(user.getUsername());

        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user.setActivated(false);
        user.setActivationKey(RandomUtil.generateActivationKey());
        userRepository.save(user);

        log.debug("Created Information for User: {}", user);
    }

    /**
     * Activate user account with key
     *
     * @param key user's activation key
     * @return user with activated account
     */
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    /**
     * @param mail email of user that requested reset password
     * @return user with generated reset key
     */
    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::getActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    return user;
                });
    }

    /**
     * Changing password for current user
     *
     * @param password password that will replace old one
     */
    public void changePassword(String password) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUsername)
                .ifPresent(user -> {
                    String encryptedPassword = passwordEncoder.encode(password);
                    user.setPassword(encryptedPassword);
                    log.debug("Changed password for User: {}", user);
                });
    }

    /**
     * Update basic information (first name, last name, email) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     * @param imageUrl  image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUsername)
                .ifPresent(user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setImageUrl(imageUrl);
                    log.debug("Changed Information for User: {}", user);
                });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<User> updateUser(UserDTO userDTO) {
        return userRepository
                .findOneByEmailIgnoreCase(userDTO.getEmail())
                .map(user -> {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setCity(userDTO.getCity());
                    user.setAddress(userDTO.getAddress());
                    user.setNumber(userDTO.getNumber());
                    user.setImageUrl(userDTO.getImageUrl());
                    return user;
                });
    }

    /**
     * Deleting user from database
     *
     * @param password representing password of user which will be deleted
     */
    public void deleteAccount(String password) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).ifPresent(user -> {
            if (user.getPassword().equals(password)) {
                userRepository.delete(user);
                log.debug("Deleted User: {}", user);
            }
            throw new IllegalArgumentException("Invalid password");
        });
    }

    /**
     * Returns current/logged user with Authorities
     *
     * @return user if its logged
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    /**
     * Removing temp registred users a.k.a. guests
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getUsername());
            userRepository.delete(user);
        }
    }

}
