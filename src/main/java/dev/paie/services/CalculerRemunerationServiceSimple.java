package dev.paie.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.Grade;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	private PaieUtils paieUtils;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {

		Grade grade = bulletin.getRemunerationEmploye().getGrade();

		BigDecimal salaireDeBaseNonArr = grade.getNbHeuresBase().multiply(grade.getTauxBase());
		String salaireDeBaseArr = paieUtils.formaterBigDecimal(salaireDeBaseNonArr);

		ResultatCalculRemuneration resultat = new ResultatCalculRemuneration();
		resultat.setSalaireDeBase(salaireDeBaseArr);

		BigDecimal salaireBrutNonArr = new BigDecimal(salaireDeBaseArr).add(bulletin.getPrimeExceptionnelle());
		resultat.setSalaireBrut(paieUtils.formaterBigDecimal(salaireBrutNonArr));

		BigDecimal totalRetenuSalariale = new BigDecimal("0");
		for (Cotisation c : bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()) {
			if (c.getTauxSalarial() != null)
				totalRetenuSalariale = totalRetenuSalariale.add(c.getTauxSalarial().multiply(salaireBrutNonArr));
		}
		resultat.setTotalRetenueSalarial(paieUtils.formaterBigDecimal(totalRetenuSalariale));

		BigDecimal totalRetenuPatronale = new BigDecimal("0");
		for (Cotisation c : bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()) {
			if (c.getTauxPatronal() != null)
				totalRetenuPatronale = totalRetenuPatronale.add(c.getTauxPatronal().multiply(salaireBrutNonArr));
		}
		resultat.setTotalCotisationsPatronales(paieUtils.formaterBigDecimal(totalRetenuPatronale));

		BigDecimal netImp = new BigDecimal(paieUtils.formaterBigDecimal(salaireBrutNonArr))
				.subtract(new BigDecimal(paieUtils.formaterBigDecimal(totalRetenuSalariale)));
		resultat.setNetImposable(paieUtils.formaterBigDecimal(netImp));

		BigDecimal sum = new BigDecimal("0");
		for (Cotisation c : bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables()) {
			if (c.getTauxSalarial() != null)
				sum = sum.add(c.getTauxSalarial().multiply(salaireBrutNonArr));
		}
		BigDecimal netAPayer = new BigDecimal(paieUtils.formaterBigDecimal(netImp))
				.subtract(new BigDecimal(paieUtils.formaterBigDecimal(sum)));

		resultat.setNetAPayer(paieUtils.formaterBigDecimal(netAPayer));
		return resultat;
	}
}
