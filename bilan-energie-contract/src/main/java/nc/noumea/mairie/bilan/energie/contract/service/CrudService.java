package nc.noumea.mairie.bilan.energie.contract.service;

import java.io.Serializable;

import nc.noumea.mairie.bilan.energie.contract.dto.DtoModel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du Crud Service
 * 
 * @author Greg Dujardin
 *
 * @param <DTO> Type de DTO
 */
public interface CrudService<DTO extends DtoModel> {
	
	/**
	 * Création d'un objet
	 * 
	 * @param dto Dto à créer
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	DTO create(DTO dto) throws TechnicalException, BusinessException;
	
	/**
	 * Lecture d'un objet
	 * 
	 * @param id Id de l'object à chercher
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	DTO read(Serializable id) throws TechnicalException, BusinessException;
	
	/**
	 * Mise à jour d'un objet
	 * 
	 * @param dto Dto à mettre à jour
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	DTO update(DTO dto) throws TechnicalException, BusinessException;
	
	/**
	 * Suppression d'un objet
	 * 
	 * @param dto Dto à supprimer
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	void delete (DTO dto) throws TechnicalException, BusinessException;

	void validate(DTO dto) throws BusinessException, TechnicalException;

}