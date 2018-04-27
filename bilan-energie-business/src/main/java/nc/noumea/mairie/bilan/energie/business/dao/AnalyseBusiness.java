package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.AnalyseEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Analyse;
import nc.noumea.mairie.bilan.energie.contract.service.AnalyseService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des analyses
 * 
 * @author Greg Dujardin
 * 
 */
@Service("analyseService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AnalyseBusiness extends
		AbstractCrudBusiness<Analyse, AnalyseEntity> implements
		AnalyseService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de toutes les analyses
	 * 
	 * @return  Liste des analyses
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<Analyse> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<AnalyseEntity> listeAnalyseEntity = (List<AnalyseEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName() + " order by date_modif").list();

		List<Analyse> lst = cm.convertList(listeAnalyseEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<AnalyseEntity> getEntityClass() {
		return AnalyseEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Analyse> getDtoClass() {
		return Analyse.class;
	}


}
