package nc.noumea.mairie.bilan.energie.business.dao;

import java.util.List;

import nc.noumea.mairie.bilan.energie.business.entities.DirectionEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Direction;
import nc.noumea.mairie.bilan.energie.contract.service.DirectionService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des directions
 * 
 * @author Greg Dujardin
 * 
 */
@Service("directionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DirectionBusiness extends
		AbstractCrudBusiness<Direction, DirectionEntity> implements
		DirectionService {

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;
	
	/**
	 * Récupération de toutes les directions
	 * 
	 * @return Liste des directions
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<Direction> getAll() throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		List<DirectionEntity> listeDirectionEntity = (List<DirectionEntity>) sessionFactory
				.getCurrentSession() 
				.createQuery("from " + getEntityClass().getName() + " order by nom").list();

		List<Direction> lst = cm.convertList(listeDirectionEntity,
				getDtoClass());
		
		
		
		return lst;
	}

	/**
	 * Récupération de toutes les directions sous forme de code Label
	 * 
	 * @return Liste des directions
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public List<CodeLabel> getAllReferentiel() throws TechnicalException,
			BusinessException {
		@SuppressWarnings("unchecked")
		List<DirectionEntity> listeDirectionEntity = (List<DirectionEntity>) sessionFactory
				.getCurrentSession()
				.createQuery("from " + getEntityClass().getName() + " order by designation").list();

		List<CodeLabel> lst = cm.convertList(listeDirectionEntity,
				CodeLabel.class);
		
		
		
		return lst;
	}

	/** Récupération de la class de l'entité */
	@Override
	public Class<DirectionEntity> getEntityClass() {
		return DirectionEntity.class;
	}

	/** Récupération de la class du DTO */
	@Override
	public Class<Direction> getDtoClass() {
		return Direction.class;
	}


}
