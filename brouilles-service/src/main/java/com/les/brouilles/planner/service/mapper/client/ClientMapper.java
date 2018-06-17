package com.les.brouilles.planner.service.mapper.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.Client;
import com.les.brouilles.planner.service.dto.ClientDTO;
import com.les.brouilles.planner.service.mapper.Mapper;

@Component
public class ClientMapper implements Mapper<Client, ClientDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Client convertToEntity(final ClientDTO dto) {
		return modelMapper.map(dto, Client.class);
	}

	@Override
	public ClientDTO convertToDTO(final Client entity) {
		return modelMapper.map(entity, ClientDTO.class);
	}

	public void mapFieldsBeforeUpdate(final Client originalToUpdate, final Client withUpdate) {

		// FIXME : mettre à jour les événements quand ça sera fait!
		// A VIRER JE PENSE CA SERT A RIEN AVEC LE MAPPER

		// Seuls nom, prenom et catégorie sont obligatoires
		if (withUpdate.getNom() != null && withUpdate.getNom() != originalToUpdate.getNom()) {
			originalToUpdate.setNom(withUpdate.getNom());
		}

		if (withUpdate.getPrenom() != null && withUpdate.getPrenom() != originalToUpdate.getPrenom()) {
			originalToUpdate.setPrenom(withUpdate.getPrenom());
		}

		if (withUpdate.getCategorie() != null && withUpdate.getCategorie() != originalToUpdate.getCategorie()) {
			originalToUpdate.setCategorie(withUpdate.getCategorie());
		}

		if (withUpdate.getAdresse() != originalToUpdate.getAdresse()) {
			originalToUpdate.setAdresse(withUpdate.getAdresse());
		}

		if (withUpdate.getDenomination() != originalToUpdate.getDenomination()) {
			originalToUpdate.setDenomination(withUpdate.getDenomination());
		}

		if (withUpdate.getMail1() != originalToUpdate.getMail1()) {
			originalToUpdate.setMail1(withUpdate.getMail1());
		}
		if (withUpdate.getMail2() != originalToUpdate.getMail2()) {
			originalToUpdate.setMail2(withUpdate.getMail2());
		}

		// Numero client non mis à jour!!!

		if (withUpdate.getTel1() != originalToUpdate.getTel1()) {
			originalToUpdate.setTel1(withUpdate.getTel1());
		}

		if (withUpdate.getTel2() != originalToUpdate.getTel2()) {
			originalToUpdate.setTel2(withUpdate.getTel2());
		}

	}

}
