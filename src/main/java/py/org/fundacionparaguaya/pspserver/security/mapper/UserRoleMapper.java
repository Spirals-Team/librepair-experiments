package py.org.fundacionparaguaya.pspserver.security.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserRoleDTO;
import py.org.fundacionparaguaya.pspserver.security.entities.UserRoleEntity;



/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class UserRoleMapper implements BaseMapper<UserRoleEntity, UserRoleDTO> {

	private final ModelMapper modelMapper;
	 
	public UserRoleMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<UserRoleDTO> entityListToDtoList(List<UserRoleEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public UserRoleDTO entityToDto(UserRoleEntity entity) {
		UserRoleDTO dto = modelMapper.map(entity, UserRoleDTO.class);
	    return dto;
	}

	@Override
	public UserRoleEntity dtoToEntity(UserRoleDTO dto) {
		return modelMapper.map(dto, UserRoleEntity.class);
	}

}
