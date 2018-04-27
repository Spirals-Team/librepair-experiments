package nc.noumea.mairie.bilan.energie.business.job;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.dao.AdressesConsolideesBusiness;
import nc.noumea.mairie.bilan.energie.business.entities.AdressesConsolideesEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Adresse;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.contract.service.IntegrationAdresseService;
import nc.noumea.mairie.bilan.energie.contract.traitement.IntegrationAdresseTraitement;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Job Quartz d'intégration des adresses
 * 
 * @author Greg Dujardin
 *
 */
public class JobIntegrationAdresse implements Job, IntegrationAdresseTraitement {

	@Autowired
	private IntegrationAdresseService integrationAdresseService;

	/** Service des adresses */
	@Autowired
	private AdresseService adresseService;

	/** Service des adresses consolidées */
	@Autowired
	private AdressesConsolideesBusiness adresseConsolideesService;

	Logger logger = LoggerFactory.getLogger("synchroAdresse");

	/**
	 * Méthode d'exécution du job
	 * 
	 * @param arg0 Contexte d'exécution
	 * @throws JobExecutionException Exception du job Quartz
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		// Appel de l'intégration des adresses sur service d'intégration
		try {
			logger.info("Synchronisation des adresses - Récupération des adresses consolidées");
			integrationAdresseService.recuperationAdressesConsolidees();
			logger.info("Synchronisation des adresses - Synchronisation");
			synchronisationAdresse();
			logger.info("Synchronisation des adresses - Fin de traitement");

		} catch (BusinessException | TechnicalException e) {
			logger.error("Synchronisation des adresses - Erreur " + e.getMessage());
			throw new JobExecutionException(e.getMessage());
		}

	}

	/**
	 * Synchronisation des adresses
	 * 
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public void synchronisationAdresse() throws TechnicalException,
			BusinessException {

		// Récupération des toutes les adresses consolidées
		List<AdressesConsolideesEntity> listeAdressesConsolidees = adresseConsolideesService
				.getAll();

		for (AdressesConsolideesEntity adressesConsolidees : listeAdressesConsolidees) {

			// On recherche si l'adresse existe déjà dans Adresse
			Adresse adresse = adresseService
					.getAdresseByObjectId(adressesConsolidees.getObjectId());

			if (adresse == null) {

				// Adresse non trouvée dans la table Adresse, on la crée
				adresse = new Adresse();

				adresse.setObjectId(adressesConsolidees.getObjectId());
				adresse.setAdresseLigne1(adressesConsolidees
						.getLibelleComplementAdresse());
				adresse.setQuartier(adressesConsolidees.getQuartier());
				adresse.setCommune(adressesConsolidees.getCommune());
				logger.trace("Synchronisation des adresses - Création de l'adresse " + adressesConsolidees.getObjectId());
				adresseService.create(adresse);
			} else {
				// Adresse trouvée, on la met à jour si elle a changé
				if (!adresse.getAdresseLigne1().equals(
						adressesConsolidees.getLibelleComplementAdresse())
						|| !adresse.getQuartier().equals(
								adressesConsolidees.getQuartier())
						|| !adresse.getCommune().equals(
								adressesConsolidees.getCommune())) {

					logger.trace("Synchronisation des adresses - Mise à jour de l'adresse "
							+ adresse.getObjectId()
							+ ";"
							+ adressesConsolidees.getLibelleComplementAdresse()
							+ ";"
							+ adressesConsolidees.getQuartier()
							+ ";"
							+ adressesConsolidees.getCommune());

					adresse.setAdresseLigne1(adressesConsolidees
							.getLibelleComplementAdresse());
					adresse.setQuartier(adressesConsolidees.getQuartier());
					adresse.setCommune(adressesConsolidees.getCommune());
					adresseService.update(adresse);
				}
			}
		}

		// Récupération de toutes les adresses
		List<Adresse> listeAdresse = adresseService.getAll();

		for (Adresse adresse : listeAdresse) {

			AdressesConsolideesEntity adressesConsolidees = adresseConsolideesService
					.getAdresseByObjectId(adresse.getObjectId());

			if (adressesConsolidees == null) {
				// l'adresse n'existe plus, on tente de la supprimer
				try {
					adresseService.delete(adresse);

					logger.trace("Synchronisation des adresses - Suppression de l'adresse "
							+ adresse.getObjectId()
							+ ";"
							+ adresse.getAdresseLigne1()
							+ ";"
							+ adresse.getQuartier()
							+ ";"
							+ adresse.getCommune());

				} catch (SuppressionImpossibleException e) {
					// L'adresse est utilisée, on met un libellé clair

					if (!adresse.isObsolete()) {
						logger.warn("Synchronisation des adresses - Suppression impossible d'adresse - top obsolète "
								+ adresse.getObjectId()
								+ ";"
								+ adresse.getAdresseLigne1()
								+ ";"
								+ adresse.getQuartier()
								+ ";"
								+ adresse.getCommune());

						adresse.setAdresseLigne1("OBSOLETE - "
								+ adresse.getAdresseLigne1());
						adresse.setObsolete(true);
						adresseService.update(adresse);
					}
				}
			}

		}
	}

}
