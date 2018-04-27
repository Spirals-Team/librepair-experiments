package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublicSimple;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service EclairagePublic
 * 
 * @author Greg Dujardin
 * 
 */
public interface EclairagePublicService extends CrudService<EclairagePublic> {

	/**
	 * Récupération de tous les éclairages publics
	 * 
	 * @return Liste des EPs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<EclairagePublicSimple> getAll() throws TechnicalException,
			BusinessException;

	/**
	 * Recherche d'éclairage public multi critères
	 * 
	 * @param numPoste Numéro de poste
	 * @param nomPoste Nom de poste
	 * @param numPolice Numéro de police
	 * @param numCompteur Numéro de compteur
	 * @param adresseCompteur Adresse du compteur
	 * @param numSupport Numéro du support
	 * @param idInfrastructure Identifant de l'infrastructure
	 * @param historique Critère historique
	 * @return Liste des EPs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<EclairagePublicSimple> getAllByCriteres(String numPoste,
			String nomPoste, String numPolice, String numCompteur,
			String adresseCompteur, String numSupport, Long idInfrastructure,
			boolean historique) throws TechnicalException, BusinessException;

	/**
	 * Recherche des modèles de support
	 * 
	 * @param modele Modèle à chercher
	 * @return Liste des modèles
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<String> getAllModele(String modele) throws TechnicalException, BusinessException;
}
