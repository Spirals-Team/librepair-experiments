package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Infrastructure;
import nc.noumea.mairie.bilan.energie.contract.dto.InfrastructureSimple;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Infrastructure
 * 
 * @author Greg Dujardin
 *
 */
public interface InfrastructureService extends CrudService<Infrastructure> {

	/**
	 * Récupération de toutes les infrastructures
	 * 
	 * @return Liste de toutes les infrastructures
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	List<InfrastructureSimple> getAll() throws TechnicalException,
			BusinessException;

	/**
	 * Récupération de la liste des infrastructures sous forme de CodeLabel
	 * 
	 * @param chercherEP
	 *            Critère 'chercher les EPs'
	 * @param chercherBatiment
	 *            'Critère 'chercher les Batiment'
	 * @return Liste des infrastructures sous forme CodeLabel
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	List<CodeLabel> getAllReferentielByCriteres(boolean chercherEP,
			boolean chercherBatiment) throws TechnicalException,
			BusinessException;

	/**
	 * Recherche des infrastructures par désignation, type et historique
	 * 
	 * @param designation
	 *            Critère désignation
	 * @param historique
	 *            Critère historique
	 * @param chercherEP
	 *            Critère 'chercher les EPs'
	 * @param chercherBatiment
	 *            'Critère 'chercher les Batiment'
	 * @return Liste des infrastructures
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	List<InfrastructureSimple> getAllByDesignation(String designation,
			boolean historique, boolean chercherEP, boolean chercherBatiment)
			throws TechnicalException, BusinessException;
}
