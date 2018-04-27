package nc.noumea.mairie.bilan.energie.web.ep;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.Luminaire;
import nc.noumea.mairie.bilan.energie.contract.dto.Support;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.contract.service.ClientService;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSupportService;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de création / édition / consultation d'un support
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SupportEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private EclairagePublicService eclairagePublicService;
	
	@WireVariable
	private TypeSupportService typeSupportService;

	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private AdresseService adresseService;
	
	@WireVariable
	private ClientService clientService;
	
	/** Window d'édition des supports */
	@Wire
	private Window supportEditWindow;

	/** Support en cours d'édition */
	private Support selectedRecord;
	
	/** Adresse sélectionné dans le choix d'adresse */
	private AdresseLabel selectedAdresse;

	/** Eclairage Publie en cours d'édition */
	private EclairagePublic selectedEclairagePublic;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Nouvel Enregistrement ? */
	private boolean nouveau;
	
	/** Passage en mode Edition possible ? */
	private boolean editable;
	
	/** Liste des adresses pour la recherche */
	private List<AdresseLabel> listeAdresse;
	
	/** Liste des modèles pour la recherche */
	private List<String> listeModele;

	/** Bandbox Adresse */
	@Wire
	private Bandbox bandboxAdresse;
	
	/** Combobox Modèle */
	@Wire
	private Combobox comboboxModele;

	/**
	 * @return the selectedRecord
	 */
	public Support getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Support selectedRecord) {
		this.selectedRecord = selectedRecord;
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
	 * @return the selectedEclairagePublic
	 */
	public EclairagePublic getSelectedEclairagePublic() {
		return selectedEclairagePublic;
	}

	/**
	 * @param selectedEclairagePublic
	 *            the selectedEclairagePublic to set
	 */
	public void setSelectedEclairagePublic(
			EclairagePublic selectedEclairagePublic) {
		this.selectedEclairagePublic = selectedEclairagePublic;
	}

	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param edit
	 *            the recordMode to set
	 */
	public void setRecordMode(RecordMode edit) {
		this.recordMode = edit;
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
	 * @return the listeAdresse
	 */
	public List<AdresseLabel> getListeAdresse() {
		return listeAdresse;
	}

	/**
	 * @param listeAdresse the listeAdresse to set
	 */
	@NotifyChange("listboxAdresse")
	public void setListeAdresse(List<AdresseLabel> listeAdresse) {
		this.listeAdresse = listeAdresse;
	}

	/**
	 * get ListeModele
	 *
	 * @return listeModele
	 */
	public List<String> getListeModele() {
		return listeModele;
	}

	/**
	 * set ListeModele
	 *
	 * @param listeModele ListeModele to set
	 */
	public void setListeModele(List<String> listeModele) {
		this.listeModele = listeModele;
	}

	/**
	 * Retourne la liste des type de support
	 * 
	 * @return Liste des types de support
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeTypeSupport() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = typeSupportService.getAllReferentiel();
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
	 * @param recordMode Mode d'édition
	 * @param readOnly Mode ReadOnly
     * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedEP") EclairagePublic eclairagePublic,
			@ExecutionArgParam("selectedRecord") Support support,
			@ExecutionArgParam("recordMode") RecordMode recordMode,
			@ExecutionArgParam("readOnly") boolean readOnly)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		setEditable(!readOnly && recordMode.equals(RecordMode.CONSULT));

		this.selectedEclairagePublic = eclairagePublic;

		if (recordMode.equals(RecordMode.NEW)) {
			setNouveau(true);
			
			this.selectedRecord = new Support();
			this.selectedRecord
					.setIdEclairagePublic(this.selectedEclairagePublic.getId());
			this.selectedRecord.setTypeSupport(new CodeLabel());
			this.selectedRecord.setListeLuminaire(new ArrayList<Luminaire>());

			this.selectedRecord.setAuteurCreation(securityService.getCurrentUserName());
			this.selectedRecord.setDateCreation(new Date());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = support;
			if (selectedRecord.getTypeSupport() == null)
				selectedRecord.setTypeSupport(new CodeLabel());
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
		supportEditWindow.detach();
	}

	/**
	 * Validation des modifications appliquées au support d'un éclairage public
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void save() throws TechnicalException {
		
		// Majuscule sur le n° d'inventaire
		if (this.selectedRecord.getNumInventaire() != null)
			this.selectedRecord.setNumInventaire(this.selectedRecord.getNumInventaire()
					.toUpperCase());
		
		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedEclairagePublic.getListeSupport().add(selectedRecord);
		}
		
		this.selectedRecord.setAuteurModif(securityService.getCurrentUserName());
		this.selectedRecord.setDateModif(new Date());

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("eclairagePublic", selectedEclairagePublic);
		params.put("support", selectedRecord);
		
		BindUtils.postGlobalCommand(null, null, "supportUpdated", params);
		supportEditWindow.detach();
	}
	
	/**
	 * Recherche d'une adresse
	 * 
	 * @param searchValue Valeur à chercher
     * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("listeAdresse")
	public void searchAdresse(@BindingParam("searchValue") String searchValue) throws TechnicalException {
		
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


	/**
	 * Recherche d'un modèle
	 * 
	 * @param searchValue Valeur à chercher
     * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("listeModele")
	public void searchModele(@BindingParam("searchValue") String searchValue) throws TechnicalException {
		
			try {
				setListeModele(eclairagePublicService.getAllModele(searchValue));
			} catch (BusinessException e) {
				throw new WebTechnicalException(e.getMessage());
			}
			comboboxModele.open();
	}
}
