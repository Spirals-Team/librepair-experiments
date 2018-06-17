package com.les.brouilles.planner.boot.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.les.brouilles.planner.persistence.model.Client;
import com.les.brouilles.planner.persistence.model.evenement.LocationSalle;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.bean.LocationSalleService;
import com.les.brouilles.planner.service.dto.LocationSalleDTO;
import com.les.brouilles.planner.service.exception.BrouillesClientNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesLocationSalleNonTrouveException;
import com.les.brouilles.planner.service.mapper.evenement.EvenementMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationSalleController {

	@Autowired
	private LocationSalleService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EvenementMapper<LocationSalle, LocationSalleDTO> mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	LocationSalleDTO createLocationSalle(@RequestBody @Valid final LocationSalleDTO locationSalleDTO) {

		log.info("Creation de la location:{}", locationSalleDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final LocationSalle locationSalle = mapper.convertToEntity(locationSalleDTO);

		final Long idClient = locationSalleDTO.getIdClient();
		final Client client = clientService.getClientById(idClient);

		if (null == client) {
			throw new BrouillesClientNonTrouveException(idClient);
		} else {
			log.debug("Client {} trouvé pour nouvel événement", idClient);
		}

		// On set le client
		locationSalle.setClient(client);
		final LocationSalle created = service.createEvenement(locationSalle);

		return mapper.convertToDTO(created);
	}

	@GetMapping("/{id}")
	public LocationSalleDTO getById(@PathVariable(value = "id") final Long id) {

		final LocationSalle location = service.getLocationSalleById(id);
		if (null == location) {
			throw new BrouillesLocationSalleNonTrouveException(id);
		}

		return mapper.convertToDTO(location);
	}

	@PutMapping
	public LocationSalleDTO update(@RequestBody @Valid final LocationSalleDTO locationSalleDTO) {

		log.info("Mise à jour de la location de salle:{}", locationSalleDTO);

		final LocationSalle locationSalle = service.getLocationSalleById(locationSalleDTO.getId());
		if (null == locationSalle) {
			throw new BrouillesLocationSalleNonTrouveException(locationSalleDTO.getId());
		}

		final LocationSalle withUpdate = mapper.convertToEntity(locationSalleDTO);

		return mapper.convertToDTO(service.update(withUpdate));

	}
}