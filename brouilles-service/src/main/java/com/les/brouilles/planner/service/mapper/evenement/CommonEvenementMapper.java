package com.les.brouilles.planner.service.mapper.evenement;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;
import com.les.brouilles.planner.service.dto.CommonEvenementDTO;

@Component
public class CommonEvenementMapper extends EvenementMapper<CommonEvenement, CommonEvenementDTO> {

	@Override
	protected Class<CommonEvenement> getEntityClass() {
		return CommonEvenement.class;
	}

	@Override
	protected Class<CommonEvenementDTO> getDtoClass() {
		return CommonEvenementDTO.class;
	}

}
