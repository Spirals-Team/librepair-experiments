package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.TypeSourceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSource;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSourceService;
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
@Service("typeSourceService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TypeSourceBusiness extends
		AbstractCrudBusiness<TypeSource, TypeSourceEntity> implements
		TypeSourceService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les types de source
	 * 
	 * @return Liste des types de source
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<TypeSource> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<TypeSourceEntity> listeTypeSourceEntity = (List<TypeSourceEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()+ " order by libelle").list();

		List<TypeSource> lst = cm.convertList(listeTypeSourceEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les types de source sous forme de code Label
	 * 
	 * @return Liste des types de source
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
	public Class<TypeSourceEntity> getEntityClass() {
		return TypeSourceEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<TypeSource> getDtoClass() {
		return TypeSource.class;
	}


}
