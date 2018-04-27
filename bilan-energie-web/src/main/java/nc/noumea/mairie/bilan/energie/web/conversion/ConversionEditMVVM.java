package nc.noumea.mairie.bilan.energie.web.conversion;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Conversion;
import nc.noumea.mairie.bilan.energie.contract.service.ConversionService;
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
 * MVVM de l'écran de création / édition / consultation d'un taux de conversion
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConversionEditMVVM extends AbstractMVVM {

	// Service des conversion
	@WireVariable
	private ConversionService conversionService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	/** Window d'édition des conversions */
	@Wire
	private Window conversionEditWindow;

	/** Utilisateur en cours d'édition */
	private Conversion selectedRecord;

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
	public Conversion getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Conversion selectedRecord) {
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
	 * @param conversion Conversion à éditer
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Conversion conversion,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		setRecordMode(recordMode);
		try {
			setEditable(recordMode.equals(RecordMode.CONSULT) 
					&& securityService.isAdministrateur());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		if (recordMode.equals(RecordMode.NEW)) {

			setNouveau(true);

			this.selectedRecord = new Conversion();

			this.selectedRecord.setAuteurCreation(securityService
					.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = conversion;
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
		conversionEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées à une conversion
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
				this.selectedRecord = conversionService
						.create(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				BindUtils.postGlobalCommand(null, null, "conversionCreated", params);
			}
			if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = conversionService
						.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				BindUtils.postGlobalCommand(null, null, "conversionUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		conversionEditWindow.detach();
	}
}
