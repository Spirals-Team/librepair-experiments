package nc.noumea.mairie.bilan.energie.web.ep;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.Luminaire;
import nc.noumea.mairie.bilan.energie.contract.dto.Support;
import nc.noumea.mairie.bilan.energie.contract.service.ClientService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSourceService;
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
 * MVVM de l'écran de création / édition / consultation d'un support
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LuminaireEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private TypeSourceService typeSourceService;

	@WireVariable
	private SecurityService securityService;
	
	@WireVariable
	private ClientService clientService;

	/** Window d'édition des supports */
	@Wire
	private Window luminaireEditWindow;
	
	/** Luminaire en cours d'édition */
	private Luminaire selectedRecord;

	/** Support en cours d'édition */
	private Support selectedSupport;

	/** Luminaire en cours d'édition */
	private EclairagePublic selectedEclairagePublic;

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
	public Luminaire getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Luminaire selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * @return the selectedSupport
	 */
	public Support getSelectedSupport() {
		return selectedSupport;
	}

	/**
	 * @param selectedSupport the selectedSupport to set
	 */
	public void setSelectedSupport(Support selectedSupport) {
		this.selectedSupport = selectedSupport;
	}

	/**
	 * @return the selectedEclairagePublic
	 */
	public EclairagePublic getSelectedEclairagePublic() {
		return selectedEclairagePublic;
	}

	/**
	 * @param selectedEclairagePublic the selectedEclairagePublic to set
	 */
	public void setSelectedEclairagePublic(EclairagePublic selectedEclairagePublic) {
		this.selectedEclairagePublic = selectedEclairagePublic;
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
	 * @param nouveau the nouveau to set
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
	 * Retourne la liste des clients
	 * 
	 * @return Liste des clients
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeClient() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = clientService.getAllReferentiel();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}
	
	/**
	 * Retourne la liste des type de source
	 * 
	 * @return Liste des types de source
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeTypeSource() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = typeSourceService.getAllReferentiel();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param eclairagePublic EP à éditer
	 * @param support Support à éditer
	 * @param luminaire Luminaire à éditer
	 * @param recordMode Mode d'édition
	 * @param readOnly Mode ReadOnly
     * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedEP") EclairagePublic eclairagePublic,
			@ExecutionArgParam("selectedSupport") Support support,
			@ExecutionArgParam("selectedRecord") Luminaire luminaire,
			@ExecutionArgParam("recordMode") RecordMode recordMode,
			@ExecutionArgParam("readOnly") boolean readOnly)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		setEditable(!readOnly && recordMode.equals(RecordMode.CONSULT));

		
		this.selectedEclairagePublic = eclairagePublic;
		this.selectedSupport = support;
		
		if (recordMode.equals(RecordMode.NEW)) {
			
			setNouveau(true);
			
			this.selectedRecord = new Luminaire();
			this.selectedRecord.setIdSupport(this.selectedSupport.getId());
			this.selectedRecord.setTypeSource(new CodeLabel());
			
			this.selectedRecord.setAuteurCreation(securityService.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = luminaire;
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
		luminaireEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées au luminaire d'un support
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("selectedEclairagePublic")
	public void save() throws TechnicalException {
		
		// Majuscule sur le modèle
		if (this.selectedRecord.getModele() != null)
			this.selectedRecord.setModele(this.selectedRecord.getModele()
					.toUpperCase());
		
		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedSupport.getListeLuminaire().add(selectedRecord);
		}
		
		this.selectedRecord.setAuteurModif(securityService.getCurrentUserName());
		this.selectedRecord.setDateModif(new Date());

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("eclairagePublic", selectedEclairagePublic);
		params.put("luminaire", selectedRecord);
		BindUtils.postGlobalCommand(null, null, "luminaireUpdated", params);
		
		luminaireEditWindow.detach();

	}

}
