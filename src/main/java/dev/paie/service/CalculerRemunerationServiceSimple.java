package dev.paie.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	PaieUtils arro = new PaieUtils();

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		// TODO Auto-generated method stub
		ResultatCalculRemuneration result = new ResultatCalculRemuneration();
		result.setSalaireDeBase(arro.formaterBigDecimal(bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()
				.multiply(bulletin.getRemunerationEmploye().getGrade().getTauxBase())));
		BigDecimal salaire = new BigDecimal(result.getSalaireDeBase());
		result.setSalaireBrut(arro.formaterBigDecimal(salaire.add(bulletin.getPrimeExceptionnelle())));
		BigDecimal salaireBrut = new BigDecimal(result.getSalaireBrut());

		List<Cotisation> nonImpot = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();

		BigDecimal sommeTOTAL_RETENUE_SALARIALE = new BigDecimal("0");
		String sommeTotal_retenue_Salariale = arro.formaterBigDecimal(sommeTOTAL_RETENUE_SALARIALE);
		for (int i = 0; i < nonImpot.size(); i++) {
			String calculinter = arro.formaterBigDecimal(nonImpot.get(i).getTauxSalarial().multiply(salaireBrut));
			sommeTOTAL_RETENUE_SALARIALE.add(new BigDecimal(calculinter));
			sommeTotal_retenue_Salariale = arro.formaterBigDecimal(sommeTOTAL_RETENUE_SALARIALE);
			sommeTOTAL_RETENUE_SALARIALE = new BigDecimal(sommeTotal_retenue_Salariale);
		}

		result.setTotalRetenueSalarial(sommeTotal_retenue_Salariale);

		BigDecimal Total_Cotisation_Patronal = new BigDecimal("0");
		String sommeTotal_Cotisation_Patronal = arro.formaterBigDecimal(Total_Cotisation_Patronal);

		for (int i = 0; i < nonImpot.size(); i++) {
			String caculInter = arro.formaterBigDecimal(nonImpot.get(i).getTauxPatronal().multiply(salaireBrut));
			Total_Cotisation_Patronal.add(new BigDecimal(caculInter));
			sommeTotal_Cotisation_Patronal = arro.formaterBigDecimal(Total_Cotisation_Patronal);
			Total_Cotisation_Patronal = new BigDecimal(sommeTotal_Cotisation_Patronal);
		}

		result.setTotalCotisationsPatronales(sommeTotal_Cotisation_Patronal);

		result.setNetImposable(arro.formaterBigDecimal(salaireBrut.subtract(sommeTOTAL_RETENUE_SALARIALE)));
		BigDecimal Net_Impossable = new BigDecimal(result.getNetImposable());

		List<Cotisation> cotisationPatron = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();

		BigDecimal sommeNet_A_Payer = new BigDecimal("0");
		String sommeNet = arro.formaterBigDecimal(sommeNet_A_Payer);

		for (int i = 0; i < cotisationPatron.size(); i++) {
			String calculInter = arro
					.formaterBigDecimal(cotisationPatron.get(i).getTauxSalarial().multiply(salaireBrut));
			sommeNet_A_Payer.add(new BigDecimal(calculInter));
			sommeNet = arro.formaterBigDecimal(sommeNet_A_Payer);
			sommeNet_A_Payer = new BigDecimal(sommeNet);

		}

		result.setNetAPayer(sommeNet);

		return result;
	}

}
