package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.AdresseEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.EclairagePublicEntity;
import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.SupportEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublicSimple;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des éclairages publics
 * 
 * @author Greg Dujardin
 * 
 */
@Service("eclairagePublicService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EclairagePublicBusiness extends
		AbstractCrudBusiness<EclairagePublic, EclairagePublicEntity> implements
		EclairagePublicService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/**
	 * Récupération de tous les éclairages publics
	 * 
	 * @return Liste des EPs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<EclairagePublicSimple> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<EclairagePublicEntity> listeEclairagePublicEntity = (List<EclairagePublicEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " order by date_modif desc").list();

		List<EclairagePublicSimple> lst = cm.convertList(
				listeEclairagePublicEntity, EclairagePublicSimple.class);

		return lst;
	}

	/**
	 * Recherche d'éclairage public multi critères
	 * 
	 * @param numPoste Numéro de poste
	 * @param nomPoste Nom de poste
	 * @param numPolice Numéro de police
	 * @param numCompteur Numéro de compteur
	 * @param adresseCompteur Adresse de compteur
	 * @param numSupport Numéro de support
	 * @param idInfrastructure Identifiant de l'infrastructure
	 * @param historique Critère Historique
	 * @return Liste des EPs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<EclairagePublicSimple> getAllByCriteres(String numPoste,
			String nomPoste, String numPolice, String numCompteur,
			String adresseCompteur, String numSupport, Long idInfrastructure, boolean historique)
			throws TechnicalException, BusinessException {
		
		// Construction de la requête
		String numPosteParam = "%" + numPoste.toUpperCase() + "%";
		String critere = " where upper(no_poste) like :numPosteParam";

		String nomPosteParam = "%" + nomPoste.toUpperCase() + "%";
		critere = critere + " and upper(nom_poste) like :nomPosteParam";

		String numPoliceParam = "%" + numPolice.toUpperCase() + "%";
		if (!numPolice.isEmpty()) {
			critere = critere + " and exists (select id from "
					+ PoliceEntity.class.getName()
					+ " where structure_id = EP.id "
					+ " and upper(num_police) like :numPoliceParam)";
		}
		
		String numCompteurParam = "%" + numCompteur.toUpperCase() + "%";
		if (!numCompteur.isEmpty()) {
			critere = critere + " and exists (select Police.id from "
					+ PoliceEntity.class.getName() + " Police ," + CompteurEntity.class.getName() + " Compteur"
					+ " where structure_id = EP.id "
					+ "   and police_id = Police.id"
					+ "   and upper(num_compteur) like :numCompteurParam)";
		}
		
		String adresseCompteurParam = "%" + adresseCompteur.toUpperCase() + "%";
		if (!adresseCompteur.isEmpty()) {
			critere = critere + " and exists (select Police.id from "
					+ PoliceEntity.class.getName() + " Police ," 
					+ CompteurEntity.class.getName() + " Compteur,"
					+ AdresseEntity.class.getName() + " Adresse"
					+ " where structure_id = EP.id "
					+ "   and police_id = Police.id"
					+ "   and Compteur.adresse = Adresse.id"
					+ "   and upper(adresse_ligne1) like :adresseCompteurParam)";
		}

		String numSupportParam = "%" + numSupport.toUpperCase() + "%";
		if (!numSupport.isEmpty()) {
			critere = critere + " and exists (select id from "
					+ SupportEntity.class.getName()
					+ " where eclairage_public_id = EP.id "
					+ " and upper(num_inventaire) like :numSupportParam)";
		}

		if (idInfrastructure != null)
			critere = critere + " and infrastructure_id = :idInfrastructure";
		
		if (!historique)
			critere = critere + " and date_fin is null";
		
		// Création de la requête
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName() + " EP " + critere
								+ " order by date_modif desc");

		// Ajout des paramères
		query.setParameter("numPosteParam", numPosteParam);
		query.setParameter("nomPosteParam", nomPosteParam);
		if (!numPolice.isEmpty())
			query.setParameter("numPoliceParam", numPoliceParam);
		if (!numCompteur.isEmpty()) 
			query.setParameter("numCompteurParam", numCompteurParam);
		if (!adresseCompteur.isEmpty()) 
			query.setParameter("adresseCompteurParam", adresseCompteurParam);
		if (!numSupport.isEmpty()) 
			query.setParameter("numSupportParam", numSupportParam);
		if (idInfrastructure != null)
			query.setParameter("idInfrastructure", idInfrastructure);

		@SuppressWarnings("unchecked")
		List<PoliceEntity> listeEclairagePublicEntity = (List<PoliceEntity>) query.list();

		return cm.convertList(listeEclairagePublicEntity, EclairagePublicSimple.class);
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<EclairagePublicEntity> getEntityClass() {
		return EclairagePublicEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<EclairagePublic> getDtoClass() {
		return EclairagePublic.class;
	}

	/**
	 * Recherche des modèles de support
	 * 
	 * @param modele Modèle à chercher
	 * @return Liste des modèles
	 */
	@Override
	public List<String> getAllModele(String modele) throws TechnicalException,
			BusinessException {
		String modeleParam = modele.toUpperCase() + "%"; 
		
		@SuppressWarnings("unchecked")
		List<String> listeModele = (List<String>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct Support.modele from " + SupportEntity.class.getName()
								+ " Support where upper(modele_support) like :modeleParam")
				.setParameter("modeleParam", modeleParam).list();


		return listeModele;
	}

}
