package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.TypeEmplacementEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeSourceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeEmplacement;
import nc.noumea.mairie.bilan.energie.contract.service.TypeEmplacementService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des types d'emplacement
 * 
 * @author Greg Dujardin
 * 
 */
@Service("typeEmplacementService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TypeEmplacementBusiness extends
		AbstractCrudBusiness<TypeEmplacement, TypeEmplacementEntity> implements
		TypeEmplacementService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les types d'emplacement
	 * 
	 * @return Liste des types d'emplacement
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<TypeEmplacement> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<TypeEmplacementEntity> listeTypeEmplacementEntity = (List<TypeEmplacementEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()).list();

		List<TypeEmplacement> lst = cm.convertList(listeTypeEmplacementEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les types d'emplacement sous forme de code Label
	 * 
	 * @return Liste des types d'emplacement
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentiel() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		List<TypeSourceEntity> listeTypeSourceEntity = (List<TypeSourceEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by libelle").list();

		List<CodeLabel> lst = cm.convertList(listeTypeSourceEntity,
				CodeLabel.class);
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<TypeEmplacementEntity> getEntityClass() {
		return TypeEmplacementEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<TypeEmplacement> getDtoClass() {
		return TypeEmplacement.class;
	}


}
