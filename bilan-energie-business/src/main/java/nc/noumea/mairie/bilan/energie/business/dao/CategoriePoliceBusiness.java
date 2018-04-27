package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.CategoriePoliceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeSourceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CategoriePolice;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.service.CategoriePoliceService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des types source
 * 
 * @author Greg Dujardin
 * 
 */
@Service("categoriePoliceService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoriePoliceBusiness extends
		AbstractCrudBusiness<CategoriePolice, CategoriePoliceEntity> implements
		CategoriePoliceService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les catégories de police
	 * 
	 * @return  Liste des catégories de police
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CategoriePolice> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<CategoriePoliceEntity> listeCategoriePoliceEntity = (List<CategoriePoliceEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()+ " order by libelle").list();

		List<CategoriePolice> lst = cm.convertList(listeCategoriePoliceEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les types de source sous forme de code Label
	 * 
	 * @return  Liste des catégories de police
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
	public Class<CategoriePoliceEntity> getEntityClass() {
		return CategoriePoliceEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<CategoriePolice> getDtoClass() {
		return CategoriePolice.class;
	}


}
