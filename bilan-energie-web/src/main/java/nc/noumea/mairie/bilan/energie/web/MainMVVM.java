package nc.noumea.mairie.bilan.energie.web;

import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.analyse.AnalyseGestionPageId;
import nc.noumea.mairie.bilan.energie.web.analyse.AnalysePageGestion;
import nc.noumea.mairie.bilan.energie.web.batiment.BatimentGestionPageId;
import nc.noumea.mairie.bilan.energie.web.batiment.BatimentPageGestion;
import nc.noumea.mairie.bilan.energie.web.categoriePolice.CategoriePoliceGestionPageId;
import nc.noumea.mairie.bilan.energie.web.categoriePolice.CategoriePolicePageGestion;
import nc.noumea.mairie.bilan.energie.web.client.ClientGestionPageId;
import nc.noumea.mairie.bilan.energie.web.client.ClientPageGestion;
import nc.noumea.mairie.bilan.energie.web.codeCtme.CodeCtmeGestionPageId;
import nc.noumea.mairie.bilan.energie.web.codeCtme.CodeCtmePageGestion;
import nc.noumea.mairie.bilan.energie.web.conversion.ConversionGestionPageId;
import nc.noumea.mairie.bilan.energie.web.conversion.ConversionPageGestion;
import nc.noumea.mairie.bilan.energie.web.direction.DirectionGestionPageId;
import nc.noumea.mairie.bilan.energie.web.direction.DirectionPageGestion;
import nc.noumea.mairie.bilan.energie.web.ep.EclairagePublicGestionPageId;
import nc.noumea.mairie.bilan.energie.web.ep.EclairagePublicPageGestion;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.facture.FactureGestionPageId;
import nc.noumea.mairie.bilan.energie.web.facture.FacturePageGestion;
import nc.noumea.mairie.bilan.energie.web.infrastructure.InfrastructureGestionPageId;
import nc.noumea.mairie.bilan.energie.web.infrastructure.InfrastructurePageGestion;
import nc.noumea.mairie.bilan.energie.web.parametrage.ParametrageGestionPageId;
import nc.noumea.mairie.bilan.energie.web.parametrage.ParametragePageGestion;
import nc.noumea.mairie.bilan.energie.web.police.PoliceGestionPageId;
import nc.noumea.mairie.bilan.energie.web.police.PolicePageGestion;
import nc.noumea.mairie.bilan.energie.web.typeCompteur.TypeCompteurGestionPageId;
import nc.noumea.mairie.bilan.energie.web.typeCompteur.TypeCompteurPageGestion;
import nc.noumea.mairie.bilan.energie.web.typeEmplacement.TypeEmplacementGestionPageId;
import nc.noumea.mairie.bilan.energie.web.typeEmplacement.TypeEmplacementPageGestion;
import nc.noumea.mairie.bilan.energie.web.typePolice.TypePoliceGestionPageId;
import nc.noumea.mairie.bilan.energie.web.typePolice.TypePolicePageGestion;
import nc.noumea.mairie.bilan.energie.web.typeSource.TypeSourceGestionPageId;
import nc.noumea.mairie.bilan.energie.web.typeSource.TypeSourcePageGestion;
import nc.noumea.mairie.bilan.energie.web.typeSupport.TypeSupportGestionPageId;
import nc.noumea.mairie.bilan.energie.web.typeSupport.TypeSupportPageGestion;
import nc.noumea.mairie.bilan.energie.web.typeZone.TypeZoneGestionPageId;
import nc.noumea.mairie.bilan.energie.web.typeZone.TypeZonePageGestion;
import nc.noumea.mairie.bilan.energie.web.utilisateur.UtilisateurGestionPageId;
import nc.noumea.mairie.bilan.energie.web.utilisateur.UtilisateurPageGestion;
import nc.noumea.mairie.bilan.energie.web.wm.PageExistException;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;
import nc.noumea.mairie.bilan.energie.web.wm.TabComposer;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

/**
 * MVVM principal
 *
 * @author Greg Dujardin
 *
 */
public class MainMVVM extends AbstractMVVM {

	// Page de gestion des EPs
	private EclairagePublicGestionPageId eclairagePublicGestionPageId;

	// Page de gestion des Batiments
	private BatimentGestionPageId batimentGestionPageId;

	// Page de gestion des Polices
	private PoliceGestionPageId policeGestionPageId;

	// Page de gestion des factures
	private FactureGestionPageId factureGestionPageId;

