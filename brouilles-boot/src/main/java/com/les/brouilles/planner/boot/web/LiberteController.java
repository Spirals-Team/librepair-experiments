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
import com.les.brouilles.planner.persistence.model.evenement.Liberte;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.bean.LiberteService;
import com.les.brouilles.planner.service.dto.LiberteDTO;
import com.les.brouilles.planner.service.exception.BrouillesClientNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesLiberteNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesLocationSalleNonTrouveException;
import com.les.brouilles.planner.service.mapper.evenement.EvenementMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/liberte")
public class LiberteController {

	@Autowired
	private LiberteService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EvenementMapper<Liberte, LiberteDTO> mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	LiberteDTO create(@RequestBody @Valid final LiberteDTO liberteDTO) {

		log.info("Creation de l'evenement liberte:{}", liberteDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final Liberte liberte = mapper.convertToEntity(liberteDTO);

		final Long idClient = liberteDTO.getIdClient();
		final Client client = clientService.getClientById(idClient);

		if (null == client) {
			throw new BrouillesClientNonTrouveException(idClient);
		} else {
			log.debug("Client {} trouvé pour nouvel événement", idClient);
		}

		// On set le client
		liberte.setClient(client);
		final Liberte created = service.createEvenement(liberte);

		return mapper.convertToDTO(created);
	}

	@GetMapping("/{id}")
	public LiberteDTO getById(@PathVariable(value = "id") final Long id) {

		final Liberte liberte = service.getLiberteById(id);
		if (null == liberte) {
			throw new BrouillesLocationSalleNonTrouveException(id);
		}

		return mapper.convertToDTO(liberte);
	}

	@PutMapping
	public LiberteDTO update(@RequestBody @Valid final LiberteDTO liberteDTO) {

		log.info("Mise à jour du repas liberté:{}", liberteDTO);

		final Liberte liberte = service.getLiberteById(liberteDTO.getId());
		if (null == liberte) {
			throw new BrouillesLiberteNonTrouveException(liberteDTO.getId());
		}

		final Liberte withUpdate = mapper.convertToEntity(liberteDTO);

		return mapper.convertToDTO(service.update(withUpdate));

	}

}