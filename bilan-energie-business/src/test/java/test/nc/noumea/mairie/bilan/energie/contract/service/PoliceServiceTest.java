package test.nc.noumea.mairie.bilan.energie.contract.service;

import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.PoliceSimple;
import nc.noumea.mairie.bilan.energie.contract.exceptions.BusinessValidationException;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.nc.noumea.mairie.bilan.energie.business.dao.BilanBusinessTestNG;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Test des méthodes de PoliceBusiness
 *
 * @author Josselin PEYRON
 */
public class PoliceServiceTest extends BilanBusinessTestNG {

    private static final String NUMERO_TEST = "test";
    private static final Date DATE_1 = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_2 = new GregorianCalendar(2016, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_3 = new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_4 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();

    @Autowired
    private PoliceService policeService;

    /**
     * SessionFactory
     */
    @Autowired
    protected SessionFactory sessionFactory;


    @BeforeMethod
    protected void init() {
        super.init();
        createUtilisateur();
    }

    @AfterMethod
    protected void destroy() {
        deleteAllPolice();
        super.destroy();
    }

    private void deleteAllPolice() {
        try {
            List<PoliceSimple> policeList = policeService.getAll();
            for (PoliceSimple policeSimple : policeList) {
                getCurrentSession().delete(getCurrentSession().get(PoliceEntity.class, policeSimple.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCurrentSession().flush();
    }

    public Police createPolice(String numero, Date dateDebut, Date dateFin) throws TechnicalException, BusinessException {
        Police police = new Police();
        police.setNumPolice(numero);
        police.setDateDebut(dateDebut);
        police.setDateFin(dateFin);
        return policeService.create(police);
    }

    @Test
    public void testCreate() throws TechnicalException, BusinessException {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Assert.assertNotNull(police1.getId());
    }

    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = PoliceService.ERROR_NUMERO_DOUBLON + ".*")
    public void testCreateWithNumeroExistant2() throws TechnicalException, BusinessException {
        createPolice(NUMERO_TEST, DATE_1, DATE_2);
        createPolice(NUMERO_TEST, DATE_1, null);
    }

    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = PoliceService.ERROR_DATE_FIN_NOK )
    public void testCreateWithBadDates() throws TechnicalException, BusinessException {
        createPolice(NUMERO_TEST, DATE_2, DATE_1);
    }
}
