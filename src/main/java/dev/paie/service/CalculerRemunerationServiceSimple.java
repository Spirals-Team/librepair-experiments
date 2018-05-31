package dev.paie.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {
	@Autowired
	PaieUtils paieUtils;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		// TODO Auto-generated method stub
		ResultatCalculRemuneration resultat = new ResultatCalculRemuneration();
		BigDecimal salaireDeBase = bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase());
		resultat.setSalaireDeBase(paieUtils.formaterBigDecimal(salaireDeBase));

		BigDecimal salaireBrut = salaireDeBase.add(bulletin.getPrimeExceptionnelle());
		resultat.setSalaireBrut(paieUtils.formaterBigDecimal(salaireBrut));

		List<Cotisation> listeCotisationsNonimposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();
		BigDecimal totalRetenueSalariale = new BigDecimal("0");
		BigDecimal totalCotisationsPatronales = new BigDecimal("0");
		for (Cotisation cot : listeCotisationsNonimposables) {
			if (cot.getTauxSalarial() != null) {
				totalRetenueSalariale = totalRetenueSalariale.add(cot.getTauxSalarial().multiply(salaireBrut));
			}
			if (cot.getTauxPatronal() != null) {
				totalCotisationsPatronales = totalCotisationsPatronales
						.add(cot.getTauxPatronal().multiply(salaireBrut));
			}
		}
		resultat.setTotalRetenueSalarial(paieUtils.formaterBigDecimal(totalRetenueSalariale));
		resultat.setTotalCotisationsPatronales(paieUtils.formaterBigDecimal(totalCotisationsPatronales));

		BigDecimal netImposable = new BigDecimal(paieUtils.formaterBigDecimal(salaireBrut))
				.subtract(new BigDecimal(paieUtils.formaterBigDecimal(totalRetenueSalariale)));
		resultat.setNetImposable(paieUtils.formaterBigDecimal(netImposable));

		BigDecimal salarialImposable = new BigDecimal("0");
		List<Cotisation> listeCotisationsImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();
		for (Cotisation cot : listeCotisationsImposables) {
			salarialImposable = salarialImposable.add(cot.getTauxSalarial().multiply(salaireBrut));
		}
		BigDecimal netAPayer = netImposable.subtract(salarialImposable);
		resultat.setNetAPayer(paieUtils.formaterBigDecimal(netAPayer));

		return resultat;
	}

}
