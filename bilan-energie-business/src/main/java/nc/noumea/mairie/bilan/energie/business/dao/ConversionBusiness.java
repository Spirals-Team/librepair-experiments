package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.ConversionEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Conversion;
import nc.noumea.mairie.bilan.energie.contract.service.ConversionService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des conversions
 * 
 * @author Greg Dujardin
 * 
 */
@Service("conversionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConversionBusiness extends
		AbstractCrudBusiness<Conversion, ConversionEntity> implements
		ConversionService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tout le référentiel de conversion
	 * 
	 * @return Liste des taux de conversion
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<Conversion> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<ConversionEntity> listeConversionEntity = (List<ConversionEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName()+ " order by unite_ges").list();

		List<Conversion> lst = cm.convertList(listeConversionEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de tout le référentiel de conversion sous forme de code Label
	 * 
	 * @return Liste des taux de conversion
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentiel() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		List<ConversionEntity> listeConversionEntity = (List<ConversionEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by unite_ges").list();

		List<CodeLabel> lst = cm.convertList(listeConversionEntity,
				CodeLabel.class);
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<ConversionEntity> getEntityClass() {
		return ConversionEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Conversion> getDtoClass() {
		return Conversion.class;
	}


}
