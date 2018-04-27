package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.TypePoliceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeSourceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypePolice;
import nc.noumea.mairie.bilan.energie.contract.service.TypePoliceService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des types de police
 * 
 * @author Greg Dujardin
 * 
 */
@Service("typePoliceService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TypePoliceBusiness extends
		AbstractCrudBusiness<TypePolice, TypePoliceEntity> implements
		TypePoliceService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les types de police
	 * 
	 * @return Liste des types de police
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<TypePolice> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<TypePoliceEntity> listeTypePoliceEntity = (List<TypePoliceEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName() + " order by libelle").list();

		List<TypePolice> lst = cm.convertList(listeTypePoliceEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les types de police sous forme de code Label
	 * 
	 * @return Liste des types de polices
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
	public Class<TypePoliceEntity> getEntityClass() {
		return TypePoliceEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<TypePolice> getDtoClass() {
		return TypePolice.class;
	}


}
