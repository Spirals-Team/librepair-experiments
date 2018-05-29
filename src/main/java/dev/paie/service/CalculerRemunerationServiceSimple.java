package dev.paie.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.util.PaieUtils;

@Transactional
@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	private PaieUtils paieUtils;

	@Autowired
	private BulletinSalaireRepository bulletinSalaireRepository;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {

		BigDecimal salaireDeBase = (bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase()));

		BigDecimal salaireDeBaseArrondi = new BigDecimal(paieUtils.formaterBigDecimal(salaireDeBase));

		BigDecimal salaireBrut = salaireDeBase.add(bulletin.getPrimeExceptionnelle());

		BigDecimal salaireBrutArrondi = new BigDecimal(paieUtils.formaterBigDecimal(salaireBrut));

		BigDecimal totalRetenueSalarial = new BigDecimal(0);

		for (int i = 0; i < bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
				.size(); i++) {
			if (bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables().get(i)
					.getTauxSalarial() != null) {
				totalRetenueSalarial = totalRetenueSalarial
						.add(bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
								.get(i).getTauxSalarial().multiply(salaireBrut));
			}
		}

		BigDecimal totalRetenueSalarialeArrondi = new BigDecimal(paieUtils.formaterBigDecimal(totalRetenueSalarial));

		BigDecimal totalCotisationsPatronales = new BigDecimal(0);

		for (int i = 0; i < bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
				.size(); i++) {
			if (bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables().get(i)
					.getTauxPatronal() != null) {

				totalCotisationsPatronales = totalCotisationsPatronales
						.add(bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables()
								.get(i).getTauxPatronal().multiply(salaireBrut));
			}
		}

		BigDecimal netImposable = salaireBrutArrondi.subtract(totalRetenueSalarialeArrondi);

		BigDecimal netImposableArrondi = new BigDecimal(paieUtils.formaterBigDecimal(netImposable));

		BigDecimal somme = new BigDecimal(0);

		for (int i = 0; i < bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables()
				.size(); i++) {
			if (bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables().get(i)
					.getTauxSalarial() != null)
				somme = somme.add(bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables()
						.get(i).getTauxSalarial().multiply(salaireBrut));
		}

		BigDecimal netAPayer = netImposable.subtract(somme);

		ResultatCalculRemuneration res = new ResultatCalculRemuneration((paieUtils.formaterBigDecimal(salaireDeBase)),
				(paieUtils.formaterBigDecimal(salaireBrut)), (paieUtils.formaterBigDecimal(totalRetenueSalarial)),
				(paieUtils.formaterBigDecimal(totalCotisationsPatronales)),
				(paieUtils.formaterBigDecimal(netImposable)), (paieUtils.formaterBigDecimal(netAPayer)));

		return res;
	}

	public Map<BulletinSalaire, ResultatCalculRemuneration> retourneResultat() {

		Map<BulletinSalaire, ResultatCalculRemuneration> resultat = new HashMap<BulletinSalaire, ResultatCalculRemuneration>();
		List<BulletinSalaire> bulletins = bulletinSalaireRepository.findAll();
		for (BulletinSalaire bulletinSalaire : bulletins) {
			resultat.put(bulletinSalaire, calculer(bulletinSalaire));
		}

		return resultat;

	}

}
