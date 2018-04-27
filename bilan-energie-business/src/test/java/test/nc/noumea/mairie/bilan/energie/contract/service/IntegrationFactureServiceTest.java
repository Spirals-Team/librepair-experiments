package test.nc.noumea.mairie.bilan.energie.contract.service;

import nc.noumea.mairie.bilan.energie.business.entities.ComptageEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FichierFactureEntity;
import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.contract.dto.*;
import nc.noumea.mairie.bilan.energie.contract.service.*;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.nc.noumea.mairie.bilan.energie.business.dao.BilanBusinessTestNG;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.testng.Assert.*;

/**
 * Test des méthodes de CompteurService
 *
 * @author Josselin PEYRON
 */
public class IntegrationFactureServiceTest extends BilanBusinessTestNG {

    private static final String NUMERO_COMPTEUR = "test_compteur";
    private static final String NUMERO_POLICE = "test_police";
    private static final Long NUMERO_FACTURE_1 = 1l;
    private static final Long NUMERO_FACTURE_2 = 2l;
    private static final Date DATE_1 = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_2 = new GregorianCalendar(2015, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_3 = new GregorianCalendar(2016, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_4 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
    private static final Date DATE_5 = new GregorianCalendar(2021, Calendar.FEBRUARY, 11).getTime();

    @Autowired
    private IntegrationFactureService integrationFactureService;

    @Autowired
    private FichierFactureService fichierFactureService;

    @Autowired
    private PoliceService policeService;

    @Autowired
    private CompteurService compteurService;

    @Autowired
    private ParametrageService parametrageService;

    /**
     * SessionFactory
     */
    @Autowired
    protected SessionFactory sessionFactory;

    @BeforeMethod
    protected void init() {
        super.init();
        deleteAllEntity();
        createUtilisateur();

    }

    @AfterMethod
    protected void destroy() {
        deleteAllEntity();
        super.destroy();
    }

    private void deleteAllEntity() {
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

            List<FichierFactureSimple> fichierFactureSimpleList = fichierFactureService.getAll();
            for (FichierFactureSimple fichierFactureSimple : fichierFactureSimpleList) {
                getCurrentSession().delete(getCurrentSession().get(FichierFactureEntity.class, fichierFactureSimple.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCurrentSession().flush();
    }

    private FichierFacture createFichierFacture() throws TechnicalException, BusinessException {
        FichierFacture fichierFacture = new FichierFacture();
        fichierFacture.setDateImport(new Date());
        fichierFacture.setListeFacture(new ArrayList<Facture>());
        return fichierFactureService.createFichierFacture(fichierFacture);
    }

    private Facture createFacture(FichierFacture fichierFacture, Date dateFacture, Long numFacture) throws TechnicalException, BusinessException {
        Facture facture = new FactureEau();
        facture.setIdFichierFacture(fichierFacture.getId());
        facture.setCode(51l);
        facture.setRgpmt("rgpmt");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateFacture);
        facture.setAnneeFacturation(Integer.toUnsignedLong(calendar.get(Calendar.YEAR)));
        facture.setMoisFacturation(Integer.toUnsignedLong(calendar.get(Calendar.MONTH)));
        facture.setNumPolice(NUMERO_POLICE);
        facture.setNumFacture(numFacture);
        facture.setTarif("1");
        facture.setPuissance(new BigDecimal(1));
        facture.setQuartier(1l);
        facture.setRue(1l);
        facture.setNumVoie(1l);
        facture.setNumCompteur(NUMERO_COMPTEUR);
        facture.setNbFils(1l);
        facture.setValeurIndicePrecedent(1l);
        facture.setDateReleve(dateFacture);
        facture.setValeurIndice(1l);
        facture.setConsommationReleve(1l);
        facture.setMoisFacturation(1l);
        fichierFacture.getListeFacture().add(facture);
        fichierFactureService.updateFichierFacture(fichierFacture);
        return facture;
    }

    public Police createPolice(String numero, Date dateDebut, Date dateFin) throws TechnicalException, BusinessException {
        Police police = new Police();
        police.setNumPolice(numero);
        police.setDateDebut(dateDebut);
        police.setDateFin(dateFin);
        return policeService.create(police);
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

    private Parametrage createParametrage(String key, String value) throws TechnicalException, BusinessException {
        Parametrage dto = new Parametrage();
        dto.setParametre(key);
        dto.setValeur(value);

        return parametrageService.create(dto);
    }

    @Test
    public void testIntegrerFacture() throws TechnicalException, BusinessException {
        Police police = createPolice(NUMERO_POLICE, DATE_1, DATE_3);
        Compteur compteur = createCompteur(NUMERO_COMPTEUR, DATE_1, DATE_3, police);
        FichierFacture fichierFacture =  createFichierFacture();
        Facture facture = createFacture(fichierFacture, DATE_2, NUMERO_FACTURE_1);

        integrationFactureService.integrerFacture(facture);
        compteur = compteurService.read(compteur.getId());
        assertEquals(compteur.getListeComptage().size(),1);

        // On réintègre à nouveau la même facture -> la facture n'est pas intégrée
        fichierFacture = fichierFactureService.readFichierFacture(fichierFacture.getId());
        integrationFactureService.integrerFacture(facture);
        compteur = compteurService.read(compteur.getId());
        assertEquals(compteur.getListeComptage().size(),1);

        // On réintègre à nouveau une facture avec le même numero mais à une date différente -> la facture est intégrée
        fichierFacture = fichierFactureService.readFichierFacture(fichierFacture.getId());
        Facture facture2 = createFacture(fichierFacture, DATE_3, NUMERO_FACTURE_1);
        integrationFactureService.integrerFacture(facture2);
        compteur = compteurService.read(compteur.getId());
        assertEquals(compteur.getListeComptage().size(),2);

        // On réintègre à nouveau une facture avec un numero différent mais à la même date que précédemment -> la facture est intégrée
        fichierFacture = fichierFactureService.readFichierFacture(fichierFacture.getId());
        Facture facture3 = createFacture(fichierFacture, DATE_3, NUMERO_FACTURE_2);
        integrationFactureService.integrerFacture(facture3);
        compteur = compteurService.read(compteur.getId());
        assertEquals(compteur.getListeComptage().size(),3);
    }


    // Cas de la police inexistante (exception
    @Test(expectedExceptions = {BusinessException.class}, expectedExceptionsMessageRegExp = "Police \\("+NUMERO_POLICE+"\\) non trouvée")
    public void testIntegrerFacturePoliceInexistante() throws TechnicalException, BusinessException {
        FichierFacture fichierFacture =  createFichierFacture();
        Facture facture = createFacture(fichierFacture, DATE_2, NUMERO_FACTURE_1);
        integrationFactureService.integrerFacture(facture);
    }

    // Cas du compteur inexistante
    @Test(expectedExceptions = {BusinessException.class}, expectedExceptionsMessageRegExp = "Compteur "+NUMERO_COMPTEUR+" non trouvé")
    public void testIntegrerFactureCompteurInexistant() throws TechnicalException, BusinessException {
        Police police = createPolice(NUMERO_POLICE, DATE_1, DATE_3);
        FichierFacture fichierFacture =  createFichierFacture();
        Facture facture = createFacture(fichierFacture, DATE_2, NUMERO_FACTURE_1);
        integrationFactureService.integrerFacture(facture);
    }

    @Test
    public void testIntegrerFichierFacture() throws TechnicalException, BusinessException, IOException {

        Path sourceDirectory = Files.createTempDirectory("facture_source");
        Path travaiDirectory = Files.createTempDirectory("facture_travail");
        Path archiveDirectory = Files.createTempDirectory("facture_archive");

        createParametrage("CHEMIN_FICHIER_FACTURE_SOURCE", sourceDirectory.toAbsolutePath().toString());
        createParametrage("CHEMIN_FICHIER_FACTURE_TRAVAIL", travaiDirectory.toAbsolutePath().toString());
        createParametrage("CHEMIN_FICHIER_FACTURE_ARCHIVE", archiveDirectory.toAbsolutePath().toString());

        ClassLoader classLoader = getClass().getClassLoader();
        File zipFacture1 = new File(classLoader.getResource("factures/factures_electricite.zip").getFile());
        Files.copy(zipFacture1.toPath(),  sourceDirectory.resolve(zipFacture1.getName()));

        integrationFactureService.integrationFichierFacture();

        List<FichierFactureSimple> fichierFactureSimpleList = fichierFactureService.getAll();

        assertEquals(fichierFactureSimpleList.size(), 1);

        FichierFactureSimple fichierFactureSimple = fichierFactureSimpleList.get(0);
        assertEquals(fichierFactureSimple.getNbLignes(), new Integer(608));
        assertEquals(fichierFactureSimple.getListeAnomalie().size(), 608);

        assertFalse(Files.exists(sourceDirectory.resolve(zipFacture1.getName())));
        assertTrue(Files.exists(archiveDirectory.resolve(zipFacture1.getName())));
        Files.delete(archiveDirectory.resolve(zipFacture1.getName()));

        Files.delete(sourceDirectory);
        Files.delete(travaiDirectory);
        Files.delete(archiveDirectory);

    }



    @Test
    public void testReIntegrerFichierFacture() throws TechnicalException, BusinessException, IOException {

        Path sourceDirectory = Files.createTempDirectory("facture_source");
        Path travaiDirectory = Files.createTempDirectory("facture_travail");
        Path archiveDirectory = Files.createTempDirectory("facture_archive");

        createParametrage("CHEMIN_FICHIER_FACTURE_SOURCE", sourceDirectory.toAbsolutePath().toString());
        createParametrage("CHEMIN_FICHIER_FACTURE_TRAVAIL", travaiDirectory.toAbsolutePath().toString());
        createParametrage("CHEMIN_FICHIER_FACTURE_ARCHIVE", archiveDirectory.toAbsolutePath().toString());

        ClassLoader classLoader = getClass().getClassLoader();
        File zipFacture1 = new File(classLoader.getResource("factures/factures_electricite.zip").getFile());
        Files.copy(zipFacture1.toPath(),  sourceDirectory.resolve(zipFacture1.getName()));

        integrationFactureService.integrationFichierFacture();

        List<FichierFactureSimple> fichierFactureSimpleList = fichierFactureService.getAll();

        assertEquals(fichierFactureSimpleList.size(), 1);

        FichierFactureSimple fichierFactureSimple = fichierFactureSimpleList.get(0);

        assertTrue(Files.exists(archiveDirectory.resolve(zipFacture1.getName())));

        integrationFactureService.reIntegrerFichierFacture(fichierFactureSimple);

        List<FichierFactureSimple> fichierFactureSimpleList2 = fichierFactureService.getAll();

        assertEquals(fichierFactureSimpleList2.size(), 2);

        Files.delete(archiveDirectory.resolve(zipFacture1.getName()));

        Files.delete(sourceDirectory);
        Files.delete(travaiDirectory);
        Files.delete(archiveDirectory);

    }
}