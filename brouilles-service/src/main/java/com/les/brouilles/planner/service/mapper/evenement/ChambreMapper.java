package com.les.brouilles.planner.service.mapper.evenement;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.Chambre;
import com.les.brouilles.planner.service.dto.ChambreDTO;

@Component
public class ChambreMapper extends EvenementMapper<Chambre, ChambreDTO> {

	@Override
	protected Class<Chambre> getEntityClass() {
		return Chambre.class;
	}

	@Override
	protected Class<ChambreDTO> getDtoClass() {
		return ChambreDTO.class;
	}

}
