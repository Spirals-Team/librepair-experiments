package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.TypeSupportEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSupport;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSupportService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des types support
 * 
 * @author Greg Dujardin
 * 
 */
@Service("typeSupportService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TypeSupportBusiness extends
		AbstractCrudBusiness<TypeSupport, TypeSupportEntity> implements
		TypeSupportService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les types de support
	 * 
	 * @return Liste des types de support
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<TypeSupport> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<TypeSupportEntity> listeTypeSupportEntity = (List<TypeSupportEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()).list();

		List<TypeSupport> lst = cm.convertList(listeTypeSupportEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les types de support sous forme de code Label
	 * 
	 * @return Liste des types de support
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentiel() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		List<TypeSupportEntity> listeTypeSupportEntity = (List<TypeSupportEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by libelle").list();

		List<CodeLabel> lst = cm.convertList(listeTypeSupportEntity,
				CodeLabel.class);
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<TypeSupportEntity> getEntityClass() {
		return TypeSupportEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<TypeSupport> getDtoClass() {
		return TypeSupport.class;
	}


}
