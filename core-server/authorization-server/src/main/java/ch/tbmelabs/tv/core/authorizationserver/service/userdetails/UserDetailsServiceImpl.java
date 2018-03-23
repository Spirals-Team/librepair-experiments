package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);

  @Autowired
  private UserCRUDRepository userRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(String username) {
    LOGGER.debug("Loading userdetails for username \"" + username + "\"");

    User user;
    if ((user = userRepository.findByUsername(username)) == null) {
      throw new UsernameNotFoundException("Username " + username + " does not exist!");
    }

    return new UserDetailsImpl(user);
  }
}