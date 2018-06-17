package com.les.brouilles.planner.service.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.Client;
import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.repository.ClientRepository;
import com.les.brouilles.planner.service.mapper.client.ClientMapper;

@Component
public class ClientService {

	@Autowired
	private CompteurService compteurService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientMapper clientMapper;

	@Transactional
	public Client createClient(final Client client) {
		client.setNumeroClient("C-" + compteurService.getCompteur(TypeCompteur.CLIENT));
		final Client createClient = clientRepository.save(client);
		return createClient;
	}

	@Transactional
	public Client updateClient(final Client clientOriginalToUpdate, final Client clientWithUpdate) {

		clientMapper.mapFieldsBeforeUpdate(clientOriginalToUpdate, clientWithUpdate);

		final Client updatedClient = clientRepository.save(clientOriginalToUpdate);
		return updatedClient;
	}

	public List<Client> getClients() {
		return clientRepository.findAllByOrderByNumeroClientAsc();
	}

	public Client getClientById(final Long id) {

		return clientRepository.findOne(id);

	}
}
