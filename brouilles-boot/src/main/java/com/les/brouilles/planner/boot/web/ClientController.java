package com.les.brouilles.planner.boot.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.les.brouilles.planner.persistence.model.Client;
import com.les.brouilles.planner.service.bean.ClientService;
import com.les.brouilles.planner.service.dto.ClientDTO;
import com.les.brouilles.planner.service.exception.BrouillesClientNonTrouveException;
import com.les.brouilles.planner.service.mapper.client.ClientMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService service;

	@Autowired
	private ClientMapper mapper;

	@GetMapping()
	public List<ClientDTO> getClients() {

		// FIXME : faire une recherche par page pour cause de perfs!!

		final List<Client> clients = service.getClients();

		// @formatter:off
		return clients.stream()
				.map(c -> mapper.convertToDTO(c))
				.collect(Collectors.toList());
		// @formatter:on
	}

	@GetMapping("/{id}")
	public ClientDTO getClientById(@PathVariable(value = "id") final Long id) {

		final Client client = service.getClientById(id);
		if (null == client) {
			throw new BrouillesClientNonTrouveException(id);
		}

		return mapper.convertToDTO(client);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ClientDTO createClient(@RequestBody @Valid final ClientDTO clientDTO) {

		log.info("Creation du client:{}", clientDTO);

		// FIXME : gérer le cas où il manque des champs!!
		final Client client = mapper.convertToEntity(clientDTO);

		final Client createdClient = service.createClient(client);
		return mapper.convertToDTO(createdClient);

	}

	@PutMapping("/{id}")
	public ClientDTO updateClient(@RequestBody @Valid final ClientDTO clientDTO, @PathVariable final Long id) {

		log.info("Mise à jour du client:{}", clientDTO);

		if (clientDTO.getId() != id) {
			throw new RuntimeException(
					"Erreur d'id lors de la mise à jour du client. " + id + " != " + clientDTO.getId());
		}

		// Récupération du client
		final Client clientOriginal = service.getClientById(clientDTO.getId());
		final Client withUpdate = mapper.convertToEntity(clientDTO);

		return mapper.convertToDTO(service.updateClient(clientOriginal, withUpdate));

	}

	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable final Long id) {
		/*
		 * bookRepository.findOne(id) .orElseThrow(BookNotFoundException::new);
		 * bookRepository.delete(id);
		 */
	}
}