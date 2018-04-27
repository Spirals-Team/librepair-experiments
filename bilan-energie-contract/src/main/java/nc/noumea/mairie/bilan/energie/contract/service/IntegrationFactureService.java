package nc.noumea.mairie.bilan.energie.contract.service;

import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Facture;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFacture;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import java.io.IOException;

/**
 * Interface du service d'intégration des fichiers
 * 
 * @author Greg Dujardin
 *
 */
public interface IntegrationFactureService {

	/**
	 * Intégration d'un fichier de facture
	 * 
	 * @throws BusinessException Exception métier 
     * @throws TechnicalException Exception technique 
	 */
	void integrationFichierFacture() throws TechnicalException, BusinessException;

	/**
	 * Intégration des factures dans la table comptage
	 *
	 * @param fichierFacture
	 *            Ficher de facture à intégrer
	 * @return fichier de facture intégré
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	FichierFacture integrerComptage(FichierFacture fichierFacture) throws TechnicalException, BusinessException;


	Comptage integrerFacture(Facture facture) throws TechnicalException, BusinessException;

	void reIntegrerFichierFacture(FichierFactureSimple fichierFacture) throws TechnicalException, BusinessException, IOException;
}
