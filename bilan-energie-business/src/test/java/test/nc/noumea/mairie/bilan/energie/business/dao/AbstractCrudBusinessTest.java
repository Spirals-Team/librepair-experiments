package test.nc.noumea.mairie.bilan.energie.business.dao;

import java.lang.reflect.Method;
import java.util.Date;

import nc.noumea.mairie.bilan.energie.business.dao.AbstractCrudBusiness;
import nc.noumea.mairie.bilan.energie.business.entities.ParametrageEntity;
import nc.noumea.mairie.bilan.energie.business.entities.UtilisateurEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Test de la class {@link AbstractCrudBusiness}
 * 
 * @author David ALEXIS
 * 
 */
public class AbstractCrudBusinessTest extends BilanBusinessTestNG {

	/**
	 * Utilisation des services Paramètre et EclairagePublic pour vérifier la class
	 * {@link AbstractCrudBusiness}
	 */
	@Autowired
	private ParametrageService parametrageService;
	
	/** SessionFactory */
	@Autowired
	protected SessionFactory sessionFactory;


	@BeforeMethod
	protected void init() {
		super.init();
	}

	@AfterMethod
	protected void destroy() {
		super.destroy();
	}

	/**
	 * Test
	 * {@link AbstractCrudBusiness#create(nc.noumea.mairie.bilan.energie.contract.dto.DtoModel)}
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Test
	public void testCreate() throws TechnicalException, BusinessException {
		
		createUtilisateur();

		Parametrage dto = new Parametrage();
		dto.setParametre("Hello");
		dto.setValeur("world");

		dto = parametrageService.create(dto);
		
		Assert.assertNotNull(dto.getId());

		ParametrageEntity entity = (ParametrageEntity) getCurrentSession().get(
				ParametrageEntity.class, dto.getId());

		Assert.assertNotNull(dto.getParametre());
		Assert.assertNotNull(dto.getValeur());
		Assert.assertEquals(entity.getParametre(), dto.getParametre());
		Assert.assertEquals(entity.getValeur(), dto.getValeur());
	}

	/**
	 * Test {@link AbstractCrudBusiness#read(java.io.Serializable)}
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Test
	public void testRead() throws TechnicalException, BusinessException {

		Transaction tx = getCurrentSession().beginTransaction();
		ParametrageEntity entity = new ParametrageEntity();
		entity.setParametre("lulu");
		entity.setValeur("toto");

		Long id = (Long) getCurrentSession().save(entity);
		tx.commit();

		Parametrage dto = parametrageService.read(id);

		Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getParametre(), dto.getParametre());
		Assert.assertEquals(entity.getValeur(), dto.getValeur());
		Assert.assertEquals(id, dto.getId());
	}

	/**
	 * Test {@link AbstractCrudBusiness#update(nc.noumea.mairie.bilan.energie.contract.dto.DtoModel)}
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Test(dependsOnMethods = "testRead")
	public void testUpdate() throws TechnicalException, BusinessException {

		Transaction tx = getCurrentSession().beginTransaction();
		ParametrageEntity entity = new ParametrageEntity();
		entity.setParametre("lulu");
		entity.setValeur("toto");

		Long id = (Long) getCurrentSession().save(entity);
		tx.commit();

		Parametrage dto = parametrageService.read(id);
		
		dto.setValeur("Autre Valeur");
		
		dto = parametrageService.update(dto);

		Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getParametre(), dto.getParametre());
		Assert.assertEquals("Autre Valeur", dto.getValeur());
		Assert.assertEquals(id, dto.getId());
	}
	


	/**
	 * Test {@link AbstractCrudBusiness#update(nc.noumea.mairie.bilan.energie.contract.dto.DtoModel)}
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Test(dependsOnMethods = "testRead")
	public void testDelete() throws TechnicalException, BusinessException {

		Transaction tx = getCurrentSession().beginTransaction();
		ParametrageEntity entity = new ParametrageEntity();
		entity.setParametre("lulu");
		entity.setValeur("toto");

		Long id = (Long) getCurrentSession().save(entity);
		tx.commit();

		Parametrage dto = parametrageService.read(id);
		parametrageService.delete(dto);
		dto = parametrageService.read(id);

		Assert.assertNull(dto);
	}
	
	/**
	 * Test la méthode privée "AbstractCrudBusiness.getMethodGetId"
	 * @throws Throwable
	 */
	@Test
	public void testPrivateGetMethodGetId() throws Throwable{
		
		Method mName = AbstractCrudBusiness.class.getDeclaredMethod("getMethodGetId");
		Method mIdCalc = (Method)callPrivateMethod(parametrageService, mName);
		Method mIdTest = ParametrageEntity.class.getDeclaredMethod("getId");
		
		Assert.assertEquals(mIdCalc, mIdTest);
	}
}
