package dev.paie.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.util.PaieUtils;

@Service
@Transactional
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	PaieUtils paieUtils;

	@Autowired
	BulletinSalaireRepository bsr;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		ResultatCalculRemuneration res = new ResultatCalculRemuneration();

		// Calcul du salaire de base
		BigDecimal salaireBase = bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase());
		res.setSalaireDeBase(paieUtils.formaterBigDecimal(salaireBase));

		// Calcul du salaire brut
		BigDecimal salaireBrut = salaireBase.add(bulletin.getPrimeExceptionnelle());
		res.setSalaireBrut(paieUtils.formaterBigDecimal(salaireBrut));

		// Calcul du total de retenue salariale
		// et du total cotisations patronnales
		List<Cotisation> cotisations = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();
		BigDecimal resNonImposable = new BigDecimal("0.00");
		BigDecimal resNonImposablePatronal = new BigDecimal("0.00");
		Iterator<Cotisation> it = cotisations.iterator();
		while (it.hasNext()) {
			Cotisation test = it.next();
			if (test.getTauxSalarial() != null) {
				resNonImposable = resNonImposable.add(test.getTauxSalarial().multiply(salaireBrut));
			}
			if (test.getTauxPatronal() != null)
				resNonImposablePatronal = resNonImposablePatronal.add(test.getTauxPatronal().multiply(salaireBrut));
		}
		res.setTotalRetenueSalarial(paieUtils.formaterBigDecimal(resNonImposable));
		res.setTotalCotisationsPatronales(paieUtils.formaterBigDecimal(resNonImposablePatronal));

		// Calcul du net imposable
		BigDecimal netImposable = salaireBrut.subtract(resNonImposable);
		res.setNetImposable(paieUtils.formaterBigDecimal(netImposable));

		// Calcul du net à payer
		List<Cotisation> cotisationsImpo = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();
		BigDecimal sommeCotisations = new BigDecimal(0.0);
		Iterator<Cotisation> itc = cotisationsImpo.iterator();
		while (itc.hasNext()) {
			Cotisation test = itc.next();
			if (test.getTauxSalarial() != null)
				sommeCotisations.add(test.getTauxSalarial().multiply(salaireBrut));
		}
		res.setNetAPayer(paieUtils.formaterBigDecimal(netImposable.subtract(sommeCotisations)));

		return res;
	}

	@Override
	public Map<BulletinSalaire, ResultatCalculRemuneration> fullBulletin() {
		Map<BulletinSalaire, ResultatCalculRemuneration> res = new HashMap<>();
		List<BulletinSalaire> bulletins = bsr.findAll();
		for (BulletinSalaire bulletin : bulletins) {
			res.put(bulletin, calculer(bulletin));
		}
		return res;
	}

}
