package py.org.fundacionparaguaya.pspserver.system.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;


/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class CountryMapper implements BaseMapper<CountryEntity, CountryDTO> {

	private final ModelMapper modelMapper;
	 
	public CountryMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<CountryDTO> entityListToDtoList(List<CountryEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public CountryDTO entityToDto(CountryEntity entity) {
		CountryDTO dto = modelMapper.map(entity, CountryDTO.class);
	        return dto;
	}

	@Override
	public CountryEntity dtoToEntity(CountryDTO dto) {
		return modelMapper.map(dto, CountryEntity.class);
	}

}
