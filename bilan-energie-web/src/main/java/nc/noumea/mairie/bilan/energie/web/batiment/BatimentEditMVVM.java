package nc.noumea.mairie.bilan.energie.web.batiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.contract.service.BatimentService;
import nc.noumea.mairie.bilan.energie.contract.service.CodeCtmeService;
import nc.noumea.mairie.bilan.energie.contract.service.ConversionService;
import nc.noumea.mairie.bilan.energie.contract.service.DirectionService;
import nc.noumea.mairie.bilan.energie.contract.service.InfrastructureService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.Notification;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.police.PoliceEditPageId;
import nc.noumea.mairie.bilan.energie.web.police.PolicePageEdit;
import nc.noumea.mairie.bilan.energie.web.wm.PageExistException;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;
import nc.noumea.mairie.bilan.energie.web.wm.TabComposer;

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
import org.zkoss.zul.Checkbox;

/**
 * MVVM de l'écran de création / édition / consultation d'un batiment
 * 
 * @author Greg Dujardin
 */
/**
 * @author Greg Dujardin
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BatimentEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private BatimentService batimentService;
	
	@WireVariable
	private InfrastructureService infrastructureService;

	@WireVariable
	private DirectionService directionService;

	@WireVariable
	private CodeCtmeService codeCtmeService;

	@WireVariable
	private ConversionService conversionService;

	@WireVariable
	private AdresseService adresseService;

	@WireVariable
	private PoliceService policeService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	/** Batiment en cours d'édition */
	private Batiment selectedRecord;

	/** Adresse sélectionné dans le choix d'adresse */
	private AdresseLabel selectedAdresse;
	
	/** Police sélectionnée dans la listbox */
	private Police selectedPolice;
	
	/** Compteur sélectionné dans la listbox */
	private Compteur selectedCompteur;

	/** Comptage sélectionné dans la listbox */
	private Comptage selectedComptage;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;
	
	/** Checkbox AffichageHistorique */
	@Wire
	private Checkbox afficheHistorique;
	
	/** Liste des adresses pour la recherche */
	private List<AdresseLabel> listeAdresse;
	
	/** Liste des polices */
	private List<Police> listePolice;
	
	/** Liste des compteurs à afficher */
	private List<Compteur> listeCompteur;

	/** Liste des comptages à afficher */
	private List<Comptage> listeComptage;

	/** Bandbox Adresse */
	@Wire
	private Bandbox bandboxAdresse;


	/**
	 * @return the selectedRecord
	 */
	public Batiment getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Batiment selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	/**
	 * Retourne le service de sécurité
	 * 
	 * @return Service de sécurité
	 */
	public SecurityService getSecurityService() {
		return securityService;
	}
	
	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * the recordMode to set
	 * 
	 * @param recordMode Mode d'édition
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
	 * the readOnly to set
	 * 
	 * @param readOnly Mode ReadOnly
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	/**
	 * @return the selectedAdresse
	 */
	public AdresseLabel getSelectedAdresse() {
		return selectedAdresse;
	}

	/**
	 * @param selectedAdresse
	 *            the selectedAdresse to set
	 */
	public void setSelectedAdresse(AdresseLabel selectedAdresse) {
		this.selectedAdresse = selectedAdresse;
	}
	
	/**
	 * Retourne la liste des polices à afficher en fonction de la checkbox
	 * 'Historique'
	 * 
	 * @return Liste des polices à afficher
	 */
	public List<Police> getListePolice() {
		if (afficheHistorique.isChecked())
			listePolice = selectedRecord.getListePolice();
		else {
			List<Police> listeRetour = new ArrayList<Police>();

			if (selectedRecord != null && selectedRecord.getListePolice() != null)
				for (Police police : selectedRecord.getListePolice())
					if (police.getDateFin() == null)
						listeRetour.add(police);
			listePolice = listeRetour;
		}

		return listePolice;
	}

	/**
	 * set ListePolice
	 * 
	 * @param listePolice ListePolice to set
	 */
	public void setListePolice(List<Police> listePolice) {
		this.listePolice = listePolice;
	}

	/**
	 * Retourne la liste des compteurs à afficher en fonction de la checkbox
	 * 'Historique'
	 * 
	 * @return Liste des compteurs à afficher
	 */
	public List<Compteur> getListeCompteur() {

		if (selectedPolice != null) {
			if (afficheHistorique.isChecked())
				listeCompteur = selectedPolice.getListeCompteur();
			else {
				List<Compteur> listeRetour = new ArrayList<Compteur>();
	
				for (Compteur compteur : selectedPolice.getListeCompteur())
					if (compteur.getDateFin() == null)
						listeRetour.add(compteur);
				listeCompteur = listeRetour;
			}
			if (listeCompteur.size() == 1)
				setSelectedCompteur(listeCompteur.get(0));


		}
		
		return listeCompteur;
	}

	/**
	 * @param listeCompteur
	 *            the listeCompteur to set
	 */
	public void setListeCompteur(List<Compteur> listeCompteur) {
		this.listeCompteur = listeCompteur;
	}

	/**
	 * Retourne la liste des comptages à afficher en fonction du compteur
	 * sélectionné
	 * 
	 * @return Liste des comptages à afficher
	 */
	public List<Comptage> getListeComptage() {

		if (selectedCompteur != null) {
			listeComptage = selectedCompteur.getListeComptage();

			Collections.sort(listeComptage, new Comparator<Comptage>() {
				public int compare(Comptage m1, Comptage m2) {
					return m2.getDate().compareTo(m1.getDate());
				}
			});
		}

		return listeComptage;
	}

	/**
	 * @param listeComptage
	 *            the listeComptage to set
	 */
	public void setListeComptage(List<Comptage> listeComptage) {
		this.listeComptage = listeComptage;
	}

	/**
	 * Police de type électricité ?
	 * 
	 * @return booléen
	 */
	public boolean isPoliceElectricite() {

		if (selectedPolice != null) {
			return selectedPolice.getType().getCategorie().getLabel().equals("ELECTRICITE");
		} else
			return true;
	}
	
	/**
	 * Nouveau ?
	 * 
	 * @return booléen
	 */
	public boolean isNouveau() {

		return recordMode.equals(RecordMode.NEW);
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
	@NotifyChange({ "listeCompteur", "listeComptage" , "policeElectricite"})
	public void setSelectedPolice(Police selectedPolice) {
		this.selectedPolice = selectedPolice;
	}

	/**
	 * get SelectedCompteur
	 * 
	 * @return selectedCompteur
	 */
	public Compteur getSelectedCompteur() {
		return selectedCompteur;
	}

	/**
	 * set SelectedCompteur
	 * 
	 * @param selectedCompteur SelectedCompteur to set
	 */
	@NotifyChange("listeComptage")
	public void setSelectedCompteur(Compteur selectedCompteur) {
		this.selectedCompteur = selectedCompteur;
	}

	/**
	 * get SelectedComptage
	 * 
	 * @return selectedComptage
	 */
	public Comptage getSelectedComptage() {
		return selectedComptage;
	}

	/**
	 * set SelectedComptage
	 * 
	 * @param selectedComptage SelectedComptage to set
	 */
	public void setSelectedComptage(Comptage selectedComptage) {
		this.selectedComptage = selectedComptage;
	}

	/**
	 * Retourne la liste des infrastructures
	 * 
	 * @return Liste des infrastructures
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeInfrastructure() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = infrastructureService.getAllReferentielByCriteres(false, true);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}

	/**
	 * Retourne la liste des directions
	 * 
	 * @return Liste des directions
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeDirection() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = directionService.getAllReferentiel();
			CodeLabel codeLabel = new CodeLabel();
			codeLabel.setLabel(" ");
			liste.add(0,codeLabel);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}
	
	/**
	 * Retourne la liste des codes CTME
	 * 
	 * @return Liste des codes CTME
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeCodeCtme() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = codeCtmeService.getAllReferentiel();
			CodeLabel codeLabel = new CodeLabel();
			codeLabel.setLabel(" ");
			liste.add(0,codeLabel);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}
	
	/**
	 * Retourne la liste des taux de conversion
	 * 
	 * @return Liste des taux de conversion
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeConversion() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = conversionService.getAllReferentiel();
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
	 * @param listeAdresse
	 *            the listeAdresse to set
	 */
	public void setListeAdresse(List<AdresseLabel> listeAdresse) {
		this.listeAdresse = listeAdresse;
	}



	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param batiment Batiment à éditer
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Batiment batiment,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);

		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedRecord = new Batiment();
			this.selectedRecord.setAdresse(new AdresseLabel());
			this.selectedRecord.setCodeCtme(new CodeLabel());
			this.selectedRecord.setConversion(new CodeLabel());
			this.selectedRecord.setDirection(new CodeLabel());
			this.selectedRecord.setDirectionAffectation(new CodeLabel());
			this.selectedRecord.setInfrastructure(new CodeLabel());
		}

		if (recordMode.equals(RecordMode.EDIT)) {
			this.selectedRecord = batiment;
			if (this.selectedRecord.getCodeCtme() == null)
				this.selectedRecord.setCodeCtme(new CodeLabel());
			if (this.selectedRecord.getAdresse() == null)
				this.selectedRecord.setAdresse(new AdresseLabel());
			if (this.selectedRecord.getDirectionAffectation() == null)
				this.selectedRecord.setDirectionAffectation(new CodeLabel());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = batiment;
		}

	}

	/**
	 * Sauvegarde d'un batiment
	 * 
	 * @param close Option de fermeture de l'onglet
	 * @param pageId Identifiant de l'onglet
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange({"selectedRecord","nouveau"})
	public void save(@BindingParam("close") boolean close,
			@BindingParam("pageId") PageId pageId) throws TechnicalException {
		
		// Traitement du code CTME
		if (this.selectedRecord.getCodeCtme().getCode() == null)
			this.selectedRecord.setCodeCtme(null);

		// Traitement de la direction d'affectation
		if (this.selectedRecord.getDirectionAffectation().getCode() == null)
			this.selectedRecord.setDirectionAffectation(null);

		// Traitement de l'adresse
		if (this.selectedRecord.getAdresse().getId() == null)
			this.selectedRecord.setAdresse(null);


		try {
			if (recordMode.equals(RecordMode.NEW)) {
				this.selectedRecord = batimentService.create(this.selectedRecord);

				setRecordMode(RecordMode.EDIT);
				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("batiment", selectedRecord);
				BindUtils
						.postGlobalCommand(null, null, "batimentCreated", params);
			} else if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = batimentService.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("batiment", selectedRecord);
				BindUtils
						.postGlobalCommand(null, null, "batimentUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
		if (this.selectedRecord.getCodeCtme() == null)
			this.selectedRecord.setCodeCtme(new CodeLabel());
		if (this.selectedRecord.getAdresse() == null)
			this.selectedRecord.setAdresse(new AdresseLabel());
		if (this.selectedRecord.getDirectionAffectation() == null)
			this.selectedRecord.setDirectionAffectation(new CodeLabel());

		getWindowManager().setDirtyOnCurrentPage(false);
		getWindowManager().showNotification(
				new Notification("Le batiment a été enregistré"));
		if (close)
			try {
				getWindowManager().closePage(pageId);
			} catch (PageNotExistException e) {
				throw new WebTechnicalException(e.getMessage());
			}

	}

	/**
	 * Modification du batiment ?
	 * 
	 * @return boolean
	 * @throws TechnicalException Exception technique
	 */
	public boolean isDirty() throws TechnicalException {
		return getWindowManager().isDirty(getWindowManager().getCurrentPage().getInfo().getId());
	}
	
	
	/**
	 * Command de mise à jour d'un élément de la page
	 * 
	 * @param batiment Batiment mis à jour
     * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange({ "dirty"})
	public void miseAJour(
			@BindingParam("batiment") Batiment batiment) throws TechnicalException {

		if (batiment.getId() == selectedRecord.getId()) {

			getWindowManager().setDirtyOnCurrentPage(true);

		}
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
	@NotifyChange({ "dirty" })
	public void affectAdresse() throws TechnicalException {

		selectedRecord.setAdresse(selectedAdresse);
		bandboxAdresse.setValue(selectedAdresse.getAdresseLigne1());
		bandboxAdresse.close();
		getWindowManager().setDirtyOnCurrentPage(true);
	}
	
	/**
	 * Rafraîchissement des listes
	 * 
	 */
	@Command
	@NotifyChange({ "listeCompteur", "listeComptage", "listePolice" })
	public void refreshListe() {

	}
	
	/**
	 * Gestion du double-clic sur la liste des polices
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void listPoliceDoubleClicked() throws TechnicalException {

		Police police;

		// Chargement de la police depuis la BDD
		try {
			police = policeService.read(getSelectedPolice().getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Ouverture d'une page avec la police
		PoliceEditPageId id = new PoliceEditPageId();
		id.setId(police.getId());

		PolicePageEdit pageInfo = new PolicePageEdit();
		pageInfo.setPageId(id);
		pageInfo.setPolice(police);
		pageInfo.setRecordMode(RecordMode.EDIT);
		pageInfo.setTitle(police.getNumPolice());
		TabComposer wm = getWindowManager();

		try {
			if (securityService.isAdministrateurBatiment()
					|| securityService.isContributeurBatiment()) {
				pageInfo.setRecordMode(RecordMode.EDIT);

			} else {
				pageInfo.setRecordMode(RecordMode.CONSULT);
			}
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		try {
			wm.showPage(pageInfo);
		} catch (PageExistException e) {
			try {
				wm.showPage(e.getPageInfo().getId());
			} catch (PageNotExistException e1) {
				throw new WebTechnicalException(e1.getMessage(), e1);
			}
		}
	}


}
