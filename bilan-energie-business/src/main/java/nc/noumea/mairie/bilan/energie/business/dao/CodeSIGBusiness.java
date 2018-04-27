package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.CodeSIGEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeSIG;
import nc.noumea.mairie.bilan.energie.contract.service.CodeSIGService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des codes SIG
 * 
 * @author Greg Dujardin
 * 
 */
@Service("codeSIGService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CodeSIGBusiness extends
		AbstractCrudBusiness<CodeSIG, CodeSIGEntity> implements
		CodeSIGService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les codes SIG
	 * 
	 * @return  Liste des codes SIG
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeSIG> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<CodeSIGEntity> listeCodeSIGEntity = (List<CodeSIGEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()+ " order by libelle").list();

		List<CodeSIG> lst = cm.convertList(listeCodeSIGEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tous les codes SIG sous forme de code Label
	 * 
	 * @return Liste des codes SIG 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentiel() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		List<CodeSIGEntity> listeCodeSIGEntity = (List<CodeSIGEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by libelle").list();

		List<CodeLabel> lst = cm.convertList(listeCodeSIGEntity,
				CodeLabel.class);
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<CodeSIGEntity> getEntityClass() {
		return CodeSIGEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<CodeSIG> getDtoClass() {
		return CodeSIG.class;
	}


}
