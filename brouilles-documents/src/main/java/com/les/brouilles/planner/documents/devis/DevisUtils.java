package com.les.brouilles.planner.documents.devis;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.les.brouilles.planner.persistence.model.Client;
import com.les.brouilles.planner.persistence.model.devis.Devis;

public final class DevisUtils {

	private DevisUtils() {

	}

	public static InfosEnteteDevis getInfosEnteteDevis(final Devis devis) {

		final InfosEnteteDevis.InfosEnteteDevisBuilder builder = InfosEnteteDevis.builder();

		builder.dateDevis(DateFormatUtils.format(new Date(), "dd/MM/yyyy"));
		builder.dateEvenement(DateFormatUtils.format(devis.getEvenement().getDebut(), "dd/MM/yyyy"));

		final Client client = devis.getEvenement().getClient();

		builder.nomClient(client.getNom() + " " + client.getPrenom());
		builder.numeroDevis(devis.getNumeroDevis());

		return builder.build();

	}
}
