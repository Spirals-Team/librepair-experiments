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

import com.les.brouilles.planner.persistence.model.devis.Devis;
import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;
import com.les.brouilles.planner.service.bean.CommonEvenementService;
import com.les.brouilles.planner.service.bean.DevisService;
import com.les.brouilles.planner.service.dto.DevisDTO;
import com.les.brouilles.planner.service.exception.BrouillesDevisNonTrouveException;
import com.les.brouilles.planner.service.exception.BrouillesEvenementNonTrouveException;
import com.les.brouilles.planner.service.mapper.Mapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/devis")
public class DevisController {

	@Autowired
	private DevisService service;

	@Autowired
	private CommonEvenementService evenementService;

	@Autowired
	private Mapper<Devis, DevisDTO> mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	DevisDTO createDevis(@RequestBody @Valid final DevisDTO devisDTO) {

		log.info("Creation de l'evenement devis:{}", devisDTO);

		final Devis devis = mapper.convertToEntity(devisDTO);

		final Long idEvenement = devisDTO.getEvenementId();
		final CommonEvenement evenement = evenementService.getEvenementById(idEvenement);

		if (null == evenement) {
			throw new BrouillesEvenementNonTrouveException(idEvenement);
		} else {
			log.debug("Evenement {} trouvé pour nouveau devis", idEvenement);
		}

		devis.setEvenement(evenement);

		final Devis created = service.create(devis);

		return mapper.convertToDTO(created);
	}

	@GetMapping()
	public List<DevisDTO> getDevis() {

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<Devis> devis = service.getAllDevis();

		// @formatter:off
		return devis.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/client/{clientId}")
	public List<DevisDTO> getDevisByClientId(@PathVariable(value = "clientId") final Long clientId) {

		log.info("Recherche des devis du client {}", clientId);

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<Devis> evenements = service.findByClientId(clientId);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/evenement/{evenementId}")
	public List<DevisDTO> getDevisByEvenementId(@PathVariable(value = "evenementId") final Long evenementId) {

		log.info("Recherche des devis de l'événement {}", evenementId);

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<Devis> evenements = service.findByEvenementId(evenementId);

		// @formatter:off
		return evenements.stream()
				.map(e -> mapper.convertToDTO(e))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/{id}")
	public DevisDTO getById(@PathVariable(value = "id") final Long id) {

		final Devis devis = service.findById(id);
		if (null == devis) {
			throw new BrouillesDevisNonTrouveException(id);
		}

		return mapper.convertToDTO(devis);
	}

	@PutMapping
	public DevisDTO update(@RequestBody @Valid final DevisDTO devisDTO) {

		log.info("Mise à jour du mariage:{}", devisDTO);

		final Devis devis = service.findById(devisDTO.getId());
		if (null == devis) {
			throw new BrouillesDevisNonTrouveException(devisDTO.getId());
		}

		// Récupération du mariage
		// final Devis mariageOriginal =
		// service.getDevisById(mariageDTO.getId());
		final Devis withUpdate = mapper.convertToEntity(devisDTO);

		return mapper.convertToDTO(service.update(withUpdate));

	}
}