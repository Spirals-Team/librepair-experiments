package com.les.brouilles.planner.service.mapper.evenement;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.Repas;
import com.les.brouilles.planner.service.dto.RepasDTO;

@Component
public class RepasMapper extends EvenementMapper<Repas, RepasDTO> {

	@Override
	protected Class<Repas> getEntityClass() {
		return Repas.class;
	}

	@Override
	protected Class<RepasDTO> getDtoClass() {
		return RepasDTO.class;
	}

}
