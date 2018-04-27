package test.nc.noumea.mairie.bilan.energie.contract.service;

import nc.noumea.mairie.bilan.energie.business.entities.ComptageEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.PoliceSimple;
import nc.noumea.mairie.bilan.energie.contract.exceptions.BusinessValidationException;
import nc.noumea.mairie.bilan.energie.contract.service.CompteurService;
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
 * Test des méthodes de CompteurService
 *
 * @author Josselin PEYRON
 */
public class CompteurServiceTest extends BilanBusinessTestNG {

    private static final String NUMERO_COMPTEUR = "test";
    private static final String NUMERO_POLICE = "test_police";
    private static final String NUMERO_POLICE_2 = "test_police2";


    private static final Date DATE_1 = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_2 = new GregorianCalendar(2016, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_3 = new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_4 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_5 = new GregorianCalendar(2021, Calendar.FEBRUARY, 11).getTime();

    @Autowired
    private CompteurService compteurService;

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
        deleteAllEntries();
        super.destroy();
    }

    private void deleteAllEntries() {
        try {
            List<Compteur> compteurList = compteurService.getAllByNumeroCompteurExact(NUMERO_COMPTEUR);
            for (Compteur compteur : compteurList) {
                for (Comptage comptage : compteur.getListeComptage()){
                    getCurrentSession().delete(getCurrentSession().get(ComptageEntity.class, comptage.getId()));
                }
                getCurrentSession().delete(getCurrentSession().get(CompteurEntity.class, compteur.getId()));
            }
            List<PoliceSimple> policeList = policeService.getAll();
            for (PoliceSimple policeSimple : policeList) {
                getCurrentSession().delete(getCurrentSession().get(PoliceEntity.class, policeSimple.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCurrentSession().flush();
    }

    private Compteur createCompteur(String numero, Date dateDebut, Date dateFin, Police police) throws
            TechnicalException, BusinessException {
        Compteur compteur = new Compteur();
        compteur.setNumCompteur(numero);
        compteur.setDateDebut(dateDebut);
        compteur.setDateFin(dateFin);
        compteur.setIdPolice(police.getId());
        return compteurService.create(compteur);
    }

    public Police createPolice(String numero, Date dateDebut, Date dateFin) throws
            TechnicalException, BusinessException {
        Police police = new Police();
        police.setNumPolice(numero);
        police.setDateDebut(dateDebut);
        police.setDateFin(dateFin);

        return policeService.create(police);
    }

    @Test
    public void testCreateWithNumeroExistantDansAutrePolice() throws TechnicalException, BusinessException {
        Compteur compteur1 = createCompteur(NUMERO_COMPTEUR, DATE_1, DATE_2, createPolice(NUMERO_POLICE, DATE_1, DATE_4));
        Compteur compteur2 = createCompteur(NUMERO_COMPTEUR, DATE_3, DATE_4, createPolice(NUMERO_POLICE_2, DATE_1, DATE_4));
        Assert.assertEquals(compteur1.getNumCompteur(), compteur2.getNumCompteur());
    }

    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = CompteurService.ERROR_NUMERO_DOUBLON_PERIODE + NUMERO_COMPTEUR +"' \\(police n°"+NUMERO_POLICE+"\\)")
    public void testCreateWithNumeroExistantDansAutrePoliceAvecChevauchement() throws TechnicalException, BusinessException {
        createCompteur(NUMERO_COMPTEUR, DATE_1, DATE_2, createPolice(NUMERO_POLICE, DATE_1, DATE_4));
        createCompteur(NUMERO_COMPTEUR, DATE_1, null, createPolice(NUMERO_POLICE_2, DATE_1, DATE_4));
    }

    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = CompteurService.ERROR_NUMERO_DOUBLON_POLICE + NUMERO_COMPTEUR +"' \\(police n°"+NUMERO_POLICE+"\\)")
    public void testCreateWithNumeroExistantDansMemePolice() throws TechnicalException, BusinessException {
        Police police = createPolice(NUMERO_POLICE, DATE_1, DATE_4);
        createCompteur(NUMERO_COMPTEUR, DATE_1, DATE_2, police);
        createCompteur(NUMERO_COMPTEUR, DATE_3, DATE_4, police);
    }
//TODO : à réactiver une fois  la méthode validateDatePolice réactivée
//    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = CompteurService.ERROR_HORS_PERIODE_POLICE)
//    public void testCreateWithPeriodeHorsPolicePeriode() throws TechnicalException, BusinessException {
//        Compteur compteur = createCompteur(NUMERO_COMPTEUR, DATE_2, DATE_5, createPolice(NUMERO_POLICE, DATE_1, DATE_4));
//    }

    //TODO : à réactiver une fois  la méthode validateDatePolice réactivée
//    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = CompteurService.ERROR_DATE_FIN_NON_RENSEIGNEE)
//    public void testCreateWithDateFinNull() throws TechnicalException, BusinessException {
//        Police police = createPolice(NUMERO_POLICE, DATE_1, DATE_4);
//        Assert.assertNotNull(police.getDateFin());
//        Compteur compteur = createCompteur(NUMERO_COMPTEUR, DATE_2, null, police);
//    }

    @Test(expectedExceptions = {BusinessValidationException.class}, expectedExceptionsMessageRegExp = CompteurService.ERROR_DATE_FIN_NOK )
    public void testCreateWithBadDates() throws TechnicalException, BusinessException {
        createCompteur(NUMERO_COMPTEUR, DATE_2, DATE_1, createPolice(NUMERO_POLICE, DATE_1, DATE_4));
    }

}
