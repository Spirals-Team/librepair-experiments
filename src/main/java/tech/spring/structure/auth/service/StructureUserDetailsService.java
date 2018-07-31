package tech.spring.structure.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tech.spring.structure.auth.details.StructureUserDetails;
import tech.spring.structure.auth.model.User;
import tech.spring.structure.auth.model.repo.UserRepo;

@Service
public class StructureUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return new StructureUserDetails(user.get());
        }
        throw new UsernameNotFoundException(System.out.format("User with username %s not found!\n", username).toString());
    }

}
