package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.BatimentEntity;
import nc.noumea.mairie.bilan.energie.business.entities.EclairagePublicEntity;
import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.*;
import nc.noumea.mairie.bilan.energie.contract.exceptions.BusinessValidationException;
import nc.noumea.mairie.bilan.energie.contract.service.CompteurService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des polices
 * 
 * @author Greg Dujardin
 * 
 */
@Service("policeService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PoliceBusiness extends AbstractCrudBusiness<Police, PoliceEntity>
		implements PoliceService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	@Autowired
	private CompteurService compteurService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Récupération de toutes les polices
	 * 
	 * @return Liste des polices
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<PoliceSimple> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<PoliceEntity> listePoliceEntity = (List<PoliceEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " order by date_modif desc").list();

		List<PoliceSimple> lst = cm.convertList(listePoliceEntity,
				PoliceSimple.class);

		return lst;
	}

	/**
	 * Recherche des polices par numéro de police
	 * 
	 * @param numeroPolice Numéro de police
	 * 
	 * @return Liste des polices
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<PoliceSimple> getAllByNumeroPolice(String numeroPolice,
			boolean historique, boolean chercherEP, boolean chercherBatiment)
			throws TechnicalException, BusinessException {

		String numeroPoliceParam = "%" + numeroPolice.toUpperCase() + "%";
		
		String critere = " where upper(num_police) like :numeroPoliceParam";
		
		if (!historique)
			critere = critere + " and date_fin is null";
		
		if (!chercherEP)
			critere = critere + " and exists (select id from " + BatimentEntity.class.getName() + " where id = structure_id)";

		if (!chercherBatiment)
			critere = critere + " and exists (select id from " + EclairagePublicEntity.class.getName() + "  where id = structure_id)";

		
		@SuppressWarnings("unchecked")
		List<PoliceEntity> listePoliceEntity = (List<PoliceEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ critere
								+ " order by date_modif desc")
				.setParameter("numeroPoliceParam", numeroPoliceParam).list();

		return cm.convertList(listePoliceEntity, PoliceSimple.class);
	}

	/**
	 * Recherche des polices par numéro de police exact
	 * 
	 * @param numeroPolice Numéro de police
	 * 
	 * @return Liste des polices
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<Police> getAllByNumeroPoliceExact(String numeroPolice)
			throws TechnicalException, BusinessException {

		String numeroPoliceParam = numeroPolice.toUpperCase();

		@SuppressWarnings("unchecked")
		List<PoliceEntity> listePoliceEntity = (List<PoliceEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " where upper(num_police) = :numeroPoliceParam")
				.setParameter("numeroPoliceParam", numeroPoliceParam).list();

		return cm.convertList(listePoliceEntity, getDtoClass());
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<PoliceEntity> getEntityClass() {
		return PoliceEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Police> getDtoClass() {
		return Police.class;
	}


	@Override
	public void validate(Police police) throws BusinessException, TechnicalException {
        super.validate(police);

		List<Police> listePolice = getAllByNumeroPoliceExact(police.getNumPolice());

		validateNumeroDoublon(police, listePolice);
		validateDates(police);
		//TODO : à réactiver une fois les dates des compteurs valides par rapport aux dates des polices
		//validateListeCompteur(police);
	}

	private void validateNumeroDoublon(Police police, List<Police> listePolice) throws BusinessValidationException {
		if (listePolice != null && !listePolice.isEmpty()) {
			for (Police policeExistante : listePolice){
				if (!policeExistante.getId().equals(police.getId())){
					throw new BusinessValidationException(PoliceService.ERROR_NUMERO_DOUBLON + police.getNumPolice()+"'");
				}
			}
		}
	}

	private void validateDates(Police police) throws BusinessValidationException {
		if (police.getDateFin() != null && police.getDateFin().before(police.getDateDebut())){
			throw new BusinessValidationException(PoliceService.ERROR_DATE_FIN_NOK);
		}
	}

	private void validateListeCompteur(Police police) throws BusinessException, TechnicalException{
		if (police.getListeCompteur() != null){
			for (Compteur compteur : police.getListeCompteur()){
				try {
					compteurService.validate(compteur);
				} catch (BusinessValidationException e) {
					throw new BusinessValidationException("Compteur n°" + compteur.getNumCompteur() + " : " + e.getMessage(), e.getCause());
				}
			}
		}
	}
}
