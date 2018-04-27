package nc.noumea.mairie.bilan.energie.business.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Date;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import nc.noumea.mairie.bilan.energie.business.entities.AbstractEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.DtoModel;
import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;
import nc.noumea.mairie.bilan.energie.contract.exceptions.IntrospectionException;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.exceptions.ValidatorException;
import nc.noumea.mairie.bilan.energie.contract.service.CrudService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.PersistentObjectException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class abstraite d'implémentation du service CRUD
 * 
 * @author Greg Dujardin
 * 
 * @param <DTO> Type de DTO
 * @param <ENT> Type d'entity
 */
@Transactional
public abstract class AbstractCrudBusiness<DTO extends DtoModel, ENT extends AbstractEntity>
		implements CrudService<DTO> {

	/** Principal */
	@Autowired
	private Principal principal;
	
	/** Principal */
	@Autowired
	private SecurityService securityService;

	/** SessionFactory */
	@Autowired
	protected SessionFactory sessionFactory;

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/**
	 * Injection du validateur
	 */
	@Autowired
	private Validator validator;

	/**
	 * Création d'un enregistrement
	 * 
	 * @param dto
	 *            dto à créer
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public DTO create(DTO dto) throws ValidatorException, TechnicalException,
			BusinessException {

		validate(dto);

		ENT entity = cm.convert(dto, getEntityClass());

		// Mise à jour des infos de création et de modification
		entity.setAuteurCreation(getCurrentUserName());
		entity.setDateCreation(new Date());
		entity.setAuteurModif(getCurrentUserName());
		entity.setDateModif(new Date());

		sessionFactory.getCurrentSession().persist(entity);

		Long id = null;
		try {
			id = (Long) getMethodGetId().invoke(entity);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new IntrospectionException("Erreur de la recherche de l'Id",
					e.getCause());
		}

		@SuppressWarnings("unchecked")
		ENT entityMaj = (ENT) sessionFactory.getCurrentSession().get(
				getEntityClass(), id);

		return cm.convert(entityMaj, getDtoClass());
	}

	/**
	 * Lecture d'un enregistrement
	 * 
	 * @param id Identifiant à chercher
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public DTO read(Serializable id) throws TechnicalException,
			BusinessException {

		@SuppressWarnings("unchecked")
		ENT entityMaj = (ENT) sessionFactory.getCurrentSession().get(
				getEntityClass(), id);

		return cm.convert(entityMaj, getDtoClass());
	}

	/**
	 * Mise à jour d'un enregistrement
	 * 
	 * @param dto Dto à mettre à jour
	 * @return DTO
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public DTO update(DTO dto) throws ValidatorException, TechnicalException,
			BusinessException {

		validate(dto);

		ENT entity = cm.convert(dto, getEntityClass());

		// Mise à jour des infos de modification
		entity.setAuteurModif(getCurrentUserName());
		entity.setDateModif(new Date());

		try {
			sessionFactory.getCurrentSession().persist(entity);
		} catch (PersistentObjectException | NonUniqueObjectException e) {
			sessionFactory.getCurrentSession().merge(entity);
		}
		
		sessionFactory.getCurrentSession().flush();

		Long id = null;
		try {
			id = (Long) getMethodGetId().invoke(entity);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new IntrospectionException("Erreur de la recherche de l'Id",
					e.getCause());
		}

		@SuppressWarnings("unchecked")
		ENT entityMaj = (ENT) sessionFactory.getCurrentSession().get(
				getEntityClass(), id);

		return cm.convert(entityMaj, getDtoClass());
	}

	/**
	 * Suppression d'un enregistrement
	 * 
	 * @param dto Dto à supprimer
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	@Transactional (rollbackFor= SuppressionImpossibleException.class)
	public void delete(DTO dto) throws TechnicalException, BusinessException {

		Long id = null;
		try {
			id = (Long) getMethodGetDtoId().invoke(dto);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new IntrospectionException("Erreur de la recherche de l'Id de la DTO",
					e.getCause());
		}
		
		@SuppressWarnings("unchecked")
		ENT entity = (ENT) sessionFactory.getCurrentSession().get(
				getEntityClass(), id);

		sessionFactory.getCurrentSession().delete(entity);
		
		 try  {
			 sessionFactory.getCurrentSession().flush();
		 } catch (ConstraintViolationException e) {
			 throw new SuppressionImpossibleException("Violation de contrainte",e);
		} catch (Exception e) {
			System.out.println("Class de l'exception : " + e.getClass());
			System.out.println("Class de l'exception : " + e.getCause());
			
		}
	}

	/** Récupération de la class de l'entité
	 * 
	 * @return Class de l'entité
	 */
	protected abstract Class<ENT> getEntityClass();

	/** Récupération de la classe du DTO
	 * 
	 * @return Class du DTO
	 */
	protected abstract Class<DTO> getDtoClass();

	/**
	 * Récupération de le méthode getId() de l'entity
	 * 
	 * @return Methode getId()
	 * @throws NoSuchMethodException Méthode non trouvé
	 * @throws SecurityException Exception de sécurité
	 */
	protected Method getMethodGetId() throws NoSuchMethodException,
			SecurityException {
		Class<?> clazz = getEntityClass();
		Field fId = null;
		boucleSearchId: while (clazz != null) {

			for (Field f : clazz.getDeclaredFields()) {

				if (f.isAnnotationPresent(Id.class)) {
					fId = f;
					break boucleSearchId;
				}

				if (f.isAnnotationPresent(EmbeddedId.class)) {
					fId = f;
					break boucleSearchId;
				}

			}

			clazz = clazz.getSuperclass();

		}

		String idName = fId.getName();
		String getter = "get" + idName.substring(0, 1).toUpperCase()
				+ idName.substring(1);
		Method mId = getEntityClass().getMethod(getter);
		return mId;

	}

	/**
	 * Validation d'une DTO
	 * 
	 * @param dto
	 *            DTO à Valider
	 * @throws ValidatorException
	 *             Exception levée si la DTO n'est pas valide
	 */
	public void validate(DTO dto) throws BusinessException, TechnicalException {

		Set<ConstraintViolation<DtoModel>> constrainte = validator
				.validate(dto);

		if (constrainte.size() > 0)
			throw new ValidatorException(constrainte);
	}

	/**
	 * Récupération de le méthode getId() du dto
	 * 
	 * @return Methode getId()
	 * @throws NoSuchMethodException Exception non trouvé
	 * @throws SecurityException Exception de sécurité
	 */
	protected Method getMethodGetDtoId() throws NoSuchMethodException,
			SecurityException {
		Class<?> clazz = getDtoClass();
		Field fId = null;
		boucleSearchId: while (clazz != null) {
	
			for (Field f : clazz.getDeclaredFields()) {
	
				if (f.isAnnotationPresent(IdDto.class)) {
					fId = f;
					break boucleSearchId;
				}
	
			}
	
			clazz = clazz.getSuperclass();
	
		}
	
		String idName = fId.getName();
		String getter = "get" + idName.substring(0, 1).toUpperCase()
				+ idName.substring(1);
		Method mId = getDtoClass().getMethod(getter);
		return mId;
	
	}
	
	/**
	 * Récupération du username de l'utilisateur courant
	 * 
	 * @return Nom de l'utilisateur
     * @throws TechnicalException Exception technique
	 */
	private String getCurrentUserName() throws TechnicalException {
		try {
			return securityService.getCurrentUserName();
		} catch (BeanCreationException e) {
			// Si erreur lors de la récupération par le securityService, on récupère le principal --> Cas des jobs Quartz
			return principal.getName();
		}
	}

}
