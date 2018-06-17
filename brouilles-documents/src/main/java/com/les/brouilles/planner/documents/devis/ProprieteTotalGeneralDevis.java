package com.les.brouilles.planner.documents.devis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProprieteTotalGeneralDevis {

	private final String nom;

	private final String label;

	private final boolean redLabel;

	private final String prixUnitaireHT;

	private final String prixUnitaireTTC;

	private final String prixTotalHT;

	private final String prixTotalTTC;

	private final String type;
}
