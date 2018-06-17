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
import com.les.brouilles.planner.persistence.model.evenement.Chambre;
import com.les.brouilles.planner.service.bean.ChambreService;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.dto.ChambreDTO;
import com.les.brouilles.planner.service.exception.BrouillesChambreNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesClientNonTrouveException;
import com.les.brouilles.planner.service.mapper.evenement.EvenementMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chambre")
public class ChambresController {

	@Autowired
	private ChambreService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EvenementMapper<Chambre, ChambreDTO> mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ChambreDTO createChambre(@RequestBody @Valid final ChambreDTO chambreDTO) {

		log.info("Creation de la chambre:{}", chambreDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final Chambre chambre = mapper.convertToEntity(chambreDTO);

		final Long idClient = chambreDTO.getIdClient();
		final Client client = clientService.getClientById(idClient);

		if (null == client) {
			throw new BrouillesClientNonTrouveException(idClient);
		} else {
			log.debug("Client {} trouvé pour nouvel événement", idClient);
		}

		// On set le client
		chambre.setClient(client);
		final Chambre created = service.createEvenement(chambre);

		return mapper.convertToDTO(created);
	}

	@GetMapping("/{id}")
	public ChambreDTO getChambreById(@PathVariable(value = "id") final Long id) {

		final Chambre chambre = service.getChambreById(id);
		if (null == chambre) {
			throw new BrouillesChambreNonTrouveException(id);
		}

		return mapper.convertToDTO(chambre);
	}

	@PutMapping
	public ChambreDTO update(@RequestBody @Valid final ChambreDTO chambreDTO) {

		log.info("Mise à jour de la chambre:{}", chambreDTO);

		final Chambre chambre = service.getChambreById(chambreDTO.getId());
		if (null == chambre) {
			throw new BrouillesChambreNonTrouveException(chambreDTO.getId());
		}

		final Chambre withUpdate = mapper.convertToEntity(chambreDTO);

		return mapper.convertToDTO(service.update(withUpdate));

	}
}