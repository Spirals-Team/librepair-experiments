package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.AdresseEntity;
import nc.noumea.mairie.bilan.energie.business.entities.BatimentEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.contract.dto.BatimentSimple;
import nc.noumea.mairie.bilan.energie.contract.service.BatimentService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des batiments
 * 
 * @author Greg Dujardin
 * 
 */
@Service("batimentService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BatimentBusiness extends
		AbstractCrudBusiness<Batiment, BatimentEntity> implements
		BatimentService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/**
	 * Récupération de tous les batiments
	 * 
	 * @return Liste des batiments
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<BatimentSimple> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<BatimentEntity> listeBatimentEntity = (List<BatimentEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " order by date_modif desc").list();

		List<BatimentSimple> lst = cm.convertList(
				listeBatimentEntity, BatimentSimple.class);

		return lst;
	}

	/**
	 * Recherche de batiments multi critères
	 * 
	 * @param designation
	 *            Désignation
	 * @param numPolice
	 *            Numéro de police
	 * @param numCompteur
	 *            Numéro de compteur
	 * @param adresse
	 *            Adresse
	 * @param idInfrastructure
	 *            Identifant de l'infrastructure
	 * @param historique
	 *            Critère historique
	 * @return Liste des Batiments
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public List<BatimentSimple> getAllByCriteres(String designation,
			String numPolice, String numCompteur, String adresse,
			Long idInfrastructure, boolean historique)
			throws TechnicalException, BusinessException {
		
		// Construction de la requête
		String designationParam = "%" + designation.toUpperCase() + "%";
		String critere = " where upper(designation) like :designationParam";

		String numPoliceParam = "%" + numPolice.toUpperCase() + "%";
		if (!numPolice.isEmpty()) {
			critere = critere + " and exists (select id from "
					+ PoliceEntity.class.getName()
					+ " where structure_id = Batiment.id "
					+ " and upper(num_police) like :numPoliceParam)";
		}
		
		String numCompteurParam = "%" + numCompteur.toUpperCase() + "%";
		if (!numCompteur.isEmpty()) {
			critere = critere + " and exists (select Police.id from "
					+ PoliceEntity.class.getName() + " Police ," + CompteurEntity.class.getName() + " Compteur"
					+ " where structure_id = Batiment.id "
					+ "   and police_id = Police.id"
					+ "   and upper(num_compteur) like :numCompteurParam)";
		}
		
		String adresseParam = "%" + adresse.toUpperCase() + "%";
		if (!adresse.isEmpty()) {
			critere = critere + " and exists (select Adresse.id from "
					+ AdresseEntity.class.getName() + " Adresse"
					+ " where id = Batiment.adresse.id "
					+ "   and upper(adresse_ligne1) like :adresseParam)";
		}

		if (idInfrastructure != null)
			critere = critere + " and infrastructure_id = :idInfrastructure";
		
		if (!historique)
			critere = critere + " and date_fin is null";
		
		// Création de la requête
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName() + " Batiment " + critere
								+ " order by date_modif desc");

		// Ajout des paramères
		query.setParameter("designationParam", designationParam);
		if (!numPolice.isEmpty())
			query.setParameter("numPoliceParam", numPoliceParam);
		if (!numCompteur.isEmpty()) 
			query.setParameter("numCompteurParam", numCompteurParam);
		if (!adresse.isEmpty()) 
			query.setParameter("adresseParam", adresseParam);
		if (idInfrastructure != null)
			query.setParameter("idInfrastructure", idInfrastructure);

		@SuppressWarnings("unchecked")
		List<PoliceEntity> listeBatimentEntity = (List<PoliceEntity>) query.list();

		return cm.convertList(listeBatimentEntity, BatimentSimple.class);
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<BatimentEntity> getEntityClass() {
		return BatimentEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Batiment> getDtoClass() {
		return Batiment.class;
	}


}
