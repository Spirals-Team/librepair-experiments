package test.nc.noumea.mairie.bilan.energie.business.dao;

import nc.noumea.mairie.bilan.energie.business.entities.UtilisateurEntity;
import nc.noumea.mairie.bilan.energie.test.BilanTestNG;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Class de base des test Business
 * 
 * @author David ALEXIS
 *
 */
public class BilanBusinessTestNG extends BilanTestNG {

	/**
	 * Fabrique de session
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Session effective pour le test unitaire
	 */
	private Session session;
	
	protected void initData() {

	}
	
	 /**
     * Ouverture de la session 
     */
    protected void init() {

        session = sessionFactory.openSession();
        ManagedSessionContext.bind(session);
    }
 
    /**
     * Fermeture de la session
     */
    protected void destroy() {
        session.close();
        ManagedSessionContext.unbind(sessionFactory);
    }
    
    /**
     * @return Retourne la session courante du test unitaire
     */
    protected Session getCurrentSession(){
    	return session;
    }

	protected void createUtilisateur() {
		// Initialisation de l'utilisateur de Test
		UtilisateurEntity utilisateurEntity = new UtilisateurEntity();
		utilisateurEntity.setLogin("BilanTest");
		utilisateurEntity.setNom("Nom");
		utilisateurEntity.setPrenom("Prénom");
		utilisateurEntity.setDateDebut(new Date());

		Transaction tx = getCurrentSession().beginTransaction();
		getCurrentSession().save(utilisateurEntity);
		tx.commit();
	}
}
