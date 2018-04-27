package nc.noumea.mairie.bilan.energie.business.dao;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import nc.noumea.mairie.bilan.energie.business.entities.AdressesConsolideesEntity;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.contract.service.IntegrationAdresseService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion d'intégration des adresses
 * 
 * @author Greg Dujardin
 * 
 */
@Service("integrationAdresseService")
@Transactional
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IntegrationAdresseBusiness implements IntegrationAdresseService {

	/** Principal */
	@Autowired
	private Principal principal;

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/** SessionFactory */
	@Autowired
	protected SessionFactory sessionFactory;

	/** Service des adresses */
	@Autowired
	private AdresseService adresseService;
	
	/** Service des adresses consolidées */
	@Autowired
	private AdressesConsolideesBusiness adresseConsolideesService;
	
	Logger logger = LoggerFactory.getLogger("synchroAdresse");

	/**
	 * Récupération des adresses consolidées depuis la base de référentiel des adresses
	 * 
	 */
	@Override
	@Transactional
	public void recuperationAdressesConsolidees () {
		
		Context initialContext;
		try {
			initialContext = new InitialContext();

			if (initialContext != null) {

				DataSource ds = (DataSource) initialContext
						.lookup("java:/comp/env/jdbc/adresse");

				if (ds != null) {
					Connection dbConnection = ds.getConnection();

					if (dbConnection != null) {
						// Récupération des adresses consolidées
						String sql = "select * from ADRESSES_CONSOLIDEES ";

						PreparedStatement stmt = dbConnection
								.prepareStatement(sql);
						ResultSet resultat = stmt.executeQuery();

						sessionFactory.getCurrentSession().createSQLQuery("delete from ADRESSES_CONSOLIDEES").executeUpdate();
						
						while (resultat.next()) {
							
							// Création des adresses consolidées sur la base Bilan Energie
							AdressesConsolideesEntity adressesConsolideesEntity = new AdressesConsolideesEntity();
							adressesConsolideesEntity.setObjectId(resultat.getLong("objectid"));
							adressesConsolideesEntity.setIdAdresse(resultat.getLong("id_adresse"));
							adressesConsolideesEntity.setNoVoie(resultat.getLong("no_voie"));
							adressesConsolideesEntity.setComplementNoVoie(resultat.getString("complement_no_voie"));
							adressesConsolideesEntity.setVoie(resultat.getString("voie"));
							adressesConsolideesEntity.setQuartier(resultat.getString("quartier"));
							adressesConsolideesEntity.setCommune(resultat.getString("commune"));
							adressesConsolideesEntity.setLibelleComplementAdresse(resultat.getString("libelle_complement_adresse"));
							adressesConsolideesEntity.setNic(resultat.getString("nic"));
							adressesConsolideesEntity.setLotissement(resultat.getString("lotissement"));
							adressesConsolideesEntity.setSurfCadHa(resultat.getLong("surf_cad_ha"));
							adressesConsolideesEntity.setSurfCadA(resultat.getLong("surf_cad_a"));
							adressesConsolideesEntity.setSurfCadCa(resultat.getLong("surf_cad_ca"));
							adressesConsolideesEntity.setCodePostal(resultat.getLong("code_postal"));
							adressesConsolideesEntity.setShape(resultat.getString("shape"));
							adressesConsolideesEntity.setSurfEquivM2(resultat.getString("surf_equiv_m2"));
							
							sessionFactory.getCurrentSession().persist(adressesConsolideesEntity);
						}

						dbConnection.close();
					}
				}
			}
		} catch (Exception e) {
		}

	}

}