	// Page de gestion des utilisateurs
	private UtilisateurGestionPageId utilisateurGestionPageId;

	// Page de gestion des infrastructures
	private InfrastructureGestionPageId infrastructureGestionPageId;

	// Page de gestion des parametrages
	private ParametrageGestionPageId parametrageGestionPageId;

	// Page de gestion des directions
	private DirectionGestionPageId directionGestionPageId;

	// Page de gestion des taux de conversion
	private ConversionGestionPageId conversionGestionPageId;

	// Page de gestion des types de source
	private TypeSourceGestionPageId typeSourceGestionPageId;

	// Page de gestion des clients
	private ClientGestionPageId clientGestionPageId;

	// Page de gestion des types de catégories
	private CategoriePoliceGestionPageId categoriePoliceGestionPageId;

	// Page de gestion des types de police
	private TypePoliceGestionPageId typePoliceGestionPageId;

	// Page de gestion des types de support
	private TypeSupportGestionPageId typeSupportGestionPageId;

	// Page de gestion des types de compteur
	private TypeCompteurGestionPageId typeCompteurGestionPageId;

	// Page de gestion des types de support
	private TypeEmplacementGestionPageId typeEmplacementGestionPageId;

	// Page de gestion des types de zone
	private TypeZoneGestionPageId typeZoneGestionPageId;

	// Page de gestion des codes CTME
	private CodeCtmeGestionPageId codeCtmeGestionPageId;

	// Page de gestion des analyses
	private AnalyseGestionPageId analyseGestionPageId;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	/**
	 * Retourne le service de sécurité
	 * 
	 * @return Service de sécurité
	 */
	public SecurityService getSecurityService() {
		return securityService;
	}

	/**
	 * get LabelDeconnexion
	 *
	 * @return labelDéconnexion
	 * @throws TechnicalException Exception technique
	 */
	public String getLabelDeconnexion() throws TechnicalException {
		return "Déconnexion - " + securityService.getCurrentUserName();
	}

