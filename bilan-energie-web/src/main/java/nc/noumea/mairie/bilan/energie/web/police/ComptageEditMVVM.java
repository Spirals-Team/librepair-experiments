package nc.noumea.mairie.bilan.energie.web.police;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;

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
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de création / édition / consultation d'un comptage
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ComptageEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private SecurityService securityService;

	/** Window d'édition des comptages */
	@Wire
	private Window comptageEditWindow;

	/** Comptage en cours d'édition */
	private Comptage selectedRecord;

	/** Compteur en cours d'édition */
	private Compteur selectedCompteur;

	/** Police en cours d'édition */
	private Police selectedPolice;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Nouvel Enregistrement ? */
	private boolean nouveau;
	
	/** Passage en mode Edition possible ? */
	private boolean editable;

	/**
	 * @return the selectedRecord
	 */
	public Comptage getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Comptage selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * @return the selectedCompteur
	 */
	public Compteur getSelectedCompteur() {
		return selectedCompteur;
	}

	/**
	 * @param selectedCompteur
	 *            the selectedCompteur to set
	 */
	public void setSelectedCompteur(Compteur selectedCompteur) {
		this.selectedCompteur = selectedCompteur;
	}

	/**
	 * @return the selectedPolice
	 */
	public Police getSelectedPolice() {
		return selectedPolice;
	}

	/**
	 * @param selectedPolice
	 *            the selectedPolice to set
	 */
	public void setSelectedPolice(Police selectedPolice) {
		this.selectedPolice = selectedPolice;
	}

	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param recordMode Mode d'édition
	 *            the recordMode to set
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
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param police Police lié au comptage
	 * @param compteur Compteur lié au comptage
	 * @param comptage Comptage à éditer
	 * @param recordMode Mode d'édition
	 * @param readOnly Mode ReadOnly
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedPolice") Police police,
			@ExecutionArgParam("selectedCompteur") Compteur compteur,
			@ExecutionArgParam("selectedRecord") Comptage comptage,
			@ExecutionArgParam("recordMode") RecordMode recordMode,
			@ExecutionArgParam("readOnly") boolean readOnly)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);

		this.selectedCompteur = compteur;
		this.selectedPolice = police;
		setEditable(!readOnly && recordMode.equals(RecordMode.CONSULT) && selectedPolice
				.getType().isComptageModifiable());

		if (recordMode.equals(RecordMode.NEW)) {

			setNouveau(true);

			this.selectedRecord = new Comptage();
			this.selectedRecord.setIdCompteur(this.selectedCompteur.getId());

			this.selectedRecord.setAuteurCreation(securityService
					.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = comptage;
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
	}

	/**
	 * Fermeture de la fenêtre
	 * 
	 */
	@Command
	public void close()  {
		comptageEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées au comptage d'un compteur
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void save() throws TechnicalException {

		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedCompteur.getListeComptage().add(selectedRecord);
		}

		this.selectedRecord
				.setAuteurModif(securityService.getCurrentUserName());
		this.selectedRecord.setDateModif(new Date());

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("police", selectedPolice);
		params.put("comptage", selectedRecord);
		BindUtils.postGlobalCommand(null, null, "comptageUpdated", params);
		comptageEditWindow.detach();
	}

	/**
	 * Police de catégorie Electricite ?
	 * 
	 * @return booléen
	 */
	public boolean isPoliceElectricite() {

		if (selectedPolice.getType() != null)
			return selectedPolice.getType().getCategorie().getLabel().equals("ELECTRICITE");
		else
			return false;
	}
}
