package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.UtilisateurEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.exceptions.NotFoundException;
import nc.noumea.mairie.bilan.energie.contract.exceptions.TooManyValueException;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des utilisateurs
 * 
 * @author Greg Dujardin
 * 
 */
@Service("utilisateurService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UtilisateurBusiness extends
		AbstractCrudBusiness<Utilisateur, UtilisateurEntity> implements
		UtilisateurService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/**
	 * Récupération de tous les utilisateurs
	 * 
	 * @return Liste des utilisateurs
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public List<Utilisateur> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<UtilisateurEntity> listeUtilisateurEntity = (List<UtilisateurEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " order by nom, prenom").list();

		List<Utilisateur> lst = cm.convertList(listeUtilisateurEntity,
				getDtoClass());

		return lst;
	}

	/**
	 * Récupération d'un utilisateur par son login
	 * 
	 * @param login
	 *            login recherché
	 * @return l'utilisateur
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public Utilisateur getUtilisateurByLogin(String login)
			throws TechnicalException, BusinessException {

		@SuppressWarnings("unchecked")
		List<UtilisateurEntity> listeUtilisateurEntity = (List<UtilisateurEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " where upper(login) = :login")
				.setParameter("login", login.toUpperCase().trim()).list();

		if (listeUtilisateurEntity.size() == 0)
			throw new NotFoundException("Aucun utilisateur ne correspond à "
					+ login);
		else if (listeUtilisateurEntity.size() > 1)
			throw new TooManyValueException(
					"Plusieurs utilisateurs correspondent à " + login);

		return cm.convert(listeUtilisateurEntity.get(0), getDtoClass());
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<UtilisateurEntity> getEntityClass() {
		return UtilisateurEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Utilisateur> getDtoClass() {
		return Utilisateur.class;
	}

	/**
	 * Récupération des utilisateurs par le nom
	 * 
	 * @param nom
	 *            Nom à chercher
	 * @param historique
	 *            Critère historique
	 * @return Liste des utilisateurs
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public List<Utilisateur> getAllByNom(String nom, boolean historique)
			throws TechnicalException, BusinessException {

		String nomParam = "%" + nom.toUpperCase() + "%";

		String critere = " where (upper(nom) like :nomParam or upper(login) like :nomParam)";

		Date dateJour = new Date();
		if (!historique) {
			critere = critere
					+ " and (date_fin is null or date_fin >= :dateParam)";
		}

		Query query = (Query) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName() + critere
								+ " order by nom, prenom")
				.setParameter("nomParam", nomParam);

		if (!historique)
			query.setParameter("dateParam", dateJour);

		@SuppressWarnings("unchecked")
		List<UtilisateurEntity> listeUtilisateurEntity = query.list();

		return cm.convertList(listeUtilisateurEntity, getDtoClass());
	}
}
