package dev.paie.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
@Component
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	PaieUtils arrondi = new PaieUtils();

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		ResultatCalculRemuneration calcul = new ResultatCalculRemuneration();

		/* Calcul du salaire de base */
		String salaireDeBase = arrondi.formaterBigDecimal(bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase()));
		calcul.setSalaireDeBase(salaireDeBase);

		/* Calcul du salaire brut */
		BigDecimal salaireDeBaseDec = new BigDecimal(salaireDeBase);
		String salaireBrut = arrondi.formaterBigDecimal(salaireDeBaseDec.add(bulletin.getPrimeExceptionnelle()));
		calcul.setSalaireBrut(salaireBrut);

		/* Calcul des retenues salariales */
		BigDecimal salaireBrutDec = new BigDecimal(salaireBrut);
		BigDecimal totalRetenueSalarialeDec = new BigDecimal("0.00");
		List<Cotisation> cotisationsNonImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();
		for (Cotisation cotisation : cotisationsNonImposables) {
			totalRetenueSalarialeDec.add(cotisation.getTauxSalarial().multiply(salaireBrutDec));
		}
		String totalRetenueSalariale = arrondi.formaterBigDecimal(totalRetenueSalarialeDec);
		calcul.setTotalRetenueSalarial(totalRetenueSalariale);

		/* Calcul des cotisations patronales */
		BigDecimal totalCotisationsPatronalesDec = new BigDecimal("0.00");
		for (Cotisation cotisation : cotisationsNonImposables) {
			totalCotisationsPatronalesDec.add(cotisation.getTauxPatronal().multiply(salaireBrutDec));
		}
		String totalCotisationsPatronales = arrondi.formaterBigDecimal(totalCotisationsPatronalesDec);
		calcul.setTotalCotisationsPatronales(totalCotisationsPatronales);

		/* Calcul du net imposable */
		String netImposable = arrondi.formaterBigDecimal(salaireBrutDec.subtract(totalRetenueSalarialeDec));
		calcul.setNetImposable(netImposable);

		/* Calcul du net à payer */
		BigDecimal netImposableDec = new BigDecimal(netImposable);
		BigDecimal netAPayerDec = new BigDecimal(arrondi.formaterBigDecimal(netImposableDec));
		List<Cotisation> cotisationsImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();
		for (Cotisation cotisation : cotisationsImposables) {
			netAPayerDec.subtract(cotisation.getTauxSalarial().multiply(salaireBrutDec));
		}
		String netAPayer = arrondi.formaterBigDecimal(netAPayerDec);
		calcul.setNetAPayer(netAPayer);

		return calcul;
	}

}
