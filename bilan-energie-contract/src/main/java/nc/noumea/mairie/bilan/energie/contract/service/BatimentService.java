package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.contract.dto.BatimentSimple;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Batiment
 * 
 * @author Greg Dujardin
 * 
 */
public interface BatimentService extends CrudService<Batiment> {

	/**
	 * Récupération de tous les batiments
	 * 
	 * @return Liste des batiments
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	List<BatimentSimple> getAll() throws TechnicalException, BusinessException;

	/**
	 * Recherche de batiments multi critères
	 * 
	 * @param designation
	 *            Désignation
	 * @param numPolice
	 *            Numéro de police
	 * @param numCompteur
	 *            Numéro de compteur
	 * @param adresse
	 *            Adresse 
	 * @param idInfrastructure
	 *            Identifant de l'infrastructure
	 * @param historique
	 *            Critère historique
	 * @return Liste des Batiments
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	List<BatimentSimple> getAllByCriteres(String designation, String numPolice,
			String numCompteur, String adresse, Long idInfrastructure,
			boolean historique) throws TechnicalException, BusinessException;

}
