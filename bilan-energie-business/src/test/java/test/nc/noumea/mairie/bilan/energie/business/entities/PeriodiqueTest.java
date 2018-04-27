package test.nc.noumea.mairie.bilan.energie.business.entities;

import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Periodique;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.nc.noumea.mairie.bilan.energie.business.dao.BilanBusinessTestNG;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Test des méthodes de Periodique
 *
 * @author Josselin PEYRON
 */
public class PeriodiqueTest extends BilanBusinessTestNG {

    private static final String NUMERO_TEST = "test";
    private static final Date DATE_1 = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_2 = new GregorianCalendar(2016, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_3 = new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_4 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();



    @BeforeMethod
    protected void init() {

    }

    @AfterMethod
    protected void destroy() {
    }

    

    private Police createPolice(String numero, Date dateDebut, Date dateFin) {
        Police police = new Police();
        police.setNumPolice(numero);
        police.setDateDebut(dateDebut);
        police.setDateFin(dateFin);
        return police;
    }

    private Compteur createCompteur(String numero, Date dateDebut, Date dateFin) {
        Compteur compteur = new Compteur();
        compteur.setNumCompteur(numero);
        compteur.setDateDebut(dateDebut);
        compteur.setDateFin(dateFin);
        return compteur;
    }

    @Test
    public void testCreateWithNumeroExistant2()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Police police2 = createPolice(NUMERO_TEST, DATE_1, null);
        Assert.assertTrue(Periodique.periodeChevauche(police1,police2));
        Assert.assertTrue(Periodique.periodeChevauche(police2,police1));
    }


    @Test
    public void testCreateWithNumeroExistant4()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_3);
        Police police2 = createPolice(NUMERO_TEST, DATE_2, null);
        Assert.assertTrue(Periodique.periodeChevauche(police1,police2));
        Assert.assertTrue(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testCreateWithNumeroExistant5()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_2, DATE_3);
        Police police2 = createPolice(NUMERO_TEST, DATE_1, DATE_4);
        Assert.assertTrue(Periodique.periodeChevauche(police1,police2));
        Assert.assertTrue(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testCreateWithNumeroExistant6()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Police police2 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Assert.assertTrue(Periodique.periodeChevauche(police1,police2));
        Assert.assertTrue(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testCreateWithNumeroExistantWithoutOverlap()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Police police2 = createPolice(NUMERO_TEST, DATE_3, DATE_4);
        Assert.assertFalse(Periodique.periodeChevauche(police1,police2));
        Assert.assertFalse(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testCreateWithNumeroExistantWithoutOverlap2()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Police police2 = createPolice(NUMERO_TEST, DATE_3, null);
        Assert.assertFalse(Periodique.periodeChevauche(police1,police2));
        Assert.assertFalse(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testCreateWithNumeroExistantWithoutOverlap3()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Police police2 = createPolice(NUMERO_TEST, DATE_2, DATE_3);
        Assert.assertFalse(Periodique.periodeChevauche(police1,police2));
        Assert.assertFalse(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testCreateWithNumeroExistantWithoutOverlap4()  {
        Police police1 = createPolice(NUMERO_TEST, DATE_2, null);
        Police police2 = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Assert.assertFalse(Periodique.periodeChevauche(police1,police2));
        Assert.assertFalse(Periodique.periodeChevauche(police2,police1));
    }

    @Test
    public void testInDateRange()  {
        Police police = createPolice(NUMERO_TEST, DATE_1, DATE_4);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_1, DATE_4);
        Assert.assertTrue(Periodique.isInPeriode(compteur, police));
    }

    @Test
    public void testInDateRange2()  {
        Police police = createPolice(NUMERO_TEST, DATE_1, DATE_4);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_2, DATE_3);
        Assert.assertTrue(Periodique.isInPeriode(compteur, police));
    }

    @Test
    public void testInDateRange3()  {
        Police police = createPolice(NUMERO_TEST, DATE_1, null);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_2, null);
        Assert.assertTrue(Periodique.isInPeriode(compteur, police));
    }

    @Test
    public void testNotInDateRange()  {
        Police police = createPolice(NUMERO_TEST, DATE_2, DATE_3);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_1, DATE_4);
        Assert.assertFalse(Periodique.isInPeriode(compteur, police));
    }

    @Test
    public void testNotInDateRange1()  {
        Police police = createPolice(NUMERO_TEST, DATE_1, DATE_3);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_2, DATE_4);
        Assert.assertFalse(Periodique.isInPeriode(compteur, police));
    }

    @Test
    public void testNotInDateRange2()  {
        Police police = createPolice(NUMERO_TEST, DATE_2, DATE_4);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_1, DATE_3);
        Assert.assertFalse(Periodique.isInPeriode(compteur, police));
    }

    @Test
    public void testNotInDateRange3()  {
        Police police = createPolice(NUMERO_TEST, DATE_1, DATE_2);
        Compteur compteur = createCompteur(NUMERO_TEST, DATE_1, null);
        Assert.assertFalse(Periodique.isInPeriode(compteur, police));
    }
}
