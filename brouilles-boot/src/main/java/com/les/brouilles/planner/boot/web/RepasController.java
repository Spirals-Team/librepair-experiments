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
import com.les.brouilles.planner.persistence.model.evenement.Repas;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.bean.RepasService;
import com.les.brouilles.planner.service.dto.RepasDTO;
import com.les.brouilles.planner.service.exception.BrouillesClientNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesRepasNonTrouveException;
import com.les.brouilles.planner.service.mapper.evenement.EvenementMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/repas")
public class RepasController {

	@Autowired
	private RepasService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EvenementMapper<Repas, RepasDTO> mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	RepasDTO createRepas(@RequestBody @Valid final RepasDTO repasDTO) {

		log.info("Creation de l'evenement repas:{}", repasDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final Repas repas = mapper.convertToEntity(repasDTO);

		final Long idClient = repasDTO.getIdClient();
		final Client client = clientService.getClientById(idClient);

		if (null == client) {
			throw new BrouillesClientNonTrouveException(idClient);
		} else {
			log.debug("Client {} trouvé pour nouvel événement", idClient);
		}

		// On set le client
		repas.setClient(client);
		final Repas created = service.createEvenement(repas);

		return mapper.convertToDTO(created);
	}

	@GetMapping("/{id}")
	public RepasDTO getRepasById(@PathVariable(value = "id") final Long id) {

		final Repas repas = service.getRepasById(id);
		if (null == repas) {
			throw new BrouillesRepasNonTrouveException(id);
		}

		return mapper.convertToDTO(repas);
	}

	@PutMapping
	public RepasDTO updateRepas(@RequestBody @Valid final RepasDTO repasDTO) {

		log.info("Mise à jour du repas:{}", repasDTO);

		final Repas repas = service.getRepasById(repasDTO.getId());
		if (null == repas) {
			throw new BrouillesRepasNonTrouveException(repasDTO.getId());
		}

		final Repas withUpdate = mapper.convertToEntity(repasDTO);

		return mapper.convertToDTO(service.updateRepas(withUpdate));

	}

	// @GetMapping("supprimer/{evtId}")
	// String delete(@PathVariable(value = "evtId") final Long evtId) {
	//
	// log.info("Suppression du repas avec Id:{}", evtId);
	//
	// service.delete(evtId);
	//
	// return "OK";
	// }

}