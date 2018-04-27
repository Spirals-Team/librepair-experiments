package nc.noumea.mairie.bilan.energie.web.utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.dto.UtilisateurRole;
import nc.noumea.mairie.bilan.energie.contract.enumeration.Role;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;

import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de création / édition / consultation d'un utilisateur
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UtilisateurEditMVVM extends AbstractMVVM {

	// Service des utilisateurs
	@WireVariable
	private UtilisateurService utilisateurService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	/** Window d'édition des utilisateurs */
	@Wire
	private Window utilisateurEditWindow;

	/** Utilisateur en cours d'édition */
	private Utilisateur selectedRecord;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Passage en mode Edition possible ? */
	private boolean editable;

	/** Nouvel Enregistrement ? */
	private boolean nouveau;

	/** Checkbox Admin Batiment */
	@Wire
	Checkbox checkboxAdminBat;

	/** Checkbox Contrib Batiment */
	@Wire
	Checkbox checkboxContribBat;

	/** Checkbox Visit Batiment */
	@Wire
	Checkbox checkboxVisitBat;

	/** Checkbox Admin EP */
	@Wire
	Checkbox checkboxAdminEP;

	/** Checkbox Contrib EP */
	@Wire
	Checkbox checkboxContribEP;

	/** Checkbox Visit EP */
	@Wire
	Checkbox checkboxVisitEP;

	/**
	 * @return the selectedRecord
	 */
	public Utilisateur getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Utilisateur selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param recordMode
	 *            Mode d'édition the recordMode to set
	 */
	public void setRecordMode(RecordMode recordMode) {
		this.recordMode = recordMode;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable
	 *            the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * @return the nouveau
	 */
	public boolean isNouveau() {
		return nouveau;
	}

	/**
	 * @param nouveau
	 *            the nouveau to set
	 */
	public void setNouveau(boolean nouveau) {
		this.nouveau = nouveau;
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view
	 *            Composant View
	 * @param utilisateur
	 *            Utilisateur à éditer
	 * @param recordMode
	 *            Mode d'édition
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Utilisateur utilisateur,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		setRecordMode(recordMode);
		setEditable(recordMode.equals(RecordMode.CONSULT));

		if (recordMode.equals(RecordMode.NEW)) {

			setNouveau(true);

			this.selectedRecord = new Utilisateur();

			this.selectedRecord.setListeUtilisateurRole(new ArrayList<UtilisateurRole>());
			
			this.selectedRecord.setDateDebut(new Date());
			
			this.selectedRecord.setAuteurCreation(securityService
					.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = utilisateur;
		}
	}

	/**
	 * Bascule en mode édition
	 */
	@Command
	@NotifyChange(".")
	public void switchModeEdition() {
		setRecordMode(RecordMode.EDIT);
		setReadOnly(false);
		setEditable(false);
		changeCKAdminBat();
		changeCKContribBat();
		changeCKAdminEP();
		changeCKContribEP();
	}

	/**
	 * Fermeture de la fenêtre
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void close() throws TechnicalException {
		utilisateurEditWindow.detach();
	}

	/**
	 * Affection ou désaffectation d'un rôle en fonction d'une checkbox
	 * 
	 * @param utilisateur
	 *            Utilisateur à traiter
	 * @param checkbox
	 *            Checkbox du rôle
	 * @param role
	 *            Rôle à traiter
	 * @throws TechnicalException Exception technique
	 */
	private void setUtilisateurRole(Utilisateur utilisateur, Checkbox checkbox,
			Role role) throws TechnicalException {

		if (utilisateur.hasRole(role) && !checkbox.isChecked())
			for (UtilisateurRole utilisateurRole : utilisateur
					.getListeUtilisateurRole()) {
				if (utilisateurRole.getRole().equals(role)) {
					utilisateur.getListeUtilisateurRole().remove(
							utilisateurRole);
					return;
				}
			}
		else if (!utilisateur.hasRole(role) && checkbox.isChecked()) {
			UtilisateurRole utilisateurRole = new UtilisateurRole();

			utilisateurRole.setUtilisateur(utilisateur);
			utilisateurRole.setRole(role);
			utilisateurRole.setAuteurCreation(securityService
					.getCurrentUserName());
			;
			utilisateurRole.setDateCreation(new Date());
			utilisateurRole
					.setAuteurModif(securityService.getCurrentUserName());
			;
			utilisateurRole.setDateModif(new Date());
			utilisateurRole.setVersion(0);
			utilisateur.getListeUtilisateurRole().add(utilisateurRole);
		}
	}

	/**
	 * Validation des modifications appliquées compteur d'un utilisateur
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void save() throws TechnicalException {

		// Gestion des cases à cocher (si disable la valeur n'est pas récupérée)
		setUtilisateurRole(this.selectedRecord, checkboxAdminBat,
				Role.ADMINISTRATEUR_BATIMENT);
		setUtilisateurRole(this.selectedRecord, checkboxContribBat,
				Role.CONTRIBUTEUR_BATIMENT);
		setUtilisateurRole(this.selectedRecord, checkboxVisitBat,
				Role.VISITEUR_BATIMENT);
		setUtilisateurRole(this.selectedRecord, checkboxAdminEP,
				Role.ADMINISTRATEUR_EP);
		setUtilisateurRole(this.selectedRecord, checkboxContribEP,
				Role.CONTRIBUTEUR_EP);
		setUtilisateurRole(this.selectedRecord, checkboxVisitEP,
				Role.VISITEUR_EP);

		this.selectedRecord
				.setAuteurModif(securityService.getCurrentUserName());
		this.selectedRecord.setDateModif(new Date());

		try {
			if (recordMode.equals(RecordMode.NEW)) {
				this.selectedRecord = utilisateurService
						.create(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("utilisateur", selectedRecord);
				BindUtils.postGlobalCommand(null, null, "utilisateurCreated",
						params);
			}
			if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = utilisateurService
						.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("utilisateur", selectedRecord);
				BindUtils.postGlobalCommand(null, null, "utilisateurUpdated",
						params);
			}

		} catch (DataIntegrityViolationException e) {
			getWindowManager().showError(
					"Création de l'utilisateur impossible",
					"Le login " + selectedRecord.getLogin() + " existe déjà.");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		utilisateurEditWindow.detach();
	}

	/**
	 * Gestion des checkbox Batiment
	 */
	@Command
	@NotifyChange({ "disabledContribBat", "disabledVisitBat" })
	public void changeCKAdminBat() {
		if (checkboxAdminBat.isChecked()) {
			checkboxContribBat.setChecked(true);
		} else {
			checkboxContribBat.setChecked(false);
		}
		changeCKContribBat();
	}

	/**
	 * Gestion des checkbox Batiment
	 */
	@Command
	@NotifyChange("disabledVisitBat")
	public void changeCKContribBat() {
		if (checkboxContribBat.isChecked()) {
			checkboxVisitBat.setChecked(true);
		} else {
			checkboxVisitBat.setChecked(false);
		}
	}

	/**
	 * Gestion des checkbox EP
	 */
	@Command
	@NotifyChange({ "disabledContribEP", "disabledVisitEP" })
	public void changeCKAdminEP() {
		if (checkboxAdminEP.isChecked()) {
			checkboxContribEP.setChecked(true);
		} else {
			checkboxContribEP.setChecked(false);
		}
		changeCKContribEP();
	}

	/**
	 * Gestion des checkbox EP
	 */
	@Command
	@NotifyChange("disabledVisitEP")
	public void changeCKContribEP() {
		if (checkboxContribEP.isChecked()) {
			checkboxVisitEP.setChecked(true);
		} else {
			checkboxVisitEP.setChecked(false);
		}
	}
	
	/**
	 * Checkbox Administrateur Batiment disable ?
	 * 
	 * @return booléen
 	 * @throws TechnicalException Exception technique
 	 * @throws BusinessException Exception métier
	 */
	public boolean isDisabledAdminBat() throws TechnicalException, BusinessException {
		return (readOnly || !securityService.isAdministrateurBatiment());
	}

	/**
	 * Checkbox Contributeur Batiment disable ?
	 * 
	 * @return booléen
 	 * @throws TechnicalException Exception technique
 	 * @throws BusinessException Exception métier
	 */
	public boolean isDisabledContribBat() throws TechnicalException, BusinessException {
		return (readOnly || checkboxAdminBat.isChecked() || !securityService.isAdministrateurBatiment());
	}

	/**
	 * Checkbox Visiteur Batiment disable ?
	 * 
	 * @return booléen
 	 * @throws TechnicalException Exception technique
 	 * @throws BusinessException Exception métier
	 */
	public boolean isDisabledVisitBat() throws TechnicalException, BusinessException {
		return (readOnly || checkboxContribBat.isChecked()  || !securityService.isAdministrateurBatiment());
	}

	/**
	 * Checkbox Administrateur EP disable ?
	 * 
	 * @return booléen
 	 * @throws TechnicalException Exception technique
 	 * @throws BusinessException Exception métier
	 */
	public boolean isDisabledAdminEP() throws TechnicalException, BusinessException {
		return (readOnly || !securityService.isAdministrateurEP());
	}

	/**
	 * Checkbox Contributeur EP disable ?
	 * 
	 * @return booléen
 	 * @throws TechnicalException Exception technique
 	 * @throws BusinessException Exception métier
	 */
	public boolean isDisabledContribEP() throws TechnicalException, BusinessException {
		return (readOnly || checkboxAdminEP.isChecked()  || !securityService.isAdministrateurEP());
	}

	/**
	 * Checkbox Visiteur EP disable ?
	 * 
	 * @return booléen
 	 * @throws TechnicalException Exception technique
 	 * @throws BusinessException Exception métier
	 */
	public boolean isDisabledVisitEP() throws TechnicalException, BusinessException {
		return (readOnly || checkboxContribEP.isChecked() || !securityService.isAdministrateurEP());
	}

}
