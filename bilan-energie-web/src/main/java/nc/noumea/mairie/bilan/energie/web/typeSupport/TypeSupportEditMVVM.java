package nc.noumea.mairie.bilan.energie.web.typeSupport;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSupport;
import nc.noumea.mairie.bilan.energie.contract.service.CodeSIGService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSupportService;
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
 * MVVM de l'écran de création / édition / consultation d'un type de support
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TypeSupportEditMVVM extends AbstractMVVM {

	// Service des typeSupport
	@WireVariable
	private TypeSupportService typeSupportService;
	
	@WireVariable
	private CodeSIGService codeSIGService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	/** Window d'édition des types de support */
	@Wire
	private Window typeSupportEditWindow;

	/** Utilisateur en cours d'édition */
	private TypeSupport selectedRecord;

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
	public TypeSupport getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(TypeSupport selectedRecord) {
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
	 * Retourne la liste des codes SIG
	 * 
	 * @return Liste des codes SIG
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeCodeSIG() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = codeSIGService.getAllReferentiel();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}
	
	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param typeSupport Type de support à éditer
	 * @param recordMode Mode d'édition
     * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") TypeSupport typeSupport,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		setRecordMode(recordMode);
		setEditable(recordMode.equals(RecordMode.CONSULT));

		if (recordMode.equals(RecordMode.NEW)) {

			setNouveau(true);

			this.selectedRecord = new TypeSupport();
			this.selectedRecord.setCodeSIG(new CodeLabel());

			this.selectedRecord.setAuteurCreation(securityService
					.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = typeSupport;
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
		typeSupportEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées à un type de support
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
				this.selectedRecord = typeSupportService
						.create(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				BindUtils.postGlobalCommand(null, null, "typeSupportCreated", params);
			}
			if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = typeSupportService
						.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				BindUtils.postGlobalCommand(null, null, "typeSupportUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		typeSupportEditWindow.detach();
	}
}
