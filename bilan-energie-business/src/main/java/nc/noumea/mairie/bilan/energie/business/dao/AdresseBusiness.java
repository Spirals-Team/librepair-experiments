package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.AdresseEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Adresse;
import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.contract.exceptions.TooManyValueException;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des adresses
 * 
 * @author Greg Dujardin
 * 
 */
@Service("adresseService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdresseBusiness extends
		AbstractCrudBusiness<Adresse, AdresseEntity> implements AdresseService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/** SessionFactory */
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Adresse> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<AdresseEntity> listeAdresseEntity = (List<AdresseEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName()).list();

		List<Adresse> lst = cm.convertList(listeAdresseEntity, this.getDtoClass());

		return lst;
	}

	@Override
	public List<AdresseLabel> getAllAdresseByVoie(String nomVoie)
			throws TechnicalException, BusinessException {

		String nomVoieParam = "%" + nomVoie.toUpperCase() + "%";

		@SuppressWarnings("unchecked")
		List<AdresseEntity> listeAdresseEntity = (List<AdresseEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " where adresse_ligne1 like :nomVoieParam")
				.setParameter("nomVoieParam", nomVoieParam).list();

		return cm.convertList(listeAdresseEntity, AdresseLabel.class);
	}

	/** Récupération de la class de l'entité */
	public Class<AdresseEntity> getEntityClass() {
		return AdresseEntity.class;
	}

	/** Récupération de la class du DTO */
	public Class<Adresse> getDtoClass() {
		return Adresse.class;
	}

	@Override
	public Adresse getAdresseByObjectId(Long objectId)
			throws TechnicalException, BusinessException {
		
		@SuppressWarnings("unchecked")
		List<AdresseEntity> listeAdresseEntity = (List<AdresseEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " where objectid = :objectid")
				.setParameter("objectid", objectId).list();
		
		if (listeAdresseEntity.size() == 0) 
			return null;
		else if (listeAdresseEntity.size() > 1)
			throw new TooManyValueException("Plusieurs adresses correspondent à objectId :" + objectId);

		return cm.convert(listeAdresseEntity.get(0),getDtoClass());
	}
}
