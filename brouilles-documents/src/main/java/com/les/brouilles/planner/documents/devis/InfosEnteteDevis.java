package com.les.brouilles.planner.documents.devis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfosEnteteDevis {

	private final String dateDevis;

	private final String numeroDevis;

	private final String nomClient;

	private final String dateEvenement;
}
