package com.les.brouilles.planner.service.devis.json.propriete;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProprietePrix {

	private final String nom;

	private final String label;

	private final int quantite;

	private final boolean remise;

	private final BigDecimal prixUnitaireHT;

	private final BigDecimal prixUnitaireTTC;

	private final BigDecimal prixTotalHT;

	private final BigDecimal prixTotalTTC;

	private final String type;
}
