package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.ParametrageEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.NotFoundException;
import nc.noumea.mairie.bilan.energie.contract.exceptions.TooManyValueException;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des parametrages
 * 
 * @author Greg Dujardin
 * 
 */
@Service("parametrageService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ParametrageBusiness extends
		AbstractCrudBusiness<Parametrage, ParametrageEntity> implements
		ParametrageService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de tous les paramétrages
	 * 
	 * @return Liste des paramétrage
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<Parametrage> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<ParametrageEntity> listeParametrageEntity = (List<ParametrageEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by parametre").list();

		List<Parametrage> lst = cm.convertList(
				listeParametrageEntity, getDtoClass());

		return lst;
	}

	public Parametrage getByParametre(String parametre)  throws TechnicalException,
		BusinessException {
		@SuppressWarnings("unchecked")
		List<ParametrageEntity> listeParametrage = (List<ParametrageEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " where parametre = :parametre")
				.setParameter("parametre", parametre).list();
		
		if (listeParametrage.size() == 0) 
			throw new NotFoundException("Aucune paramètre ne correspond à " + parametre);
		else if (listeParametrage.size() > 1)
			throw new TooManyValueException("Plusieurs paramètres correspondent à " + parametre);

		return cm.convert(listeParametrage.get(0),getDtoClass());
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<ParametrageEntity> getEntityClass() {
		return ParametrageEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Parametrage> getDtoClass() {
		return Parametrage.class;
	}

}
