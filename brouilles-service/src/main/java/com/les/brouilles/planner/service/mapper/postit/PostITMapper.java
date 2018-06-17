package com.les.brouilles.planner.service.mapper.postit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.PostIT;
import com.les.brouilles.planner.service.converter.HourMinuteConverter;
import com.les.brouilles.planner.service.dto.PostITDTO;
import com.les.brouilles.planner.service.mapper.Mapper;

@Component
public class PostITMapper implements Mapper<PostIT, PostITDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostIT convertToEntity(final PostITDTO dto) {
		final PostIT entity = modelMapper.map(dto, PostIT.class);

		updateEntityDates(dto, entity);

		return entity;
	}

	@Override
	public PostITDTO convertToDTO(final PostIT entity) {

		final PostITDTO dto = modelMapper.map(entity, PostITDTO.class);

		updateDTODates(entity, dto);

		return dto;
	}

	// --------------------- private methods ------------------------

	private void updateEntityDates(final PostITDTO dto, final PostIT entity) {

		entity.setDebut(HourMinuteConverter.convertirDateEtHeureEnDate(dto.getDebut(), dto.getHeureDebut()));
	}

	private void updateDTODates(final PostIT entity, final PostITDTO dto) {
		dto.setHeureDebut(HourMinuteConverter.extraireHeureDeDate(entity.getDebut()));
	}

}
