package nc.noumea.mairie.bilan.energie.business.job;

import nc.noumea.mairie.bilan.energie.contract.service.IntegrationFactureService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Job Quartz d'intégration des factures
 * 
 * @author Greg Dujardin
 *
 */
@DisallowConcurrentExecution
public class JobIntegrationFacture implements Job  {

	@Autowired
	private IntegrationFactureService integrationFactureService;
	
	/**
	 * Méthode d'exécution du job 
	 * 
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		Logger logger = LoggerFactory.getLogger("integrationFacture");

		
		// Appel de l'intégration des factures sur service d'intégration
		try {
			logger.info("Intégration des factures - Début de traitement");
			integrationFactureService.integrationFichierFacture();
			logger.info("Intégration des factures - Fin de traitement");
		} catch (BusinessException | TechnicalException e) {
			logger.error("Intégration des factures - Erreur " + e.getMessage());
			throw new JobExecutionException(e.getMessage());
		}		

	}



}
