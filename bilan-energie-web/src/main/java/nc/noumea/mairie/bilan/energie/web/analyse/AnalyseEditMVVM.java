package nc.noumea.mairie.bilan.energie.web.analyse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Analyse;
import nc.noumea.mairie.bilan.energie.contract.dto.StructureLabel;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnalyse;
import nc.noumea.mairie.bilan.energie.contract.service.AnalyseService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.StructureService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;

import org.springframework.context.MessageSource;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de création / édition / consultation d'une analyse
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AnalyseEditMVVM extends AbstractMVVM {

	// Service des analyse
	@WireVariable
	private AnalyseService analyseService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	// Service des structures
	@WireVariable
	private StructureService structureService;
	
	@WireVariable
	private MessageSource messageSource;

	/** Window d'édition des analyses */
	@Wire
	private Window analyseEditWindow;

	/** Bandbox Structure */
	@Wire
	private Bandbox bandboxStructure;

	/** Liste des structures pour la recherche */
	private List<StructureLabel> listeStructure;

	/** Utilisateur en cours d'édition */
	private Analyse selectedRecord;

	/** Structure sélectionnée dans le choix des structures */
	private StructureLabel selectedStructure;

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
	public Analyse getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Analyse selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * @return the selectedStructure
	 */
	public StructureLabel getSelectedStructure() {
		return selectedStructure;
	}

	/**
	 * @param selectedStructure
	 *            the selectedStructure to set
	 */
	public void setSelectedStructure(StructureLabel selectedStructure) {
		this.selectedStructure = selectedStructure;
	}

	/**
	 * @return the listeStructure
	 */
	public List<StructureLabel> getListeStructure() {
		return listeStructure;
	}

	/**
	 * @param listeStructure
	 *            the listeStructure to set
	 */
	public void setListeStructure(List<StructureLabel> listeStructure) {
		this.listeStructure = listeStructure;
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
	 * Liste des types d'analyse
	 * 
	 * @return Liste des types d'analyse
	 */
	public List<TypeAnalyse> getListeTypeAnalyse() {
		return new ArrayList<TypeAnalyse>(Arrays.asList(TypeAnalyse.values()));
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param analyse Analyse à éditer
	 * @param recordMode Mode d'édition Mode d'ouverture
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Analyse analyse,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		setRecordMode(recordMode);
		try {
			setEditable(recordMode.equals(RecordMode.CONSULT)
					&& securityService.isContributeur());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		if (recordMode.equals(RecordMode.NEW)) {

			setNouveau(true);

			this.selectedRecord = new Analyse();

			this.selectedRecord.setAuteurCreation(securityService
					.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = analyse;
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
		analyseEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées à une analyse
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void save() throws TechnicalException {

		if (this.selectedRecord.getStructure() == null)
			// Pas de structure --> Message d'erreur
						getWindowManager()
						.showError(
						messageSource
								.getMessage(
										"titre.validationimpossible",
										new Object[] {},
										Locale.FRANCE),
						messageSource
								.getMessage(
										"structure.manquante",
										new Object[] {},
										Locale.FRANCE));
		else {
		
			this.selectedRecord
					.setAuteurModif(securityService.getCurrentUserName());
			this.selectedRecord.setDateModif(new Date());
	
			try {
				if (recordMode.equals(RecordMode.NEW)) {
					this.selectedRecord = analyseService
							.create(this.selectedRecord);
	
					Map<String, Object> params = new Hashtable<String, Object>();
					BindUtils.postGlobalCommand(null, null, "analyseCreated",
							params);
				}
				if (recordMode.equals(RecordMode.EDIT)) {
					this.selectedRecord = analyseService
							.update(this.selectedRecord);
	
					Map<String, Object> params = new Hashtable<String, Object>();
					BindUtils.postGlobalCommand(null, null, "analyseUpdated",
							params);
				}
	
			} catch (BusinessException e) {
				throw new WebTechnicalException(e.getMessage());
			}
	
			analyseEditWindow.detach();
		}
	}

	/**
	 * Recherche d'une structure
	 * 
	 * @param searchValue Valeur recherchée
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("listeStructure")
	public void searchStructure(@BindingParam("searchValue") String searchValue)
			throws TechnicalException {

		try {
			setListeStructure(structureService
					.getAllStructureByDesignation(searchValue));
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
	}

	/**
	 * Affectation d'une structure
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void affectStructure() throws TechnicalException {

		selectedRecord.setStructure(selectedStructure);
		bandboxStructure.setValue(selectedStructure.getDesignation());
		bandboxStructure.close();
	}

}
