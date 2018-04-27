package nc.noumea.mairie.bilan.energie.web.facture;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.FichierAnomalie;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.contract.enumeration.EtatFichier;
import nc.noumea.mairie.bilan.energie.contract.service.FichierFactureService;
import nc.noumea.mairie.bilan.energie.contract.service.IntegrationFactureService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.Notification;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

/**
 * MVVM de l'écran de mise à jour de l'intégration des fichiers de facture
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FactureEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private FichierFactureService fichierFactureService;

	@WireVariable
	private IntegrationFactureService integrationFactureService;

	/** Fichier en cours d'édition */
	private FichierFactureSimple selectedRecord;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Anomalie sélectionnée dans la listbox */
	private FichierAnomalie selectedAnomalie;

	/**
	 * @return the selectedRecord
	 */
	public FichierFactureSimple getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(FichierFactureSimple selectedRecord) {
		this.selectedRecord = selectedRecord;
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
	 * @return the selectedAnomalie
	 */
	public FichierAnomalie getSelectedAnomalie() {
		return selectedAnomalie;
	}

	/**
	 * @param selectedAnomalie
	 *            the selectedAnomalie to set
	 */
	public void setSelectedAnomalie(FichierAnomalie selectedAnomalie) {
		this.selectedAnomalie = selectedAnomalie;
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param fichierFacture Fichier facture à éditer
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") FichierFactureSimple fichierFacture,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);

		if (recordMode.equals(RecordMode.EDIT)) {
			this.selectedRecord = fichierFacture;
		}

	}


	/**
	 * dirty ?
	 *
	 * @return booléen
	 * @throws TechnicalException Exception technique
	 */
	public boolean isDirty() throws TechnicalException {
		return getWindowManager().isDirty(getWindowManager().getCurrentPage().getInfo().getId());
	}
	
	/**
	 * Modification du booléen Traitée
	 *
	 * @param event Evénement ZK
	 * @param fichierAnomalie Anomalie concernée
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange({"selectedRecord", "dirty"})
	public void changeAnomalieTraitee(@ContextParam(ContextType.TRIGGER_EVENT)  CheckEvent event,
			@BindingParam("anomalie") FichierAnomalie fichierAnomalie) throws TechnicalException {
		this.selectedAnomalie = fichierAnomalie;
		
		if (event.isChecked()) {
			this.selectedRecord.setNbErreursTraitees(this.selectedRecord.getNbErreursTraitees()+1);
		} else {
			this.selectedRecord.setNbErreursTraitees(this.selectedRecord.getNbErreursTraitees()-1);
		}
		
		if (selectedRecord.getNbErreurs().equals(selectedRecord.getNbErreursTraitees())) 
			selectedRecord.setEtat(EtatFichier.ANOMALIES_TRAITEES);
		else
			selectedRecord.setEtat(EtatFichier.ANOMALIE_INTEGRATION);
		
		getWindowManager().setDirtyOnCurrentPage(true);

	}

	/**
	 * Sauvegarde d'un fichier de facture
	 * 
	 * @param close Booléen de fermeture de l'onglet
	 * @param pageId Identifiant de la page
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("selectedRecord")
	public void save(@BindingParam("close") boolean close,
			@BindingParam("pageId") PageId pageId) throws TechnicalException {

		try {
			if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = fichierFactureService
						.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("fichierFacture", selectedRecord);
				BindUtils.postGlobalCommand(null, null,
						"fichierFactureUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		getWindowManager().setDirtyOnCurrentPage(false);

		getWindowManager().showNotification(
				new Notification("Le fichier a été enregistré"));
		if (close)
			try {
				getWindowManager().closePage(pageId);
			} catch (PageNotExistException e) {
				throw new WebTechnicalException(e.getMessage());
			}

	}

	@Command
	@NotifyChange("selectedRecord")
	public void reIntegrerFichierFacture() throws TechnicalException {

		//getWindowManager().showNotification(new Notification("Le fichier a été relancé"));

		//Clients.showNotification("Le fichier a été relancé", "info", null, "bottom_right", 3000);

		try {
				integrationFactureService.reIntegrerFichierFacture(selectedRecord);
				getWindowManager().showNotification(new Notification("Le fichier a été relancé"));
		} catch (Exception e) {
			getWindowManager().showError("Erreur réintégration", e.getMessage());
		}

	}

	public boolean isReIntegrable(){
		return !this.selectedRecord.getEtat().equals(EtatFichier.INTEGRE);
	}

}
