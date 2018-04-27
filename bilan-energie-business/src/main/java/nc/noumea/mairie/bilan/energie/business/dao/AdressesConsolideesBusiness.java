package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.AdressesConsolideesEntity;
import nc.noumea.mairie.bilan.energie.contract.exceptions.TooManyValueException;
import nc.noumea.mairie.bilan.energie.contract.exceptions.ValidatorException;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des adresses consolidées
 * 
 * @author Greg Dujardin
 * 
 */
@Service("adressesConsolideesService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class AdressesConsolideesBusiness {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/** SessionFactory */
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * Recherche de toutes les Adresses consolidées
	 * 
	 * @return Liste des adresses
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	public List<AdressesConsolideesEntity> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<AdressesConsolideesEntity> listeAdressesConsolideesEntity = (List<AdressesConsolideesEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + AdressesConsolideesEntity.class.getName())
				.list();

		return listeAdressesConsolideesEntity;
	}

	/**
	 * Recherche d'une adresse consolidée par ObjectId
	 * 
	 * @param objectId ObjectId à chercher
	 * @return  Liste des adresses
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	public AdressesConsolideesEntity getAdresseByObjectId(Long objectId)
			throws TechnicalException, BusinessException {

		@SuppressWarnings("unchecked")
		List<AdressesConsolideesEntity> listeAdressesConsolideesEntity = (List<AdressesConsolideesEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + AdressesConsolideesEntity.class.getName()
								+ " where objectid = :objectid")
				.setParameter("objectid", objectId).list();

		if (listeAdressesConsolideesEntity.size() == 0)
			return null;
		else if (listeAdressesConsolideesEntity.size() > 1)
			throw new TooManyValueException(
					"Plusieurs adresses correspondent à objectId :" + objectId);

		return cm.convert(listeAdressesConsolideesEntity.get(0),
				AdressesConsolideesEntity.class);
	}

	/**
	 * Création d'une adresse consolidée
	 * 
	 * @param adressesConsolideesEntity Création d'une adresse consolidée
	 * @return Adresse Consolidée créé
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 * @throws ValidatorException Exception de validation
	 */
	public AdressesConsolideesEntity create(AdressesConsolideesEntity adressesConsolideesEntity) throws ValidatorException, TechnicalException,
			BusinessException {


		sessionFactory.getCurrentSession().persist(adressesConsolideesEntity);

		Long id = adressesConsolideesEntity.getObjectId();

		AdressesConsolideesEntity entityMaj = (AdressesConsolideesEntity) sessionFactory.getCurrentSession().get(
				AdressesConsolideesEntity.class, id);

		return entityMaj;
	}

}
