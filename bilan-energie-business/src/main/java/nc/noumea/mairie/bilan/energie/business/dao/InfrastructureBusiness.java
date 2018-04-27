package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.InfrastructureEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Infrastructure;
import nc.noumea.mairie.bilan.energie.contract.dto.InfrastructureSimple;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeStructure;
import nc.noumea.mairie.bilan.energie.contract.service.InfrastructureService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des infrastructures
 * 
 * @author Greg Dujardin
 * 
 */
@Service("infrastructureService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InfrastructureBusiness extends
		AbstractCrudBusiness<Infrastructure, InfrastructureEntity> implements
		InfrastructureService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/**
	 * Récupération de toutes les infrastructures
	 * 
	 * @return Liste des infrastructures
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public List<InfrastructureSimple> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<InfrastructureEntity> listeInfrastructureEntity = (List<InfrastructureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ " order by date_modif desc").list();

		List<InfrastructureSimple> lst = cm.convertList(
				listeInfrastructureEntity, InfrastructureSimple.class);

		return lst;
	}

	/**
	 * Récupération de toutes les infrastructures sous forme de code Label
	 * 
	 * 
	 * @param chercherEP
	 *            Critère 'chercher les EPs'
	 * @param chercherBatiment
	 *            'Critère 'chercher les Batiment'
	 * @return Liste des infrastructures
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentielByCriteres(boolean chercherEP,
			boolean chercherBatiment) throws TechnicalException,
			BusinessException {

		String critere = " where date_fin is null";

		if (!chercherEP)
			critere = critere + " and type = '"
					+ TypeStructure.BATIMENT.toString() + "'";

		if (!chercherBatiment)
			critere = critere + " and type = '" + TypeStructure.EP.toString()
					+ "'";

		@SuppressWarnings("unchecked")
		List<InfrastructureEntity> listeInfrastructureEntity = (List<InfrastructureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName()
								+ critere + " order by designation").list();

		List<CodeLabel> lst = cm.convertList(listeInfrastructureEntity,
				CodeLabel.class);

		return lst;
	}

	/**
	 * Récupération des infrastructures par désignation
	 * 
	 * @param designation
	 *            Designation
	 * @param historique
	 *            Critère historique
	 * @param chercherEP
	 *            Critère 'Chercher les EPs'
	 * @param chercherBatiment
	 *            Critère 'Chercher les Batiment'
	 * @return Liste des infrastructures
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	@Override
	public List<InfrastructureSimple> getAllByDesignation(String designation,
			boolean historique, boolean chercherEP, boolean chercherBatiment)
			throws TechnicalException, BusinessException {

		String designationParam = "%" + designation.toUpperCase() + "%";

		String critere = " where upper(designation) like :designationParam";

		if (!historique)
			critere = critere + " and date_fin is null";

		if (!chercherEP)
			critere = critere + " and type = '"
					+ TypeStructure.BATIMENT.toString() + "'";

		if (!chercherBatiment)
			critere = critere + " and type = '" + TypeStructure.EP.toString()
					+ "'";

		@SuppressWarnings("unchecked")
		List<InfrastructureEntity> listeInfrastructureEntity = (List<InfrastructureEntity>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from " + getEntityClass().getName() + critere
								+ " order by date_modif desc")
				.setParameter("designationParam", designationParam).list();

		return cm.convertList(listeInfrastructureEntity,
				InfrastructureSimple.class);
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<InfrastructureEntity> getEntityClass() {
		return InfrastructureEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Infrastructure> getDtoClass() {
		return Infrastructure.class;
	}
}
