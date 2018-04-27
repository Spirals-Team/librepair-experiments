package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.TypeCompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeSourceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeCompteur;
import nc.noumea.mairie.bilan.energie.contract.service.TypeCompteurService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des types de compteur
 * 
 * @author Greg Dujardin
 * 
 */
@Service("typeCompteurService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TypeCompteurBusiness extends
		AbstractCrudBusiness<TypeCompteur, TypeCompteurEntity> implements
		TypeCompteurService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les types de compteur
	 * 
	 * @return Liste des types de compteur
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<TypeCompteur> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<TypeCompteurEntity> listeTypeCompteurEntity = (List<TypeCompteurEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()).list();

		List<TypeCompteur> lst = cm.convertList(listeTypeCompteurEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les types de compteur sous forme de code Label
	 * 
	 * @return Liste des types de compteur
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
	public Class<TypeCompteurEntity> getEntityClass() {
		return TypeCompteurEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<TypeCompteur> getDtoClass() {
		return TypeCompteur.class;
	}


}
