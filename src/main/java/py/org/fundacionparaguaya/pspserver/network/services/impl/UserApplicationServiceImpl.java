package py.org.fundacionparaguaya.pspserver.network.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.network.dtos.UserApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.UserApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.UserApplicationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.UserApplicationRepository;
import py.org.fundacionparaguaya.pspserver.network.services.UserApplicationService;


@Service
public class UserApplicationServiceImpl implements UserApplicationService {

	private Logger LOG = LoggerFactory.getLogger(UserApplicationServiceImpl.class);

	private UserApplicationRepository userApplicationRepository;
	
	private final UserApplicationMapper userApplicationMapper;
	
	public UserApplicationServiceImpl(UserApplicationRepository userApplicationRepository, UserApplicationMapper userApplicationMapper) {
		this.userApplicationRepository = userApplicationRepository;
		this.userApplicationMapper = userApplicationMapper;
	}

	@Override
	public UserApplicationDTO updateUserApplication(Long userApplicationId, UserApplicationDTO userApplicationDTO) {
		checkArgument(userApplicationId > 0, "Argument was %s but expected nonnegative", userApplicationId);

		return Optional.ofNullable(userApplicationRepository.findOne(userApplicationId))
                .map(parameter -> {
                    BeanUtils.copyProperties(userApplicationDTO, parameter);
                    LOG.debug("Changed Information for User Application: {}", parameter);
                    return parameter;
                })
                .map(userApplicationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User Application does not exist"));
	}

	@Override
	public UserApplicationDTO addUserApplication(UserApplicationDTO userApplicationDTO) {
		UserApplicationEntity userApplication = new UserApplicationEntity();
		BeanUtils.copyProperties(userApplicationDTO, userApplication);
		UserApplicationEntity newUserApplication= userApplicationRepository.save(userApplication);
		return userApplicationMapper.entityToDto(newUserApplication);
	}

	@Override
	public UserApplicationDTO getUserApplicationById(Long userApplicationId) {
		checkArgument(userApplicationId > 0, "Argument was %s but expected nonnegative", userApplicationId);

        return Optional.ofNullable(userApplicationRepository.findOne(userApplicationId))
                .map(userApplicationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User Application does not exist"));
	}

	@Override
	public List<UserApplicationDTO> getAllUserApplications() {
		List<UserApplicationEntity> userApplication = userApplicationRepository.findAll();
		return userApplicationMapper.entityListToDtoList(userApplication);
	}

	@Override
	public void deleteUserApplication(Long userApplicationId) {
		checkArgument(userApplicationId > 0, "Argument was %s but expected nonnegative", userApplicationId);

        Optional.ofNullable(userApplicationRepository.findOne(userApplicationId))
                .ifPresent(userApplication -> {
                	userApplicationRepository.delete(userApplication);
                    LOG.debug("Deleted User Application: {}", userApplication);
                });
		
	}

	
	
}
