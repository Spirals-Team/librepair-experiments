package com.les.brouilles.planner.boot.web;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.les.brouilles.planner.persistence.model.devis.ProprietePrixDevisMariage;
import com.les.brouilles.planner.service.bean.ProprietePrixDevisMariageService;
import com.les.brouilles.planner.service.devis.json.propriete.ProprietePrix;
import com.les.brouilles.planner.service.devis.json.propriete.ProprietePrixUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/proprietesdevismariage")
public class ProprietesPrixDevisMariageController {

	@Autowired
	private ProprietePrixDevisMariageService service;

	@GetMapping()
	public Map<String, ProprietePrix> getAll() {

		final List<ProprietePrixDevisMariage> props = service.findAllByOrderByOrderGroupAsc();

		// @formatter:off
		return props.stream()
				.map(mapToProprietePrix())
				.collect(Collectors.toMap(
						ProprietePrix::getNom,
						Function.identity(),
						(e1, e2) -> e1,
						LinkedHashMap::new));
		// @formatter:on
	}

	Function<ProprietePrixDevisMariage, ProprietePrix> mapToProprietePrix() {
		return ppdm -> {

			final int quantite = ppdm.getQuantite();
			final BigDecimal prixUnitaireTTC = ppdm.getPrixUnitaireTTC();
			final BigDecimal prixTotalTTC = ProprietePrixUtils.computeAmountFrom(quantite, prixUnitaireTTC);
			final BigDecimal prixTotalHT = ProprietePrixUtils.convertToHT(prixTotalTTC);
			final BigDecimal prixUnitaireHT = ProprietePrixUtils.convertToHT(prixUnitaireTTC);

			// @formatter:off
			return ProprietePrix.builder()
				.label(ppdm.getLabel())
				.nom(ppdm.getNom())
				.quantite(ppdm.getQuantite())
				.prixUnitaireTTC(prixUnitaireTTC)
				.prixUnitaireHT(prixUnitaireHT)
				.prixTotalTTC(prixTotalTTC)
				.prixTotalHT(prixTotalHT)
				.remise(ppdm.isRemise())
				.type(ppdm.getTypeProprietePrix().toString())
				.build();
			// @formatter:on
		};
	}

}