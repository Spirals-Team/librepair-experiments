package nc.noumea.mairie.bilan.energie.business.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

import nc.noumea.mairie.bilan.energie.business.job.JobIntegrationFacture;
import nc.noumea.mairie.bilan.energie.contract.dto.*;
import nc.noumea.mairie.bilan.energie.contract.enumeration.EtatFichier;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnomalie;
import nc.noumea.mairie.bilan.energie.contract.exceptions.IntegrationException;
import nc.noumea.mairie.bilan.energie.contract.service.CompteurService;
import nc.noumea.mairie.bilan.energie.contract.service.FichierFactureService;
import nc.noumea.mairie.bilan.energie.contract.service.IntegrationFactureService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.core.converter.ConvertManager;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.core.utils.FileUtils;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * Service de gestion d'intégration des fichiers de facture
 * 
 * @author Greg Dujardin
 * 
 */
@Service("integrationFactureService")
@Transactional(noRollbackFor=IntegrationException.class)
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IntegrationFactureBusiness implements IntegrationFactureService {

	/** Principal */
	@Autowired
	private Principal principal;

	/** Moteur de conversion */
	@Autowired
	private ConvertManager cm;

	/** Service des fichiers de facture */
	@Autowired
	private FichierFactureService fichierFactureService;

	/** Service des polices */
	@Autowired
	private PoliceService policeService;

	/** Service des compteurs */
	@Autowired
	private CompteurService compteurService;

	/** Service des paramètres */
	@Autowired
	private ParametrageService parametrageService;

	Logger logger = LoggerFactory.getLogger("integrationFacture");

	/** Enumération Type de fichier */
	private enum TypeFichier {
		EAU, ELECTRICITE
	};

	/** Constante Extension .zip */
	private static String EXTENSION_ZIP = ".ZIP";

	/** Constante Extension .csv */
	private static String EXTENSION_CSV = ".CSV";

	/** Constante Préfixe fichier Factures */
	private static String PREFIXE_FICHIER_FACTURES = "FACTURES_";

	/** Constante Préfixe fichier Factures */
	private static String PREFIXE_FICHIER_FACTURES_EAU = "FACTURES_EAU";

	/** Constante Préfixe fichier Factures */
	private static String PREFIXE_FICHIER_FACTURES_ELECTRICITE = "FACTURES_ELECTRICITE";

	/** Constante Charset */
	private static String CHARSET_FICHIER_FACTURES = "Cp1252";

	/** Entête du fichier */
	private static String[] ENTETE_FICHIER = { "EXPLOITATION", "BORDEREAU",
			"ANNEE FACTURE", "MOIS FACTURE", "POLICE", "FACTURE", "TARIF",
			"PUISSANCE", "QUARTIER BRCHT", "RUE BRCHT", "N° DANS VOIE",
			"COMPLEMENT N° VOIE", "DESCRIPTION BRANCHEMENT", "COMPTEUR ACTIF",
			"NBRE DE FILS", "TYPE FACTURE PRECEDENTE",
			"ANNEE RELEVE PRECEDENTE", "MOIS  RELEVE PRECEDENTE",
			"JOUR  RELEVE PRECEDENTE", "ANCIEN INDEX", "TYPE FACTURE",
			"ANNEE RELEVE", "MOIS  RELEVE", "JOUR  RELEVE", "NOUVEL INDEX",
			"CONSO RELEVEE", "CONSO CPTR DIVISIONNAIRE", "MONTANT NET FACTURE" };

	/**
	 * Intégration d'un fichier de facture
	 * 
	 * @param fichierFacture
	 *            Ficher de facture à intégrer
	 * @return fichier de facture intégré
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	private FichierFacture integrerFichierUnitaire(FichierFacture fichierFacture)
			throws TechnicalException, BusinessException {

		// Mise à jour de l'entité fichier de facture
		fichierFacture.setDateIntegration(new Date());
		fichierFacture.setAuteurModif(principal.getName());
		fichierFacture.setDateModif(new Date());

		// Contrôle des données
		fichierFacture = controlerFichierFacture(fichierFacture);

		if (fichierFacture.getNbErreurs() > 0) {
			fichierFacture.setEtat(EtatFichier.ANOMALIE_INTEGRATION);
		} else {
			// Pas d'erreur dans le contrôle des données, on intègre
			fichierFacture = integrerComptage(fichierFacture);
			fichierFacture.setEtat(EtatFichier.INTEGRE);
		}

		// On enregistre les modifications
		fichierFacture = fichierFactureService
				.updateFichierFacture(fichierFacture);

		return fichierFacture;

	}

	/**
	 * Intégration des factures dans la table comptage
	 * 
	 * @param fichierFacture
	 *            Ficher de facture à intégrer
	 * @return fichier de facture intégré
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	public FichierFacture integrerComptage(FichierFacture fichierFacture)
			throws TechnicalException, BusinessException {

		// Pour chaque facture du fichier de facture, on effectue le contrôle
		for (Facture facture : fichierFacture.getListeFacture()) {
			integrerFacture(facture);
		}

		return fichierFacture;
	}

	public Comptage integrerFacture(Facture facture) throws TechnicalException, BusinessException {

		Comptage comptage = null;
		
		// Contrôle du numéro de police
		List<Police> listePolice = policeService
				.getAllByNumeroPoliceExact(facture.getNumPolice());

		if (listePolice.size() == 0) {

			logger.trace("Intégration des factures - Police ("
					+ facture.getNumPolice() + ") non trouvée");

			// Pas de police trouvé, on déclenche une Exception
			throw new IntegrationException("Police ("
					+ facture.getNumPolice() + ") non trouvée");

		} else {
			// On a trouvé des polices, on cherche un numéro de compteur
			// correspondant à la facture

			boolean trouve = false;
			for (Police police : listePolice) {
				for (Compteur compteur : police.getListeCompteur()) {

					if (trouve
							&& compteur.getNumCompteur().equals(
									facture.getNumCompteur().trim())) {
						// On a déjà trouvé un couple Police / Compteur, on
						// déclenche une Exception

						logger.trace("Intégration des factures - Couple Police / Compteur ("
										+ facture.getNumPolice() + " / "
										+ facture.getNumCompteur()
										+ ") en doublon");


						throw new IntegrationException(
								"Couple Police / Compteur ("
										+ facture.getNumPolice() + " / "
										+ facture.getNumCompteur()
										+ ") en doublon");

					}

					if (compteur.getNumCompteur().equals(
							facture.getNumCompteur().trim())) {
						trouve = true;

						boolean nouveau;
						int index = 0;

						for (Comptage comptageExistant : compteur
								.getListeComptage()) {
							if (comptageExistant.getAnnee().equals(
									facture.getAnneeFacturation())
									&& comptageExistant.getMois().equals(
											facture.getMoisFacturation())
									&& comptageExistant.getNumFacture().equals(
											facture.getNumFacture()))
								comptage = comptageExistant;
						}

						if (comptage == null) {
							nouveau = true;
							// Création d'un nouveau comptage
							comptage = new Comptage();
							comptage.setAuteurCreation(principal.getName());
							comptage.setDateCreation(new Date());
						} else {
							nouveau = false;
							index = compteur.getListeComptage().indexOf(
									comptage);
						}

						comptage.setAuteurModif(principal.getName());
						comptage.setDateModif(new Date());
						comptage.setIdCompteur(compteur.getId());
						comptage.setNumFacture(facture.getNumFacture());
						comptage.setAnnee(facture.getAnneeFacturation());
						comptage.setMois(facture.getMoisFacturation());
						comptage.setDate(facture.getDateReleve());
						comptage.setMontantNet(facture.getMontantNet());
						comptage.setType(facture.getTypeReleve());
						comptage.setValeur(facture.getValeurIndice());
						comptage.setConsommation(facture
								.getConsommationReleve());
						comptage.setTypePrecedent(facture
								.getTypePrecedent());
						comptage.setDatePrecedent(facture
								.getDatePrecedente());
						comptage.setValeurPrecedent(facture
								.getValeurIndicePrecedent());
						comptage.setConsommationCptDivisionnaire(facture
								.getConscpt());
						comptage.setBordereau(facture.getRgpmt());

						if (nouveau)
							compteur.getListeComptage().add(comptage);
						else
							compteur.getListeComptage()
									.set(index, comptage);

						policeService.update(police);
					}
				}
			}
			if (!trouve) {
				// On n'a pas trouvé de compteur correspondant, on déclenche
				// une Exception
				logger.trace("Intégration des factures - Compteur "
						+ facture.getNumCompteur() + " non trouvé");

				throw new IntegrationException("Compteur "
						+ facture.getNumCompteur() + " non trouvé");
			}
			return comptage;
		}
	}

	/**
	 * Contrôle des données d'un fichier de facture
	 * 
	 * @param fichierFacture
	 *            fichier à intégrer
	 * @return resultat du retour
	 * @throws BusinessException
	 *             Exception métier
	 * @throws TechnicalException
	 *             Exception technique
	 */
	private FichierFacture controlerFichierFacture(FichierFacture fichierFacture)
			throws TechnicalException, BusinessException {

		List<FichierAnomalie> listeFichierAnomalie = new ArrayList<FichierAnomalie>();

		// Pour chaque facture du fichier de facture, on effectue le contrôle
		for (Facture facture : fichierFacture.getListeFacture()) {

			String donnees = "Num de Police : " + facture.getNumPolice()
					+ ", Num de compteur : " + facture.getNumCompteur();

			// Contrôle du numéro de police
			List<Police> listePolice = policeService
					.getAllByNumeroPoliceExact(facture.getNumPolice());

			if (listePolice.size() == 0) {

				// Pas de police trouvé, on cherche si on a une correspondance
				// d'un numéro de compteur
				List<Compteur> listeCompteur = compteurService
						.getAllByNumeroCompteurExact(facture.getNumCompteur());

				if (listeCompteur.size() == 0) {
					// Aucun rapprochement possible, on génère une anomalie

					listeFichierAnomalie.add(createAnomalie(fichierFacture,
							TypeAnomalie.ERREUR_PAS_CORRESPONDANCE, donnees));
				} else {
					// Rapprochement possible sur le numéro de compteur mais pas
					// le numéro de police,
					// on génére une anomalie
					// Aucun rapprochement possible, on génère une anomalie

					listeFichierAnomalie.add(createAnomalie(fichierFacture,
							TypeAnomalie.ERREUR_NUM_POLICE, donnees));

				}

			} else {
				// On a trouvé des polices, on cherche un numéro de compteur
				// correspondant à la facture

				boolean trouve = false;
				for (Police police : listePolice) {
					for (Compteur compteur : police.getListeCompteur()) {

						if (trouve
								&& compteur.getNumCompteur().equals(
										facture.getNumCompteur().trim())) {
							// On a déjà trouvé un couple Police / Compteur, on
							// génère une anomalie car c'est un doublon

							listeFichierAnomalie
									.add(createAnomalie(fichierFacture,
											TypeAnomalie.ERREUR_COUPLE_DOUBLON,
											donnees));

						}

						if (compteur.getNumCompteur().equals(
								facture.getNumCompteur().trim()))
							trouve = true;
					}
				}
				if (!trouve) {
					// On a pas trouvé de compteur correspondant, on génère une
					// anomalie
					listeFichierAnomalie.add(createAnomalie(fichierFacture,
							TypeAnomalie.ERREUR_NUM_COMPTEUR, donnees));
				}
			}
		}

		// On met à jour l'entité FichierFacture
		fichierFacture.setListeAnomalie(listeFichierAnomalie);
		fichierFacture.setNbErreurs(listeFichierAnomalie.size());

		return fichierFacture;
	}

	/**
	 * Création et initialisation d'une entité FichierAnomalie
	 * 
	 * @param fichierFacture
	 *            Fichier de facture à associer à l'anomalie
	 * @param type
	 *            Type de l'anomalie
	 * @param donnees
	 *            Données de l'anomalie
	 * @return fichierAnomalie
	 */
	private FichierAnomalie createAnomalie(FichierFacture fichierFacture,
			TypeAnomalie type, String donnees) {

		FichierAnomalie fichierAnomalie = new FichierAnomalie();

		fichierAnomalie.setIdFichierFacture(fichierFacture.getId());
		fichierAnomalie.setType(type);
		fichierAnomalie.setDonnees(donnees);
		fichierAnomalie.setAuteurCreation(principal.getName());
		fichierAnomalie.setDateCreation(new Date());
		fichierAnomalie.setAuteurModif(principal.getName());
		fichierAnomalie.setDateModif(new Date());

		return fichierAnomalie;
	}

	/**
	 * Recherche et import des fichiers de facture ZIP
	 * 
	 * @throws BusinessException
	 *             Exception métier
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Override
	public void integrationFichierFacture() throws TechnicalException,
			BusinessException {

		logger.trace("Intégration des factures - Récupération des paramètres");

		// Récupération des chemins
		Parametrage repSource = parametrageService
				.getByParametre("CHEMIN_FICHIER_FACTURE_SOURCE");

		logger.trace("Intégration des factures - Récupération des fichiers zip");

		// Recherche des fichiers ZIP
		List<File> listeFichierFactureZip = FileUtils.listFichier(
				repSource.getValeur(), "factures_", EXTENSION_ZIP);

		for (File file : listeFichierFactureZip) {
            importerFichierZip(file);
		}
	}

    private void importerFichierZip(File fileZip) throws TechnicalException, BusinessException {
        logger.info("Intégration des factures - Traitement du fichier "
                + fileZip.getName());

        Parametrage repTravail = parametrageService
        		.getByParametre("CHEMIN_FICHIER_FACTURE_TRAVAIL");
        Parametrage repArchivage = parametrageService
        		.getByParametre("CHEMIN_FICHIER_FACTURE_ARCHIVE");

        String nomRepertoireTravail = repTravail.getValeur()
               + File.separator
                + fileZip.getName().substring(0,
                        fileZip.getName().lastIndexOf("."));
       // Création du répertoire de travail
        File repertoireTravail = new File(nomRepertoireTravail);
        if (!repertoireTravail.exists()) {
            repertoireTravail.mkdir();
        }

        // Dézippage du fichier ZIP
        FileUtils.unZipIt(fileZip, nomRepertoireTravail);

        // Recherche des fichiers CSV
        List<File> listeFichierFacture = FileUtils.listFichier(
                nomRepertoireTravail, PREFIXE_FICHIER_FACTURES,
                EXTENSION_CSV);

        // Contrôle du contenu du ZIP
        if (listeFichierFacture.size() == 0) {
            logger.error("Intégration des factures - Traitement du fichier "
                    + fileZip.getName()
                    + " Erreur : Le fichier ZIP ne contient pas de fichier CSV");
            throw new IntegrationException(
                    "Le fichier ZIP ne contient pas de fichier CSV");
        }
        if (listeFichierFacture.size() > 1) {
            logger.error("Intégration des factures - Traitement du fichier "
                    + fileZip.getName()
                    + " Erreur : Le fichier ZIP contient plusieurs fichiers CSV");
            throw new IntegrationException(
                    "Le fichier ZIP contient plusieurs fichiers CSV");
        }

        // Traitement du fichier CSV
        for (File fileCsv : listeFichierFacture) {

            logger.trace("Intégration des factures - Traitement du fichier "
                    + fileZip.getName() + " Import du fichier " + fileCsv.getName());
            FichierFacture fichierFacture = importerFichierCsv(fileZip,
                    fileCsv);

            // Mise à jour du fichier Facture en BDD
            try {
                fichierFacture = fichierFactureService
                        .createFichierFacture(fichierFacture);
            } catch (BusinessException e) {
                logger.error("Intégration des factures - Erreur lors de la création d'un fichier facture en BDD");

                throw new IntegrationException(
                        "Erreur lors de la création d'un fichier facture en BDD", e);
            }

            if (fichierFacture.getEtat().equals(EtatFichier.IMPORTE)) {
                // Intégration des factures
                logger.trace("Intégration des factures - Traitement du fichier "
                        + fileZip.getName() + " Intégration des factures");
                fichierFacture = integrerFichierUnitaire(fichierFacture);
            }

        }

        // Suppression du répertoire de travail
        FileUtils.delete(repertoireTravail);

        // Déplacement du fichier ZIP
        if (!fileZip.renameTo(new File(repArchivage.getValeur()
                + File.separator + fileZip.getName()))) {
            logger.error("Intégration des factures - Traitement du fichier "
                    + fileZip.getName()
                    + " Erreur lors de l'archivage du fichier");
            throw new IntegrationException(
                    "Erreur lors de l'archivage du fichier");
        }
    }


	public void reIntegrerFichierFacture(FichierFactureSimple fichierFacture) throws TechnicalException, BusinessException, IOException {

		String zipFileName = fichierFacture.getNom();

		Parametrage repArchivageParametrage = parametrageService
				.getByParametre("CHEMIN_FICHIER_FACTURE_ARCHIVE");

		// Récupération des chemins
		Parametrage repSourceParametrage = parametrageService
				.getByParametre("CHEMIN_FICHIER_FACTURE_SOURCE");

		File zipFacture1 = new File(repArchivageParametrage.getValeur() + '/' + zipFileName);


		if (!zipFacture1.exists()){
			throw new IntegrationException("Le fichier '" + zipFileName + "' n'existe plus dans le répertoire Archive");
		}

		Path sourceDirectory = Paths.get(repSourceParametrage.getValeur());

		Files.move(zipFacture1.toPath(),  sourceDirectory.resolve(zipFacture1.getName()));

		runJobIntegrationFacture();

	}

	private boolean runJobIntegrationFacture() throws IntegrationException {
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			Collection<Scheduler> schedulers = schedulerFactory.getAllSchedulers();
			for (Scheduler scheduler : schedulers) {
				JobKey jobKey = findJobKey(scheduler, "IntegrationFacture");
				if (jobKey != null){
					scheduler.triggerJob(jobKey);
					return true;
				}
			}
			throw new IntegrationException("Le job quartz IntegrationFacture n'a pas été trouvé");

		} catch (SchedulerException e) {
			throw new IntegrationException(e);
		}
	}

	private JobKey findJobKey(Scheduler scheduler, String jobName) throws SchedulerException {
		// Check running jobs first
		for (JobExecutionContext runningJob : scheduler.getCurrentlyExecutingJobs()) {
			if (Objects.equals(jobName, runningJob.getJobDetail().getKey().getName())) {
				return runningJob.getJobDetail().getKey();
			}
		}
		// Check all jobs if not found
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				if (Objects.equals(jobName, jobKey.getName())) {
					return jobKey;
				}
			}
		}
		return null;
	}

    /**
	 * Import d'un fichier CSV
	 * 
	 * @param fileZip
	 *            Fichier à importer
	 * @param file
	 *            Fichier dézipper
	 * @return FichierFacture mis à jour
	 * @throws TechnicalException
	 *             Exception technique
	 * @throws BusinessException
	 *             Exception métier
	 */
	private FichierFacture importerFichierCsv(File fileZip, File file)
			throws TechnicalException, BusinessException {

		// Création d'une entité FichierFacture
		FichierFacture fichierFacture = new FichierFacture();
		fichierFacture.setDateImport(new Date());
		fichierFacture.setNom(fileZip.getName());
		fichierFacture.setNbErreurs(0);
		fichierFacture.setNbErreursTraitees(0);
		fichierFacture.setListeFacture(new ArrayList<Facture>());

		// Calcul du md5 et de la taille du fichier
		byte[] data;
		try {
			data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			
			logger.error("Intégration des factures - Erreur lors de la lecture du fichier");
			throw new IntegrationException(
					"Erreur lors de la lecture du fichier", e);
		}
		String md5 = DigestUtils.md5DigestAsHex(data);

		fichierFacture.setMd5(md5);
		fichierFacture.setTaille(data.length);

		// Contrôle du type de Fichier
		TypeFichier type;
		if (file.getName().toUpperCase()
				.startsWith(PREFIXE_FICHIER_FACTURES_EAU))
			type = TypeFichier.EAU;

		else if (file.getName().toUpperCase()
				.startsWith(PREFIXE_FICHIER_FACTURES_ELECTRICITE))
			type = TypeFichier.ELECTRICITE;

		else {
			logger.error("Intégration des factures - Le nom du fichier ZIP ne permet pas de déterminer le type de facture");
			fichierFacture.setDetailErreur("Le nom du fichier ZIP ne permet pas de déterminer le type de facture");
			fichierFacture.setEtat(EtatFichier.ANOMALIE_IMPORT);
			return fichierFacture;
		}
			
		// Lecture du fichier
		BufferedReader br = null;
		try {
			String ligneCourante;

			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), CHARSET_FICHIER_FACTURES));

			// Contrôle de la première ligne
			ligneCourante = br.readLine();
			try {
				contrôlerEntête(ligneCourante);
			} catch (IntegrationException e) {
				fichierFacture.setDetailErreur(e.getMessage());
				fichierFacture.setEtat(EtatFichier.ANOMALIE_IMPORT);
				return fichierFacture;
			}

			// Balayage du fichier
			ligneCourante = br.readLine();
			if (ligneCourante == null) {
				logger.error("Intégration des factures - Le fichier CSV ne contient pas de données");
				fichierFacture.setDetailErreur("Le fichier CSV ne contient pas de données");
				fichierFacture.setEtat(EtatFichier.ANOMALIE_IMPORT);
				return fichierFacture;
			}

			Integer nbLignes = 0;
			while (ligneCourante != null) {
				nbLignes++;
				// Intégration de la ligne sous forme de facture
				try {
					Facture facture = integrerLigneFacture(ligneCourante, nbLignes,
							type);
					fichierFacture.getListeFacture().add(facture);
				} catch (IntegrationException e) {
					fichierFacture.getListeFacture().removeAll(fichierFacture.getListeFacture());
					fichierFacture.setDetailErreur(e.getMessage());
					fichierFacture.setEtat(EtatFichier.ANOMALIE_IMPORT);
					return fichierFacture;
				}

				ligneCourante = br.readLine();
			}

			fichierFacture.setNbLignes(nbLignes);

		} catch (IOException e) {
			logger.error("Intégration des factures - Erreur lors de la lecture du fichier");

			throw new IntegrationException(
					"Erreur lors de la lecture du fichier", e);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				logger.error("Intégration des factures - Erreur lors de la fermeture du fichier");

				throw new IntegrationException(
						"Erreur lors de la fermeture du fichier", ex);
			}
		}

		// Fermeture du fichier
		try {
			if (br != null)
				br.close();
		} catch (IOException ex) {
			logger.error("Intégration des factures - Erreur lors de la fermeture du fichier");

			throw new IntegrationException(
					"Erreur lors de la fermeture du fichier", ex);
		}

		fichierFacture.setEtat(EtatFichier.IMPORTE);
		return fichierFacture;

	}

	/**
	 * Contrôle de l'entête du fichier
	 * 
	 * @param ligneCourante
	 *            Ligne à contrôler
	 * @throws BusinessException
	 *             Exception métier
	 */
	private void contrôlerEntête(String ligneCourante) throws BusinessException {

		if (ligneCourante == null)
			throw new IntegrationException("Le fichier CSV est vide");

		// Séparation des données en fonction des virgules sauf celles comprises
		// entre double-quote
		String[] listeDonnees = ligneCourante
				.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		if (listeDonnees.length != 28) {
			logger.error("Intégration des factures - Le format du fichier CSV n'est pas correct");
			throw new IntegrationException(
					"Le format du fichier CSV n'est pas correct");
		}
			
		// Formatage des données
		for (int i = 0; i < listeDonnees.length; i++) {
			String donnees = listeDonnees[i];

			// Suppression des quotes de début et fin
			if (donnees.startsWith("\""))
				donnees = donnees.substring(1, donnees.length() - 1);

			listeDonnees[i] = donnees.trim();

			if (!listeDonnees[i].equals(ENTETE_FICHIER[i])) {
				logger.error("Intégration des factures - Le format du fichier CSV n'est pas correct");
				throw new IntegrationException(
						"Le format du fichier CSV n'est pas correct");
			}
		}

	}

	/**
	 * Intégration d'une ligne d'un fichier de facture
	 * 
	 * @param ligneCourante
	 *            Ligne à intégrer
	 * @param numLigne
	 *            Numéro de la ligne
	 * @param type
	 *            Type de fichier
	 * @return Facture créée
	 * @throws BusinessException
	 *             Exception métier
	 * @throws TechnicalException
	 *             Exception technique
	 */
	private Facture integrerLigneFacture(String ligneCourante,
			Integer numLigne, TypeFichier type) throws BusinessException,
			TechnicalException {

		// Séparation des données en fonction des virgules sauf celles comprises
		// entre double-quote
		String[] listeDonnees = ligneCourante
				.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		if (listeDonnees.length != 28) {
			logger.error("Intégration des factures - Le format de la ligne "
					+ (numLigne + 1L) + " n'est pas correct");

			throw new IntegrationException("Le format de la ligne "
					+ (numLigne + 1L) + " n'est pas correct");
		}
			
		// Formatage des données
		for (int i = 0; i < listeDonnees.length; i++) {
			String donnees = listeDonnees[i];

			// Suppression des quotes de début et fin
			if (donnees.startsWith("\""))
				donnees = donnees.substring(1, donnees.length() - 1);

			listeDonnees[i] = donnees.trim();
		}

		String numPolice = listeDonnees[4];
		String numCompteur = listeDonnees[13];
		Long numFacture = Long.valueOf(listeDonnees[5]);
		Long anneeFacturation = Long.valueOf(listeDonnees[2]);
		Long moisFacturation = Long.valueOf(listeDonnees[3]);

		// Recherche d'une facture déjà existante
		Facture facture = fichierFactureService.getFactureByCriteres(numPolice,
				numCompteur, numFacture, anneeFacturation, moisFacturation);

		if (facture == null)
			// Création de la facture
			if (type.equals(TypeFichier.EAU))
				facture = new FactureEau();
			else
				facture = new FactureElectricite();
		else
			facture.setIdFichierFacture(null);

		// Affectation des champs
		facture.setCode(Long.valueOf(listeDonnees[0]));
		facture.setRgpmt(listeDonnees[1]);
		facture.setAnneeFacturation(Long.valueOf(listeDonnees[2]));
		facture.setMoisFacturation(Long.valueOf(listeDonnees[3]));
		facture.setNumPolice(listeDonnees[4]);
		facture.setNumFacture(Long.valueOf(listeDonnees[5]));
		facture.setTarif(listeDonnees[6]);
		facture.setPuissance(new BigDecimal(listeDonnees[7]));
		facture.setQuartier(Long.valueOf(listeDonnees[8]));
		facture.setRue(Long.valueOf(listeDonnees[9]));
		facture.setNumVoie(Long.valueOf(listeDonnees[10]));
		facture.setCptnv(listeDonnees[11]);
		facture.setDescription(listeDonnees[12]);
		facture.setNumCompteur(listeDonnees[13]);
		facture.setNbFils(Long.valueOf(listeDonnees[14]));

		String typePrecedent = listeDonnees[15];
		if (typePrecedent.equals(" ") || typePrecedent.trim().length() == 0) {
			logger.warn("Intégration des factures - Forcage du type de facture précédente à null pour la facture n°"
					+ facture.getNumFacture());
			facture.setTypePrecedent(null);
		} else if (typePrecedent.equals("R") || typePrecedent.equals("E"))
			facture.setTypePrecedent(typePrecedent);
		else
			throw new IntegrationException("Le format du type de facture précédent de la ligne "
					+ (numLigne + 1L) + " n'est pas correct");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(listeDonnees[16]),
				Integer.valueOf(listeDonnees[17]) - 1,
				Integer.valueOf(listeDonnees[18]));

		facture.setDatePrecedente(calendar.getTime());
		facture.setValeurIndicePrecedent(Long.valueOf(listeDonnees[19]));

		String typeReleve = listeDonnees[20];
		if (typeReleve.equals(" ")  || typeReleve.trim().length() == 0) {
			logger.warn("Intégration des factures - Forcage du type de facture à null pour la facture n°"
					+ facture.getNumFacture());
			facture.setTypeReleve(null);
		} else if (typeReleve.equals("R") || typeReleve.equals("E"))
			facture.setTypeReleve(typeReleve);
		else
			throw new IntegrationException("Le format du type de facture de la ligne "
					+ (numLigne + 1L) + " n'est pas correct");

		calendar.set(Integer.valueOf(listeDonnees[21]),
				Integer.valueOf(listeDonnees[22]) - 1,
				Integer.valueOf(listeDonnees[23]));

		facture.setDateReleve(calendar.getTime());
		facture.setValeurIndice(Long.valueOf(listeDonnees[24]));
		facture.setConsommationReleve(Long.valueOf(listeDonnees[25]));
		facture.setConscpt(Long.valueOf(listeDonnees[26]));
		facture.setMontantNet(Long.valueOf(listeDonnees[27]));

		return facture;

	}
}
