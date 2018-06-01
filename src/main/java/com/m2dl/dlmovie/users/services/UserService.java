package com.m2dl.dlmovie.users.services;

import com.m2dl.dlmovie.config.security.JwtTokenProvider;
import com.m2dl.dlmovie.config.security.requests.LoginRequest;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.repositories.MovieRepository;
import com.m2dl.dlmovie.users.domain.Role;
import com.m2dl.dlmovie.users.domain.RoleName;
import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.exceptions.AlreadyReportedException;
import com.m2dl.dlmovie.users.exceptions.DBException;
import com.m2dl.dlmovie.users.repositories.RoleRepository;
import com.m2dl.dlmovie.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private MovieRepository movieRepository;

    private RoleRepository roleRepository;

    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository,
                       MovieRepository movieRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new DBException("User Role not set."));

        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    public User getById(Long id) {
        return userRepository.findOne(id);
    }

    public void addFavorite(Long userId, Long movieId){
        User user = userRepository.findOne(userId);
        Movie movie = movieRepository.findOne(movieId);
        if (user.getFavorites().contains(movie)){
            throw new AlreadyReportedException("Movie already added in favorites");
        }
        user.getFavorites().add(movie);
        userRepository.save(user);
    }

    public void deleteFavorite(Long userId, Long movieId) {
        User user = userRepository.findOne(userId);
        Movie movie = movieRepository.findOne(movieId);
        user.getFavorites().remove(movie);
        userRepository.save(user);
    }

    public Set<Movie> getFavorites(Long userId) {
        User user = userRepository.findOne(userId);
        return user.getFavorites();
    }
}
