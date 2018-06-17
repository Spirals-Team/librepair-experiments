package com.les.brouilles.planner.boot.web;

import java.util.List;
import java.util.stream.Collectors;

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
import com.les.brouilles.planner.persistence.model.evenement.Mariage;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.bean.MariageService;
import com.les.brouilles.planner.service.dto.MariageDTO;
import com.les.brouilles.planner.service.exception.BrouillesClientNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesMariageNonTrouveException;
import com.les.brouilles.planner.service.mapper.evenement.EvenementMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mariage")
public class MariageController {

	@Autowired
	private MariageService service;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EvenementMapper<Mariage, MariageDTO> mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	MariageDTO createMariage(@RequestBody @Valid final MariageDTO mariageDTO) {

		log.info("Creation du mariage:{}", mariageDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final Mariage chambre = mapper.convertToEntity(mariageDTO);

		final Long idClient = mariageDTO.getIdClient();
		final Client client = clientService.getClientById(idClient);

		if (null == client) {
			throw new BrouillesClientNonTrouveException(idClient);
		} else {
			log.debug("Client {} trouvé pour nouvel événement mariage", idClient);
		}

		// On set le client
		chambre.setClient(client);
		final Mariage created = service.createEvenement(chambre);

		return mapper.convertToDTO(created);
	}

	@GetMapping
	List<MariageDTO> getMariages() {

		log.info("Récupération de tous les mariages");

		// FIXME : pagination!

		// @formatter:off
		return service.getAllMariages().stream()
				.map(m -> mapper.convertToDTO(m))
				.collect(Collectors.toList());
		// @formatter:on

	}

	@GetMapping("/{id}")
	public MariageDTO getById(@PathVariable(value = "id") final Long id) {

		final Mariage mariage = service.getMariageById(id);
		if (null == mariage) {
			throw new BrouillesMariageNonTrouveException(id);
		}

		return mapper.convertToDTO(mariage);
	}

	@PutMapping
	public MariageDTO updateMariage(@RequestBody @Valid final MariageDTO mariageDTO) {

		log.info("Mise à jour du mariage:{}", mariageDTO);

		final Mariage mariage = service.getMariageById(mariageDTO.getId());
		if (null == mariage) {
			throw new BrouillesMariageNonTrouveException(mariageDTO.getId());
		}

		// Récupération du mariage
		// final Mariage mariageOriginal =
		// service.getMariageById(mariageDTO.getId());
		final Mariage withUpdate = mapper.convertToEntity(mariageDTO);

		return mapper.convertToDTO(service.updateMariage(withUpdate));

	}
}