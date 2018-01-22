package py.org.fundacionparaguaya.pspserver.network.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;



/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class ApplicationMapper implements BaseMapper<ApplicationEntity, ApplicationDTO> {

	private final ModelMapper modelMapper;
	 
	public ApplicationMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ApplicationDTO> entityListToDtoList(List<ApplicationEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public ApplicationDTO entityToDto(ApplicationEntity entity) {
		ApplicationDTO dto = modelMapper.map(entity, ApplicationDTO.class);
	        return dto;
	}

	@Override
	public ApplicationEntity dtoToEntity(ApplicationDTO dto) {
		return modelMapper.map(dto, ApplicationEntity.class);
	}

}
