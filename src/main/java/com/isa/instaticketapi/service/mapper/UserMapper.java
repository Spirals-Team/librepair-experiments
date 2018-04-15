package com.isa.instaticketapi.service.mapper;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.service.dto.account.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service helping faster converting DTO object to domain
 * @author sansajn
 */
@Service
public class UserMapper {

    /**
     * Converting from DTO to User object
     * @param userDTO DTO object
     * @return user domain object
     */
    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setNumber(userDTO.getNumber());
            user.setAddress(userDTO.getAddress());
            user.setCity(userDTO.getCity());
            if(userDTO.getCreatedBy() == null) {
                user.setCreatedBy(user.getUsername());
            }
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            if (authorities != null) {
                user.setAuthorities(authorities);
            }
            return user;
        }
    }

    /**
     * Converting authoritis from string
     * @param strings authoritis
     * @return collection of type Authority
     */
    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        if (strings != null) {
            return strings.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        } else
            return null;
    }

}
