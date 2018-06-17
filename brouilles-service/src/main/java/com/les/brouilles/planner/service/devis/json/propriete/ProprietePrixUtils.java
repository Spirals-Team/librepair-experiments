package com.les.brouilles.planner.service.devis.json.propriete;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ProprietePrixUtils {

	private static final BigDecimal HT_RATIO = new BigDecimal("1.2");

	public static BigDecimal convertToHT(final BigDecimal prixTTC) {
		return arrondi(prixTTC.divide(HT_RATIO, 2, RoundingMode.HALF_UP));
	}

	public static BigDecimal computeAmountFrom(final int quantite, final BigDecimal prixTTC) {
		return arrondi(prixTTC.multiply(new BigDecimal(quantite)));
	}

	private static BigDecimal arrondi(final BigDecimal valeur) {
		return valeur.setScale(2, RoundingMode.HALF_UP);
	}

}
