package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.CodeCtmeEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeCtme;
import nc.noumea.mairie.bilan.energie.contract.service.CodeCtmeService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des codes Ctme
 * 
 * @author Greg Dujardin
 * 
 */
@Service("codeCtmeService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CodeCtmeBusiness extends
		AbstractCrudBusiness<CodeCtme, CodeCtmeEntity> implements
		CodeCtmeService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les codes Ctme
	 * 
	 * @return  Liste des codes CTME
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeCtme> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<CodeCtmeEntity> listeCodeCtmeEntity = (List<CodeCtmeEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()+ " order by libelle").list();

		List<CodeCtme> lst = cm.convertList(listeCodeCtmeEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les codes Ctme sous forme de code Label
	 * 
	 * @return  Liste des codes CTME
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentiel() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		List<CodeCtmeEntity> listeCodeCtmeEntity = (List<CodeCtmeEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by libelle").list();

		List<CodeLabel> lst = cm.convertList(listeCodeCtmeEntity,
				CodeLabel.class);
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<CodeCtmeEntity> getEntityClass() {
		return CodeCtmeEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<CodeCtme> getDtoClass() {
		return CodeCtme.class;
	}


}
