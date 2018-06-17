package com.les.brouilles.planner.service.mapper.evenement;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.Mariage;
import com.les.brouilles.planner.service.dto.MariageDTO;

@Component
public class MariageMapper extends EvenementMapper<Mariage, MariageDTO> {

	@Override
	protected Class<Mariage> getEntityClass() {
		return Mariage.class;
	}

	@Override
	protected Class<MariageDTO> getDtoClass() {
		return MariageDTO.class;
	}

}
