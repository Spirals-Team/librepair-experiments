package nc.noumea.mairie.bilan.energie.web.police;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeCompteurService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeEmplacementService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;

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
 * MVVM de l'écran de création / édition / consultation d'un compteur
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CompteurEditMVVM extends AbstractMVVM {
	
	/**
	 * Injection des services
	 */	@WireVariable
	private TypeCompteurService typeCompteurService;
	 
	@WireVariable
	private TypeEmplacementService typeEmplacementService;
	
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private AdresseService adresseService;
	
	/** Window d'édition des compteurs */
	@Wire
	private Window compteurEditWindow;

	/** Compteur en cours d'édition */
	private Compteur selectedRecord;
	
	/** Police en cours d'édition */
	private Police selectedPolice;
	
	/** Adresse sélectionnée dans le choix d'adresse */
	private AdresseLabel selectedAdresse;
	
	/** Mode d'ouverture de la page */
	private RecordMode recordMode;
	
	/** Accès read only ? */
	private boolean readOnly;

	/** Passage en mode Edition possible ? */
	private boolean editable;
	
	/** Nouvel Enregistrement ? */
	private boolean nouveau;
	
	/** Liste des adresses pour la recherche */
	private List<AdresseLabel> listeAdresse;

	/** Bandbox Adresse */
	@Wire
	private Bandbox bandboxAdresse;
	
	/**
	 * @return the selectedRecord
	 */
	public Compteur getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord the selectedRecord to set
	 */
	public void setSelectedRecord(Compteur selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * @return the selectedPolice
	 */
	public Police getSelectedPolice() {
		return selectedPolice;
	}

	/**
	 * @param selectedPolice the selectedPolice to set
	 */
	public void setSelectedPolice(Police selectedPolice) {
		this.selectedPolice = selectedPolice;
	}

	/**
	 * @return the selectedAdresse
	 */
	public AdresseLabel getSelectedAdresse() {
		return selectedAdresse;
	}

	/**
	 * @param selectedAdresse the selectedAdresse to set
	 */
	public void setSelectedAdresse(AdresseLabel selectedAdresse) {
		this.selectedAdresse = selectedAdresse;
	}

	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param recordMode Mode d'édition the recordMode to set
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
	 * @param readOnly the readOnly to set
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
	 * @param editable the editable to set
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
	 * @param nouveau the nouveau to set
	 */
	public void setNouveau(boolean nouveau) {
		this.nouveau = nouveau;
	}

	/**
	 * @return the listeAdresse
	 */
	public List<AdresseLabel> getListeAdresse() {
		return listeAdresse;
	}

	/**
	 * @param listeAdresse the listeAdresse to set
	 */
	public void setListeAdresse(List<AdresseLabel> listeAdresse) {
		this.listeAdresse = listeAdresse;
	}

	/**
	 * Retourne la liste des type de compteur
	 * 
	 * @return Liste des types de compteur
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeTypeCompteur() throws TechnicalException {
		
		List<CodeLabel> liste;
		try {
			liste = typeCompteurService.getAllReferentiel() ;
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
		return liste;
	}
	
	/**
	 * Retourne la liste des type d'emplacement
	 * 
	 * @return Liste des types d'emplacement
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeTypeEmplacement() throws TechnicalException {
		
		List<CodeLabel> liste;
		try {
			liste = typeEmplacementService.getAllReferentiel() ;
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
		return liste;
	}


	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param police Police lié au compteur
	 * @param compteur à éditer
	 * @param recordMode Mode d'édition
	 * @param readOnly Mode ReadOnly
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup (
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedPolice") Police police,
			@ExecutionArgParam("selectedRecord") Compteur compteur,
			@ExecutionArgParam("recordMode") RecordMode recordMode,
			@ExecutionArgParam("readOnly") boolean readOnly)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		setEditable(!readOnly && recordMode.equals(RecordMode.CONSULT));

		this.selectedPolice = police;
		
		if (recordMode.equals(RecordMode.NEW)) {
			
			setNouveau(true);
			
			this.selectedRecord = new Compteur();
			this.selectedRecord.setIdPolice(this.selectedPolice.getId());
			this.selectedRecord.setType(new CodeLabel());
			this.selectedRecord.setTypeEmplacement(new CodeLabel());
			this.selectedRecord.setListeComptage(new ArrayList<Comptage>());
			
			this.selectedRecord.setAuteurCreation(securityService.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = compteur;
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
	public void close() {
		compteurEditWindow.detach();
	}
	
	/**
	 * Validation des modifications appliquées compteur d'une police
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void save() throws TechnicalException {
		
		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedPolice.getListeCompteur().add(selectedRecord);
		}
		
		this.selectedRecord.setAuteurModif(securityService.getCurrentUserName());
		this.selectedRecord.setDateModif(new Date());

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("police", selectedPolice);
		params.put("compteur", selectedRecord);
		BindUtils.postGlobalCommand(null, null, "compteurUpdated", params);
		compteurEditWindow.detach();
	}
	
	/**
	 * Recherche d'une adresse
	 * 
	 * @param searchValue Valeur à chercher
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("listeAdresse")
	public void searchAdresse(@BindingParam("searchValue") String searchValue)
			throws TechnicalException {

		try {
			setListeAdresse(adresseService.getAllAdresseByVoie(searchValue));
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
	}

	/**
	 * Affectation d'une adresse
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void affectAdresse() throws TechnicalException {

		selectedRecord.setAdresse(selectedAdresse);
		bandboxAdresse.setValue(selectedAdresse.getAdresseLigne1());
		bandboxAdresse.close();
	}

}
