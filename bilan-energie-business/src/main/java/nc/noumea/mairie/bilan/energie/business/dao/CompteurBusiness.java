package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.CompteurEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.*;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.exceptions.BusinessValidationException;
import nc.noumea.mairie.bilan.energie.contract.service.CompteurService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des compteurs
 *
 * @author Greg Dujardin
 *
 */
@Service("compteurService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompteurBusiness extends
		AbstractCrudBusiness<Compteur, CompteurEntity> implements
		CompteurService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	@Autowired
	private PoliceService policeService;


	/**
	 * Recherche des compteurs par numéro de compteur exact
	 * @param numeroCompteur Numéro de compteur
	 *
	 * @return Liste des compteurs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<Compteur> getAllByNumeroCompteurExact(String numeroCompteur)
			throws TechnicalException, BusinessException {

		String numeroCompteurParam = numeroCompteur.toUpperCase() ;

		@SuppressWarnings("unchecked")
		List<CompteurEntity> listeCompteurEntity = (List<CompteurEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " where upper(num_compteur) = :numeroCompteurParam")
				.setParameter("numeroCompteurParam", numeroCompteurParam).list();


		return cm.convertList(listeCompteurEntity, getDtoClass());
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<CompteurEntity> getEntityClass() {
		return CompteurEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Compteur> getDtoClass() {
		return Compteur.class;
	}

	@Override
	public void validate(Compteur compteur) throws BusinessException, TechnicalException {
		super.validate(compteur);
		validateNumeroDoublon(compteur);
		validateDates(compteur);
		validateDatePolice(compteur);
	}

	private void validateDatePolice(Compteur compteur) throws TechnicalException, BusinessException {
		Police police = getPolice(compteur);
		if (compteur.getDateFin() == null && police.getDateFin() != null){
			throw new BusinessValidationException(CompteurService.ERROR_DATE_FIN_NON_RENSEIGNEE);
		}
		if (!Periodique.isInPeriode(compteur, police)){
			throw new BusinessValidationException(CompteurService.ERROR_HORS_PERIODE_POLICE);
		}
	}

	private void validateDates(Compteur compteur) throws TechnicalException, BusinessException {
		if (compteur.getDateFin() != null && compteur.getDateFin().before(compteur.getDateDebut())){
			throw new BusinessValidationException(CompteurService.ERROR_DATE_FIN_NOK);
		}
	}

	private void validateNumeroDoublon(Compteur compteur) throws TechnicalException, BusinessException {
		List<Compteur> listeCompteur = getAllByNumeroCompteurExact(compteur.getNumCompteur());
		if (listeCompteur != null && !listeCompteur.isEmpty()) {
			for (Compteur compteurExistant : listeCompteur){
				if (!compteurExistant.getId().equals(compteur.getId())){
					if (Periodique.periodeChevauche(compteur, compteurExistant)) {
						throw new BusinessValidationException(CompteurService.ERROR_NUMERO_DOUBLON_PERIODE + compteur.getNumCompteur() + "' (police n°" + getPolice(compteurExistant).getNumPolice() + ")");
					}
					if (compteur.getIdPolice().equals(compteurExistant.getIdPolice())) {
						throw new BusinessValidationException(CompteurService.ERROR_NUMERO_DOUBLON_POLICE + compteur.getNumCompteur() + "' (police n°" + getPolice(compteurExistant).getNumPolice() + ")");
					}
				}
			}
		}
	}

	private Police getPolice(Compteur compteur) throws TechnicalException, BusinessException {
		return policeService.read(compteur.getIdPolice());
	}
}
