package nc.noumea.mairie.bilan.energie.web.codeCtme;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeCtme;
import nc.noumea.mairie.bilan.energie.contract.service.CodeCtmeService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;

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
 * MVVM de l'écran de création / édition / consultation d'un code CTME
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CodeCtmeEditMVVM extends AbstractMVVM {

	// Service des codeCtme
	@WireVariable
	private CodeCtmeService codeCtmeService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	/** Window d'édition des types de zone */
	@Wire
	private Window codeCtmeEditWindow;

	/** Utilisateur en cours d'édition */
	private CodeCtme selectedRecord;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Passage en mode Edition possible ? */
	private boolean editable;

	/** Nouvel Enregistrement ? */
	private boolean nouveau;

	/**
	 * @return the selectedRecord
	 */
	public CodeCtme getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(CodeCtme selectedRecord) {
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
	 * @param view Composant View
	 * @param codeCtme Code CTME à éditer
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") CodeCtme codeCtme,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		setRecordMode(recordMode);
		setEditable(recordMode.equals(RecordMode.CONSULT));

		if (recordMode.equals(RecordMode.NEW)) {

			setNouveau(true);

			this.selectedRecord = new CodeCtme();

			this.selectedRecord.setAuteurCreation(securityService
					.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = codeCtme;
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
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void close() throws TechnicalException {
		codeCtmeEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées à un type de zone
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void save() throws TechnicalException {
		
		this.selectedRecord
				.setAuteurModif(securityService.getCurrentUserName());
		this.selectedRecord.setDateModif(new Date());

		try {
			if (recordMode.equals(RecordMode.NEW)) {
				this.selectedRecord = codeCtmeService
						.create(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				BindUtils.postGlobalCommand(null, null, "codeCtmeCreated", params);
			}
			if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = codeCtmeService
						.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				BindUtils.postGlobalCommand(null, null, "codeCtmeUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		codeCtmeEditWindow.detach();
	}
}
