package com.les.brouilles.planner.service.mapper.devis;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.devis.Devis;
import com.les.brouilles.planner.service.dto.DevisDTO;
import com.les.brouilles.planner.service.mapper.Mapper;

@Component
public class DevisMapper implements Mapper<Devis, DevisDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Devis convertToEntity(final DevisDTO dto) {
		final Devis entity = modelMapper.map(dto, Devis.class);

		return entity;
	}

	@Override
	public DevisDTO convertToDTO(final Devis entity) {

		final DevisDTO dto = modelMapper.map(entity, DevisDTO.class);

		return dto;
	}

	// --------------------- private methods ------------------------

}
