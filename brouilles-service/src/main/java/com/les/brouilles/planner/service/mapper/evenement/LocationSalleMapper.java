package com.les.brouilles.planner.service.mapper.evenement;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.LocationSalle;
import com.les.brouilles.planner.service.dto.LocationSalleDTO;

@Component
public class LocationSalleMapper extends EvenementMapper<LocationSalle, LocationSalleDTO> {

	@Override
	protected Class<LocationSalle> getEntityClass() {
		return LocationSalle.class;
	}

	@Override
	protected Class<LocationSalleDTO> getDtoClass() {
		return LocationSalleDTO.class;
	}

}
