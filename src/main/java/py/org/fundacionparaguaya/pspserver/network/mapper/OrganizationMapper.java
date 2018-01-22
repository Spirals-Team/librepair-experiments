package py.org.fundacionparaguaya.pspserver.network.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;



/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class OrganizationMapper implements BaseMapper<OrganizationEntity, OrganizationDTO> {

	private final ModelMapper modelMapper;
	 
	public OrganizationMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<OrganizationDTO> entityListToDtoList(List<OrganizationEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public OrganizationDTO entityToDto(OrganizationEntity entity) {
		OrganizationDTO dto = modelMapper.map(entity, OrganizationDTO.class);
	    return dto;
	}

	@Override
	public OrganizationEntity dtoToEntity(OrganizationDTO dto) {
		return modelMapper.map(dto, OrganizationEntity.class);
	}

}
