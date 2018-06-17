package com.les.brouilles.planner.service.mapper.evenement;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.Liberte;
import com.les.brouilles.planner.service.dto.LiberteDTO;

@Component
public class LiberteMapper extends EvenementMapper<Liberte, LiberteDTO> {

	@Override
	protected Class<Liberte> getEntityClass() {
		return Liberte.class;
	}

	@Override
	protected Class<LiberteDTO> getDtoClass() {
		return LiberteDTO.class;
	}

}
