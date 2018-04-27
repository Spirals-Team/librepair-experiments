package nc.noumea.mairie.bilan.energie.business.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.AbstractFactureEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FactureEauEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FactureElectriciteEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FichierFactureEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Facture;
import nc.noumea.mairie.bilan.energie.contract.dto.FactureEau;
import nc.noumea.mairie.bilan.energie.contract.dto.FactureElectricite;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFacture;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.contract.exceptions.IntrospectionException;
import nc.noumea.mairie.bilan.energie.contract.exceptions.TooManyValueException;
import nc.noumea.mairie.bilan.energie.contract.service.CompteurService;
import nc.noumea.mairie.bilan.energie.contract.service.FichierFactureService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des fichiers de facture
 * 
 * @author Greg Dujardin
 * 
 */
@Service("fichierFactureService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FichierFactureBusiness extends
		AbstractCrudBusiness<FichierFactureSimple, FichierFactureEntity>
		implements FichierFactureService {

	/** Principal */
	@Autowired
	private Principal principal;

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/** Service des polices */
	@Autowired
	private PoliceService policeService;

	/** Service des compteurs */
	@Autowired
	private CompteurService compteurService;

	/** Service des paramètres */
	@Autowired
	private ParametrageService parametrageService;

	/**
	 * Récupération de tous les fichiers de facture
	 * 
	 * @return Liste des fichiers de facture
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<FichierFactureSimple> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<FichierFactureEntity> listeFichierFactureEntity = (List<FichierFactureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " order by date_integration desc").list();

		List<FichierFactureSimple> lst = cm.convertList(
				listeFichierFactureEntity, getDtoClass());

		return lst;
	}

	/**
	 * Lecture d'un enregistrement
	 * 
	 * @param id Identifiant à chercher
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public FichierFacture readFichierFacture(Serializable id)
			throws TechnicalException, BusinessException {

		FichierFactureEntity entityMaj = (FichierFactureEntity) sessionFactory
				.getCurrentSession().get(getEntityClass(), id);

		return cm.convert(entityMaj, FichierFacture.class);
	}

	@Override
	protected Class<FichierFactureEntity> getEntityClass() {
		return FichierFactureEntity.class;
	}

	@Override
	protected Class<FichierFactureSimple> getDtoClass() {
		return FichierFactureSimple.class;
	}

	@Override
	public FichierFacture updateFichierFacture(FichierFacture fichierFacture)
			throws TechnicalException, BusinessException {

		validate(fichierFacture);

		FichierFactureEntity fichierFactureEntity = cm.convert(fichierFacture,
				getEntityClass());

		// Mise à jour des infos de modification
		fichierFactureEntity.setAuteurModif(principal.getName());
		fichierFactureEntity.setDateModif(new Date());

		try {
			sessionFactory.getCurrentSession().persist(fichierFactureEntity);
		} catch (PersistentObjectException e) {
			sessionFactory.getCurrentSession().merge(fichierFactureEntity);
		}

		Long id = null;
		try {
			id = (Long) getMethodGetId().invoke(fichierFactureEntity);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new IntrospectionException("Erreur de la recherche de l'Id",
					e.getCause());
		}

		FichierFactureEntity entityMaj = (FichierFactureEntity) sessionFactory
				.getCurrentSession().get(getEntityClass(), id);

		return cm.convert(entityMaj, FichierFacture.class);

	}

	@Override
	public FichierFacture createFichierFacture(FichierFacture fichierFacture)
			throws TechnicalException, BusinessException {

		validate(fichierFacture);

		FichierFactureEntity entity = cm.convert(fichierFacture,
				getEntityClass());

		// Mise à jour des infos de création et de modification
		entity.setAuteurCreation(principal.getName());
		entity.setDateCreation(new Date());
		entity.setAuteurModif(principal.getName());
		entity.setDateModif(new Date());

		try {
			sessionFactory.getCurrentSession().persist(entity);
		} catch (PersistentObjectException e) {
			sessionFactory.getCurrentSession().merge(entity);
		}

		Long id = null;
		try {
			id = (Long) getMethodGetId().invoke(entity);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new IntrospectionException("Erreur de la recherche de l'Id",
					e.getCause());
		}

		FichierFactureEntity entityMaj = (FichierFactureEntity) sessionFactory
				.getCurrentSession().get(getEntityClass(), id);

		return cm.convert(entityMaj, FichierFacture.class);
	}

	@Override
	public Facture getFactureByCriteres(String numPolice, String numCompteur,
			Long numFacture, Long anneeFacturation, Long moisFacturation)
			throws TechnicalException, BusinessException {

		// Recherche dans factures d'électricité
		@SuppressWarnings("unchecked")
		List<AbstractFactureEntity> listeAbstractFactureEntity = (List<AbstractFactureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + FactureElectriciteEntity.class.getName()
								+ " where police = :numPolice"
								+ " and cpt = :numCompteur"
								+ " and fac = :numFacture"
								+ " and anfac = :anneeFacturation"
								+ " and moisfac = :moisFacturation")
				.setParameter("numPolice", numPolice)
				.setParameter("numCompteur", numCompteur)
				.setParameter("numFacture", numFacture)
				.setParameter("anneeFacturation", anneeFacturation)
				.setParameter("moisFacturation", moisFacturation).list();

		// Recherche dans les factures d'eau
		@SuppressWarnings("unchecked")
		List<AbstractFactureEntity> listeFactureEau = (List<AbstractFactureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + FactureEauEntity.class.getName()
								+ " where police = :numPolice"
								+ " and cpt = :numCompteur"
								+ " and fac = :numFacture"
								+ " and anfac = :anneeFacturation"
								+ " and moisfac = :moisFacturation")
				.setParameter("numPolice", numPolice)
				.setParameter("numCompteur", numCompteur)
				.setParameter("numFacture", numFacture)
				.setParameter("anneeFacturation", anneeFacturation)
				.setParameter("moisFacturation", moisFacturation).list();

		listeAbstractFactureEntity.addAll(listeFactureEau);

		if (listeAbstractFactureEntity.size() == 0)
			return null;
		else if (listeAbstractFactureEntity.size() > 1)
			throw new TooManyValueException(
					"Plusieurs factures correspondent aux critères "
							+ numPolice + ", " + numCompteur + ", "
							+ numFacture + "," + anneeFacturation + ","
							+ moisFacturation);
		else {
			AbstractFactureEntity factureEntity = listeAbstractFactureEntity.get(0);
			if (factureEntity.getClass().equals(FactureElectriciteEntity.class))
				return cm.convert(factureEntity, FactureElectricite.class);
			else
				return cm.convert(factureEntity, FactureEau.class);
		}
	}

	public void validate(FichierFacture fichierFacture) throws BusinessException, TechnicalException {
		super.validate(cm.convert(fichierFacture, FichierFactureSimple.class));
	}
}
