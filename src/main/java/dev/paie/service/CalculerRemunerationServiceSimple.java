package dev.paie.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	private PaieUtils paieUtils;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		BigDecimal salaireBase = bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase());
		BigDecimal salaireBrut = salaireBase.add(bulletin.getPrimeExceptionnelle());
		BigDecimal totalRetenueSalariale = new BigDecimal(0);
		for (int i = 0; i < bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
				.size(); i++) {
			if (bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables().get(i)
					.getTauxSalarial() != null) {
				totalRetenueSalariale = totalRetenueSalariale
						.add(bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
								.get(i).getTauxSalarial().multiply(salaireBrut));
			}
		}
		BigDecimal totalCotisationPatronales = new BigDecimal(0);
		for (int i = 0; i < bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
				.size(); i++) {
			if (bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables().get(i)
					.getTauxPatronal() != null) {
				totalCotisationPatronales = totalCotisationPatronales
						.add(bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
								.get(i).getTauxPatronal().multiply(salaireBrut));
			}
		}
		BigDecimal salaireBruteA = new BigDecimal(paieUtils.formaterBigDecimal(salaireBrut));
		BigDecimal TotalretenuSalarialA = new BigDecimal(paieUtils.formaterBigDecimal(totalRetenueSalariale));

		BigDecimal netImposable = salaireBruteA.subtract(TotalretenuSalarialA);

		BigDecimal netAPayer = new BigDecimal(0);

		for (int i = 0; i < bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables()
				.size(); i++) {
			if (bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables().get(i)
					.getTauxSalarial() != null) {
				netAPayer = netAPayer.subtract(bulletin.getRemunerationEmploye().getProfilRemuneration()
						.getCotisationsImposables().get(i).getTauxSalarial().multiply(salaireBrut));
			}
		}
		netAPayer = netAPayer.add(netImposable);

		ResultatCalculRemuneration resultat = new ResultatCalculRemuneration(paieUtils.formaterBigDecimal(salaireBase),
				paieUtils.formaterBigDecimal(salaireBrut), paieUtils.formaterBigDecimal(totalRetenueSalariale),
				paieUtils.formaterBigDecimal(totalCotisationPatronales), paieUtils.formaterBigDecimal(netImposable),
				paieUtils.formaterBigDecimal(netAPayer));
		return resultat;
	}

}
