package com.cmpl.web.core.user;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.common.user.ActionToken;
import com.cmpl.web.core.common.user.ActionTokenService;

@CacheConfig(cacheNames = "users")
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User> implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
  private static final String creationDateParameter = "creationDate";

  private final UserRepository userRepository;
  private final ActionTokenService tokenService;
  private final UserMailService userMailService;

  public UserServiceImpl(ApplicationEventPublisher publisher, ActionTokenService tokenService,
      UserMailService userMailService, UserRepository userRepository) {
    super(userRepository, publisher);
    this.tokenService = tokenService;
    this.userMailService = userMailService;
    this.userRepository = userRepository;
  }

  @Override
  @CachePut(key = "#a0.id")
  public UserDTO updateEntity(UserDTO dto) {
    UserDTO updatedUser = super.updateEntity(dto);
    return updatedUser;
  }

  @Override
  protected UserDTO toDTO(User entity) {
    UserDTO dto = UserDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected User toEntity(UserDTO dto) {
    User entity = UserBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  public UserDTO findByLogin(String login) {
    User user = userRepository.findByLogin(login);
    if (user == null) {
      return null;
    }
    return toDTO(user);
  }

  @Override
  public UserDTO findByEmail(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      return null;
    }
    return toDTO(user);
  }

  @Override
  @Cacheable(value = "pagedUsers")
  public Page<UserDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  @Cacheable(value = "listedUsers")
  public List<UserDTO> getUsers() {

    return toListDTO(userRepository.findAll(new Sort(Direction.ASC, "login")));
  }

  @Override
  public void askPasswordChange(long userId, Locale locale) throws Exception {
    UserDTO user = getEntity(userId);
    userMailService.sendChangePasswordEmail(user, generatePasswordResetToken(user), locale);
  }

  @Override
  @CacheEvict(value = {"pagedUsers", "listedUsers"}, allEntries = true)
  public UserDTO createUser(UserDTO dto, Locale locale) {
    UserDTO createdUser = createEntity(dto);
    try {
      userMailService.sendAccountCreationEmail(createdUser, generateActivationToken(createdUser), locale);
    } catch (Exception e) {
      LOGGER.error("Impossible d'envoyer le mail d'activation de compte", e);
    }
    return createdUser;
  }

  @Override
  @Cacheable(key = "#a0")
  public UserDTO getEntity(Long userId) {
    UserDTO fetchedUser = super.getEntity(userId);

    return fetchedUser;
  }

  @Override
  @Transactional
  @CacheEvict(value = {"pagedUsers", "listedUsers"}, allEntries = true)
  public UserDTO createEntity(UserDTO dto) {
    UserDTO createdUser = super.createEntity(dto);

    return createdUser;

  }

  @Override
  @CacheEvict(value = {"pagedUsers", "listedUsers"}, allEntries = true)
  @CachePut(key = "#a0")
  public UserDTO updateLastConnection(Long userId, LocalDateTime connectionDateTime) {
    Optional<User> result = userRepository.findById(userId);
    if (!result.isPresent()) {
      return null;
    }
    UserDTO user = toDTO(result.get());
    user.setLastConnection(connectionDateTime);
    user = toDTO(userRepository.save(toEntity(user)));

    return user;

  }

  String generateActivationToken(UserDTO user) {
    ActionToken actionToken = new ActionToken();
    actionToken.setAction(UserService.USER_ACTIVATION);
    actionToken.setUserId(user.getId());
    actionToken.setExpirationDate(Instant.now().plus(3, ChronoUnit.DAYS));

    Map<String, String> additionalParameters = new HashMap<>();
    additionalParameters.put(creationDateParameter, Long.toString(Instant.now().toEpochMilli()));
    actionToken.setAdditionalParameters(additionalParameters);

    return tokenService.generateToken(actionToken);
  }

  @Override
  public String generatePasswordResetToken(UserDTO user) {
    ActionToken actionToken = new ActionToken();
    actionToken.setAction(UserService.USER_RESET_PASSWORD);
    actionToken.setUserId(user.getId());
    actionToken.setExpirationDate(Instant.now().plus(3, ChronoUnit.HOURS));

    Map<String, String> additionalParameters = new HashMap<>();
    additionalParameters.put(creationDateParameter, Long.toString(Instant.now().toEpochMilli()));
    actionToken.setAdditionalParameters(additionalParameters);

    return tokenService.generateToken(actionToken);
  }
}
