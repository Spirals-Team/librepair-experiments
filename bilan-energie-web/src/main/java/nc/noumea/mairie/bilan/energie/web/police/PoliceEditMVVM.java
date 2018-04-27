package nc.noumea.mairie.bilan.energie.web.police;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.StructureLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypePolice;
import nc.noumea.mairie.bilan.energie.contract.exceptions.BusinessValidationException;
import nc.noumea.mairie.bilan.energie.contract.service.AdresseService;
import nc.noumea.mairie.bilan.energie.contract.service.ClientService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.StructureService;
import nc.noumea.mairie.bilan.energie.contract.service.TypePoliceService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.Notification;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;

import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de création / édition / consultation d'une police
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PoliceEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private PoliceService policeService;

	@WireVariable
	private ClientService clientService;

	@WireVariable
	private TypePoliceService typePoliceService;

	@WireVariable
	private AdresseService adresseService;

	@WireVariable
	private StructureService structureService;

	// Service de séurité
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private MessageSource messageSource;

	/** Police en cours d'édition */
	private Police selectedRecord;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Compteur sélectionné dans la listbox */
	private Compteur selectedCompteur;

	/** Comptage sélectionné dans la listbox */
	private Comptage selectedComptage;

	/** Adresse sélectionné dans le choix d'adresse */
	private AdresseLabel selectedAdresse;

	/** Structure sélectionnée dans le choix des structures */
	private StructureLabel selectedStructure;

	/** Checkbox AffichageHistorique */
	@Wire
	private Checkbox afficheHistorique;

	/** Listbox des compteurs */
	@Wire
	private Listbox listboxCompteur;

	/** Listbox des comptages */
	@Wire
	private Listbox listboxComptage;

	/** Liste des adresses pour la recherche */
	private List<AdresseLabel> listeAdresse;

	/** Bandbox Adresse */
	@Wire
	private Bandbox bandboxAdresse;

	/** Liste des structures pour la recherche */
	private List<StructureLabel> listeStructure;

	/** Bandbox Structure */
	@Wire
	private Bandbox bandboxStructure;

	/** ComboBox Type */
	@Wire
	private Combobox comboboxType;

	/** Liste des compteurs à afficher */
	private List<Compteur> listeCompteur;

	/** Liste des comptages à afficher */
	private List<Comptage> listeComptage;

	/**
	 * @return the selectedRecord
	 */
	public Police getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Police selectedRecord) {
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
	 * @return the selectedCompteur
	 */
	public Compteur getSelectedCompteur() {
		return selectedCompteur;
	}

	/**
	 * @param selectedCompteur
	 *            the selectedCompteur to set
	 */
	@NotifyChange("listeComptage")
	public void setSelectedCompteur(Compteur selectedCompteur) {
		this.selectedCompteur = selectedCompteur;
	}

	/**
	 * @return the selectedComptage
	 */
	public Comptage getSelectedComptage() {
		return selectedComptage;
	}

	/**
	 * @param selectedComptage
	 *            the selectedComptage to set
	 */
	public void setSelectedComptage(Comptage selectedComptage) {
		this.selectedComptage = selectedComptage;
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
	 * Retourne le nb de fils
	 * 
	 * @return Nb de fils
	 */
	public String getNbFilsString() {
		if (selectedRecord.getNbFils() == null)
			return "";
		else
			return selectedRecord.getNbFils().toString();
	}

	/**
	 * Affecte le nb de fils
	 * 
	 * @param nbFilsString Nb de fils
	 */
	public void setNbFilsString(String nbFilsString) {
		if (nbFilsString.isEmpty())
			selectedRecord.setNbFils(null);
		else
			selectedRecord.setNbFils(Long.valueOf(nbFilsString));
	}

	/**
	 * Police Electricité ?
	 * 
	 * @return booléen
	 */
	public boolean isPoliceElectricite() {

		if (selectedRecord.getType().getLibelle() != null)
			return selectedRecord.getType().getCategorie().getLabel().equals("ELECTRICITE");
		else
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
	 * Retourne la liste des compteurs à afficher en fonction de la checkbox
	 * 'Historique'
	 * 
	 * @return Liste des compteurs à afficher
	 */
	public List<Compteur> getListeCompteur() {

		if (afficheHistorique.isChecked())
			listeCompteur = selectedRecord.getListeCompteur();
		else {
			List<Compteur> listeRetour = new ArrayList<Compteur>();

			for (Compteur compteur : selectedRecord.getListeCompteur())
				if (compteur.getDateFin() == null)
					listeRetour.add(compteur);
			listeCompteur = listeRetour;
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
	 * Retourne la liste des types de police
	 * 
	 * @return Liste des types de police
     * @throws TechnicalException Exception technique
	 */
	public List<TypePolice> getListeTypePolice() throws TechnicalException {

		List<TypePolice> liste;
		try {
			liste = typePoliceService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param police Police à éditer
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Police police,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);

		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedRecord = new Police();
			this.selectedRecord.setType(new TypePolice());
			this.selectedRecord.setClient(new CodeLabel());
			this.selectedRecord.setListeCompteur(new ArrayList<Compteur>());
		}

		if (recordMode.equals(RecordMode.EDIT)) {
			this.selectedRecord = police;
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = police;
		}

		if (selectedRecord.getListeCompteur().size() > 0)
			selectedCompteur = selectedRecord.getListeCompteur().get(0);
	}

	/**
	 * Sauvegarde d'une police
	 * 
	 * @param close Option de fermeture de la page
	 * @param pageId Identifiant de la page
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange({"selectedRecord","nouveau"})
	public void save(@BindingParam("close") boolean close,
			@BindingParam("pageId") PageId pageId) throws TechnicalException {

		if (this.selectedRecord.getType().getCategorie().getLabel()
				.equals("EAU")) {
			this.selectedRecord.setCalibre(null);
			this.selectedRecord.setNbFils(null);
		}

		try {
			policeService.validate(this.selectedRecord);
			if (recordMode.equals(RecordMode.NEW)) {
				this.selectedRecord = policeService.create(this.selectedRecord);
				setRecordMode(RecordMode.EDIT);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("police", selectedRecord);
				BindUtils
						.postGlobalCommand(null, null, "policeCreated", params);
			} else if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = policeService.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("police", selectedRecord);
				BindUtils
						.postGlobalCommand(null, null, "policeUpdated", params);
			}

			getWindowManager().setDirtyOnCurrentPage(false);
			getWindowManager().showNotification(
					new Notification("La police a été enregistrée"));

		} catch (BusinessException e) {
			getWindowManager().showError("Erreur", e.getMessage());
		}

		if (close)
			try {
				getWindowManager().closePage(pageId);
			} catch (PageNotExistException e) {
				throw new WebTechnicalException(e.getMessage());
			}

	}

	/**
	 * Formulaire Dirty ?
	 * 
	 * @return booléen
	 * @throws TechnicalException Exception technique
	 */
	public boolean isDirty() throws TechnicalException {
		return getWindowManager().isDirty(
				getWindowManager().getCurrentPage().getInfo().getId());
	}

	/**
	 * Command de mise à jour d'un élément de la page
	 * 
	 * @param police Police à mettre à jour
	 * @param compteur Compteur à mettre à jour
	 * @param comptage Comptage à mettre à jour
     * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "compteurUpdated", "comptageUpdated" })
	@NotifyChange({ "listeCompteur", "listeComptage", "dirty",
			"policeElectricite" })
	public void miseAJour(@BindingParam("police") Police police,
			@BindingParam("compteur") Compteur compteur,
			@BindingParam("comptage") Comptage comptage)
			throws TechnicalException {

		if (police.getId() == selectedRecord.getId()) {

			getWindowManager().setDirtyOnCurrentPage(true);

			if (compteur != null)
				setSelectedCompteur(compteur);

			if (comptage != null)
				setSelectedComptage(comptage);
		}
	}

	/**
	 * Rafraîchissement des listes
	 * 
	 */
	@Command
	@NotifyChange({ "listeCompteur", "listeComptage" })
	public void refreshListe() {

	}

	/**
	 * Gestion du double-clic sur la liste des compteurs
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void listCompteurDoubleClicked() throws TechnicalException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedPolice", selectedRecord);
		map.put("selectedRecord", selectedCompteur);
		map.put("recordMode", RecordMode.CONSULT);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/police/compteurEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Création d'un compteur
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void createCompteur() throws TechnicalException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedPolice", selectedRecord);
		map.put("recordMode", RecordMode.NEW);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/police/compteurEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un compteur
	 * 
	 * @param compteur Compteur à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void deleteCompteur(
			@BindingParam("compteurRecord") final Compteur compteur)
			throws TechnicalException {
		this.selectedCompteur = compteur;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("compteur.suppression",
				new Object[] { compteur.getNumCompteur() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							selectedRecord.getListeCompteur().remove(compteur);
							listeCompteur.remove(compteur);
							selectedCompteur = null;
							Map<String, Object> params = new Hashtable<String, Object>();
							params.put("police", selectedRecord);
							BindUtils.postGlobalCommand(null, null,
									"compteurUpdated", params);

						}
					}
				});
	}

	/**
	 * Gestion du double-clic sur la liste des comptages
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void listComptageDoubleClicked() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedPolice", selectedRecord);
		map.put("selectedCompteur", selectedCompteur);
		map.put("selectedRecord", selectedComptage);
		map.put("recordMode", RecordMode.CONSULT);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/police/comptageEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Création d'un comptage
	 * 
	 * @param compteur Compteur lié au comptage
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void createComptage(@BindingParam("compteurRecord") Compteur compteur)
			throws TechnicalException {

		this.selectedCompteur = compteur;

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedPolice", selectedRecord);
		map.put("selectedCompteur", selectedCompteur);
		map.put("recordMode", RecordMode.NEW);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/police/comptageEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un comptage
	 * 
	 * @param comptage Comptage à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void deleteComptage(
			@BindingParam("comptageRecord") final Comptage comptage)
			throws TechnicalException {
		this.selectedComptage = comptage;

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("comptage.suppression",
				new Object[] { df.format(comptage.getDate()) }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							selectedRecord
									.getListeCompteur()
									.get(selectedRecord.getListeCompteur()
											.indexOf(selectedCompteur))
									.getListeComptage().remove(comptage);
							listeCompteur
									.get(selectedRecord.getListeCompteur()
											.indexOf(selectedCompteur))
									.getListeComptage().remove(comptage);
							selectedCompteur.getListeComptage()
									.remove(comptage);
							listeComptage.remove(comptage);
							setSelectedComptage(null);
							Map<String, Object> params = new Hashtable<String, Object>();
							params.put("police", selectedRecord);
							params.put("compteur", selectedCompteur);
							BindUtils.postGlobalCommand(null, null,
									"compteurUpdated", params);

						}
					}
				});
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
	 * Recherche d'une structure
	 * 
	 * @param searchValue Valeur à chercher
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
	@NotifyChange({ "dirty" })
	public void affectStructure() throws TechnicalException {

		selectedRecord.setStructure(selectedStructure);
		bandboxStructure.setValue(selectedStructure.getDesignation());
		bandboxStructure.close();
		getWindowManager().setDirtyOnCurrentPage(true);
	}

}