	/**
	 * Permet d'afficher la page courante comme dirty
	 * 
	 * @param dirty
	 *            Valeur à affecter
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@GlobalCommand
	public void dirty(boolean dirty) throws TechnicalException {
		TabComposer wm = getWindowManager();
		wm.setDirtyOnCurrentPage(dirty);
	}

	/**
	 * Initialisation du workspace
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@AfterCompose
	public void initSetup() throws TechnicalException {

		try {
			if (securityService.isUtilisateurEP())
				openGestionEP();

			if (securityService.isUtilisateurBatiment())
				openGestionBatiment();

			// Enregistrer l'utilisateur comme logué
			securityService.loginCurrentUser();

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Déconnexion
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void logout() throws TechnicalException {

		try {
			// Enregistrer l'utilisateur comme dé-logué
			securityService.logoutCurrentUser();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		finally{
			Sessions.getCurrent().invalidate();
			Executions.sendRedirect("/");
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des EPs
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionEP() throws TechnicalException {

		if (eclairagePublicGestionPageId == null)
			eclairagePublicGestionPageId = new EclairagePublicGestionPageId();
		EclairagePublicPageGestion pageInfo = new EclairagePublicPageGestion();
		pageInfo.setPageId(eclairagePublicGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * Ouverture de l'onglet de gestion des Batiments
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionBatiment() throws TechnicalException {

		if (batimentGestionPageId == null)
			batimentGestionPageId = new BatimentGestionPageId();
		BatimentPageGestion pageInfo = new BatimentPageGestion();
		pageInfo.setPageId(batimentGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * Ouverture de l'onglet de gestion des Polices
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionPolice() throws TechnicalException {

		if (policeGestionPageId == null)
			policeGestionPageId = new PoliceGestionPageId();
		PolicePageGestion pageInfo = new PolicePageGestion();
		pageInfo.setPageId(policeGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * Ouverture de l'onglet de gestion de l'intégration des factures
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionIntegrationFacture() throws TechnicalException {

		if (factureGestionPageId == null)
			factureGestionPageId = new FactureGestionPageId();
		FacturePageGestion pageInfo = new FacturePageGestion();
		pageInfo.setPageId(factureGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * Ouverture de l'onglet de gestion des utilisateurs
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionUtilisateur() throws TechnicalException {

		if (utilisateurGestionPageId == null)
			utilisateurGestionPageId = new UtilisateurGestionPageId();
		UtilisateurPageGestion pageInfo = new UtilisateurPageGestion();
		pageInfo.setPageId(utilisateurGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * Ouverture de l'onglet de gestion des infrastructures
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionInfrastructure() throws TechnicalException {

		if (infrastructureGestionPageId == null)
			infrastructureGestionPageId = new InfrastructureGestionPageId();
		InfrastructurePageGestion pageInfo = new InfrastructurePageGestion();
		pageInfo.setPageId(infrastructureGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des paramétrages
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionParametrage() throws TechnicalException {

		if (parametrageGestionPageId == null)
			parametrageGestionPageId = new ParametrageGestionPageId();
		ParametragePageGestion pageInfo = new ParametragePageGestion();
		pageInfo.setPageId(parametrageGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des directions
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionDirection() throws TechnicalException {

		if (directionGestionPageId == null)
			directionGestionPageId = new DirectionGestionPageId();
		DirectionPageGestion pageInfo = new DirectionPageGestion();
		pageInfo.setPageId(directionGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des taux de conversions
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionConversion() throws TechnicalException {

		if (conversionGestionPageId == null)
			conversionGestionPageId = new ConversionGestionPageId();
		ConversionPageGestion pageInfo = new ConversionPageGestion();
		pageInfo.setPageId(conversionGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des types de source
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionTypeSource() throws TechnicalException {

		if (typeSourceGestionPageId == null)
			typeSourceGestionPageId = new TypeSourceGestionPageId();
		TypeSourcePageGestion pageInfo = new TypeSourcePageGestion();
		pageInfo.setPageId(typeSourceGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des clients
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionClient() throws TechnicalException {

		if (clientGestionPageId == null)
			clientGestionPageId = new ClientGestionPageId();
		ClientPageGestion pageInfo = new ClientPageGestion();
		pageInfo.setPageId(clientGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des catégories de police
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionCategoriePolice() throws TechnicalException {

		if (categoriePoliceGestionPageId == null)
			categoriePoliceGestionPageId = new CategoriePoliceGestionPageId();
		CategoriePolicePageGestion pageInfo = new CategoriePolicePageGestion();
		pageInfo.setPageId(categoriePoliceGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des types de police
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionTypePolice() throws TechnicalException {

		if (typePoliceGestionPageId == null)
			typePoliceGestionPageId = new TypePoliceGestionPageId();
		TypePolicePageGestion pageInfo = new TypePolicePageGestion();
		pageInfo.setPageId(typePoliceGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des types de support
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionTypeSupport() throws TechnicalException {

		if (typeSupportGestionPageId == null)
			typeSupportGestionPageId = new TypeSupportGestionPageId();
		TypeSupportPageGestion pageInfo = new TypeSupportPageGestion();
		pageInfo.setPageId(typeSupportGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des types de compteur
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionTypeCompteur() throws TechnicalException {

		if (typeCompteurGestionPageId == null)
			typeCompteurGestionPageId = new TypeCompteurGestionPageId();
		TypeCompteurPageGestion pageInfo = new TypeCompteurPageGestion();
		pageInfo.setPageId(typeCompteurGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des types d'emplacement
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionTypeEmplacement() throws TechnicalException {

		if (typeEmplacementGestionPageId == null)
			typeEmplacementGestionPageId = new TypeEmplacementGestionPageId();
		TypeEmplacementPageGestion pageInfo = new TypeEmplacementPageGestion();
		pageInfo.setPageId(typeEmplacementGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des types de zone
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionTypeZone() throws TechnicalException {

		if (typeZoneGestionPageId == null)
			typeZoneGestionPageId = new TypeZoneGestionPageId();
		TypeZonePageGestion pageInfo = new TypeZonePageGestion();
		pageInfo.setPageId(typeZoneGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des codes CTME
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionCodeCtme() throws TechnicalException {

		if (codeCtmeGestionPageId == null)
			codeCtmeGestionPageId = new CodeCtmeGestionPageId();
		CodeCtmePageGestion pageInfo = new CodeCtmePageGestion();
		pageInfo.setPageId(codeCtmeGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

	/**
	 * Ouverture de l'onglet de gestion des analyses
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void openGestionAnalyse() throws TechnicalException {

		if (analyseGestionPageId == null)
			analyseGestionPageId = new AnalyseGestionPageId();
		AnalysePageGestion pageInfo = new AnalysePageGestion();
		pageInfo.setPageId(analyseGestionPageId);

		TabComposer wm = getWindowManager();

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}

}
