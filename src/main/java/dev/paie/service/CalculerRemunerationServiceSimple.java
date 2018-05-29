package dev.paie.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	private PaieUtils paieUtils  = new PaieUtils();

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		// TODO Auto-generated method stub

		String SALAIRE_BASE =paieUtils.formaterBigDecimal( bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase()));
		
		String SALAIRE_BRUT =paieUtils.formaterBigDecimal(new BigDecimal(SALAIRE_BASE).add(bulletin.getPrimeExceptionnelle()));
		// BigDecimal total_retenue_salariale =;

		List<Cotisation> cotisationsNonImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();
		List<Cotisation> cotisationsImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();

		BigDecimal TOTAL_RETENUE_SALARIALE = new BigDecimal("0");
		BigDecimal TOTAL_RETENUE_PATRONALES = new BigDecimal("0");
		for (Cotisation cotisation : cotisationsNonImposables) {
			if(cotisation.getTauxSalarial()!=null){
				TOTAL_RETENUE_SALARIALE = TOTAL_RETENUE_SALARIALE.add(cotisation.getTauxSalarial());
			}
			if(cotisation.getTauxPatronal()!=null){
				TOTAL_RETENUE_PATRONALES = TOTAL_RETENUE_PATRONALES.add(cotisation.getTauxPatronal());
			}
		}
		String total_retenue_salariale =paieUtils.formaterBigDecimal( TOTAL_RETENUE_SALARIALE = TOTAL_RETENUE_SALARIALE.multiply(new BigDecimal(SALAIRE_BRUT)));
		String total_retenue_patronales = paieUtils.formaterBigDecimal(TOTAL_RETENUE_PATRONALES = TOTAL_RETENUE_PATRONALES.multiply(new BigDecimal(SALAIRE_BRUT)));
		
		String NET_IMPOSABLE = paieUtils.formaterBigDecimal(new BigDecimal(SALAIRE_BRUT).subtract(new BigDecimal(total_retenue_salariale)));

		BigDecimal TOTAL_RETENUE_IMPO = new BigDecimal(0);
		for (Cotisation cotisation2 : cotisationsImposables) {
			if(cotisation2.getTauxSalarial()!=null){
				TOTAL_RETENUE_IMPO = TOTAL_RETENUE_IMPO.add(cotisation2.getTauxSalarial().multiply(new BigDecimal(SALAIRE_BRUT)));
			}
		}
		
		
		String NET_A_PAYER =paieUtils.formaterBigDecimal( new BigDecimal(NET_IMPOSABLE).subtract(TOTAL_RETENUE_IMPO) );

		ResultatCalculRemuneration remuneration = new ResultatCalculRemuneration();
		String netAPayer = NET_A_PAYER;
		remuneration.setNetAPayer(netAPayer);
		String netImposable = NET_IMPOSABLE;
		remuneration.setNetImposable(netImposable);
		String salaireBrut = SALAIRE_BRUT;
		remuneration.setSalaireBrut(salaireBrut);
		String salaireDeBase = SALAIRE_BASE;
		remuneration.setSalaireDeBase(salaireDeBase);
		String totalCotisationsPatronales = total_retenue_patronales;
		remuneration.setTotalCotisationsPatronales(totalCotisationsPatronales);
		String totalRetenueSalarial = total_retenue_salariale;
		remuneration.setTotalRetenueSalarial(totalRetenueSalarial);

		return remuneration;
	}

}