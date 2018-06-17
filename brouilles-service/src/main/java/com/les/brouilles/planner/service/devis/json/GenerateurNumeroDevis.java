package com.les.brouilles.planner.service.devis.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.model.devis.Devis;
import com.les.brouilles.planner.persistence.repository.DevisRepository;
import com.les.brouilles.planner.service.bean.CompteurService;

@Component
public class GenerateurNumeroDevis {

	@Autowired
	private DevisRepository devisRepository;

	@Autowired
	private CompteurService compteurService;

	private final Map<String, Integer> listeLetterByPosition = new HashMap<>();

	// -------------------- public methods ----------------------

	@PostConstruct
	public void init() {
		for (int i = 'A'; i <= 'Z'; i++) {
			listeLetterByPosition.put(String.valueOf((char) i), (i - 'A' + 1));
		}
	}

	public String generateFor(final Devis devis) {

		if (null != devis.getNumeroDevis()) {
			return devis.getNumeroDevis();
		}

		String numeroDevis = null;

		final List<Devis> listeDevis = devisRepository.findAllByEvenementId(devis.getEvenement().getId());

		if (null == listeDevis || listeDevis.isEmpty()) {
			numeroDevis = creerNouveauNumero();
		} else {
			final String lastNumeroDevis = listeDevis.get(listeDevis.size() - 1).getNumeroDevis();
			final String lastLetter = String.valueOf(lastNumeroDevis.charAt(lastNumeroDevis.length() - 1));
			final int position = listeLetterByPosition.get(lastLetter);
			final String newLetter = getCharForNumber(position + 1);
			numeroDevis = lastNumeroDevis.substring(0, lastNumeroDevis.length() - 1) + newLetter;
		}

		return numeroDevis;
	}

	// -------------------- private methods ----------------------

	private String creerNouveauNumero() {

		return "D-" + compteurService.getCompteur(TypeCompteur.DEVIS) + "A";
	}

	private String getCharForNumber(final int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
	}
}
