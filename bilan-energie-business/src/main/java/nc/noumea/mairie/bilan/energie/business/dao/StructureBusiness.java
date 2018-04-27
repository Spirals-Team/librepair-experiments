package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.StructureEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.StructureLabel;
import nc.noumea.mairie.bilan.energie.contract.service.StructureService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des structures
 * 
 * @author Greg Dujardin
 * 
 */
@Service("structureService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StructureBusiness implements StructureService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/** SessionFactory */
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * Récupération de toutes les structures
	 * 
	 * @return Liste des structures
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<StructureLabel> getAll() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		
		List<StructureEntity> listeStructureEntity = (List<StructureEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName() + " order by designation").list();

		List<StructureLabel> lst = cm.convertList(listeStructureEntity,
				getDtoClass());
		
		
		
		return lst;
	}
	
	/**
	 * Recherche des structures par désignation
	 * 
	 * @param designation Désignation de la structure à rechercher
	 * @return Liste des structures trouvées
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<StructureLabel> getAllStructureByDesignation(String designation)
			throws TechnicalException, BusinessException {

		String designationParam = "%" + designation.toUpperCase() + "%";

		// Recherche dans les structures
		@SuppressWarnings("unchecked")
		List<StructureEntity> listeStructureEntity = (List<StructureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " where upper(designation) like :designationParam")
				.setParameter("designationParam", designationParam).list();

		return cm.convertList(listeStructureEntity, getDtoClass());
	}
	
	/** Récupération de la class de l'entité
	 * 
	 * @return Class de la structureEntity
	 */
	public Class<StructureEntity> getEntityClass() {
		return StructureEntity.class;
	}

	/** Récupération de la class du DTO
	 * 
	 * @return Class de la structureLabel
	 */
	public Class<StructureLabel> getDtoClass() {
		return StructureLabel.class;
	}


}
