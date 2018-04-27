package nc.noumea.mairie.bilan.energie.contract.service;

import java.io.Serializable;
import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Facture;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFacture;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Fichier Facture
 * 
 * @author Greg Dujardin
 *
 */
public interface FichierFactureService extends CrudService<FichierFactureSimple>{

	/**
	 * Récupération de tous les fichiers de factures
	 * 
	 * @return Liste des fichiers de facture
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<FichierFactureSimple> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Lecture d'un FichieFacture
	 * 
	 * @param id identifiant à rechercher
	 * @return FichierFacture
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	FichierFacture readFichierFacture(Serializable id) throws TechnicalException, BusinessException;
	
	/**
	 * Mise à jour d'un FichierFacture
	 * 
	 * @param fichierFacture FichierFacture à mettre à jour
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	FichierFacture updateFichierFacture(FichierFacture fichierFacture) throws TechnicalException, BusinessException;

	/**
	 * Création d'un FichierFacture
	 * 
	 * @param fichierFacture FichierFacture à mettre à jour
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	FichierFacture createFichierFacture(FichierFacture fichierFacture) throws TechnicalException, BusinessException;

	/**
	 * Recherche d'une facture selon des critères
	 * 
	 * @param numPolice Numéro de police
	 * @param numCompteur Numéro de compteur
	 * @param numFacture Numéro de facture
	 * @param anneeFacturation Année de la facture
	 * @param moisFacturation Mois de la facture
	 * @return Facture
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	Facture getFactureByCriteres(String numPolice, String numCompteur, Long numFacture, Long anneeFacturation, Long moisFacturation) throws TechnicalException, BusinessException;
}
