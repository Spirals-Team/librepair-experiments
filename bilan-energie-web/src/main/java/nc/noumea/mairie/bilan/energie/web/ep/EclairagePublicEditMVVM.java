package nc.noumea.mairie.bilan.energie.web.ep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.Luminaire;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.Support;
import nc.noumea.mairie.bilan.energie.contract.service.ConversionService;
import nc.noumea.mairie.bilan.energie.contract.service.DirectionService;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
import nc.noumea.mairie.bilan.energie.contract.service.InfrastructureService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeZoneService;
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

import org.springframework.context.MessageSource;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de création / édition / consultation d'un éclairage public
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EclairagePublicEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private EclairagePublicService eclairagePublicService;

	@WireVariable
	private ConversionService conversionService;

	@WireVariable
	private InfrastructureService infrastructureService;

	@WireVariable
	private DirectionService directionService;

	@WireVariable
	private PoliceService policeService;

	@WireVariable
	private TypeZoneService typeZoneService;
	
	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private MessageSource messageSource;

	/** Eclairage Public en cours d'édition */
	private EclairagePublic selectedRecord;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Support sélectionné dans la listbox */
	private Support selectedSupport;

	/** Luminaire sélectionné dans la listbox */
	private Luminaire selectedLuminaire;

	/** Police sélectionnée dans la listbox */
	private Police selectedPolice;

	/** Checkbox AffichageHistorique */
	@Wire
	private Checkbox afficheHistorique;

	/** Listbox des supports */
	@Wire
	private Listbox listboxSupport;

	/** Listbox des luminaires */
	@Wire
	private Listbox listboxLuminaire;

	/** Liste des Supports à afficher */
	private List<Support> listeSupport;

	/** Liste des Luminaire à afficher */
	private List<Luminaire> listeLuminaire;

	/**
	 * @return the selectedRecord
	 */
	public EclairagePublic getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(EclairagePublic selectedRecord) {
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
	 * Nouveau ?
	 * 
	 * @return booléen
	 */
	public boolean isNouveau() {

		return recordMode.equals(RecordMode.NEW);
	}
	
	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the selectedSupport
	 */
	public Support getSelectedSupport() {
		return selectedSupport;
	}

	/**
	 * @param selectedSupport
	 *            the selectedSupport to set
	 */
	@NotifyChange("listeLuminaire")
	public void setSelectedSupport(Support selectedSupport) {
		this.selectedSupport = selectedSupport;
	}

	/**
	 * @return the selectedLuminaire
	 */
	public Luminaire getSelectedLuminaire() {
		return selectedLuminaire;
	}

	/**
	 * @param selectedLuminaire
	 *            the selectedLuminaire to set
	 */
	public void setSelectedLuminaire(Luminaire selectedLuminaire) {
		this.selectedLuminaire = selectedLuminaire;
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
	public void setSelectedPolice(Police selectedPolice) {
		this.selectedPolice = selectedPolice;
	}

	/**
	 * Retourne la liste des supports à afficher en fonction de la checkbox
	 * 'Historique'
	 * 
	 * @return Liste des supports à afficher
	 */
	public List<Support> getListeSupport() {

		if (afficheHistorique.isChecked())
			listeSupport = selectedRecord.getListeSupport();
		else {
			List<Support> listeRetour = new ArrayList<Support>();

			for (Support support : selectedRecord.getListeSupport())
				if (support.getDateFin() == null)
					listeRetour.add(support);
			listeSupport = listeRetour;
		}

		Collections.sort(listeSupport, new Comparator<Support>() {
			public int compare(Support m1, Support m2) {
				return m1.getNumInventaire().compareTo(m2.getNumInventaire());
			}
		});
		return listeSupport;
	}

	/**
	 * @param listeSupport
	 *            the listeSupport to set
	 */
	public void setListeSupport(List<Support> listeSupport) {
		this.listeSupport = listeSupport;
	}

	/**
	 * Retourne la liste des luminaires à afficher en fonction de la checkbox
	 * 'Historique' et du support sélectionné
	 * 
	 * @return Liste des luminaires à afficher
	 */
	public List<Luminaire> getListeLuminaire() {

		List<Luminaire> listeRetour = new ArrayList<Luminaire>();

		if (selectedSupport != null)
			if (afficheHistorique.isChecked())
				listeLuminaire = selectedSupport.getListeLuminaire();
			else {

				for (Luminaire luminaire : selectedSupport.getListeLuminaire())
					if (luminaire.getDateFin() == null)
						listeRetour.add(luminaire);
				listeLuminaire = listeRetour;
			}
		else
			listeLuminaire = listeRetour;

		return listeLuminaire;
	}

	/**
	 * @param listeLuminaire
	 *            the listeLuminaire to set
	 */
	public void setListeLuminaire(List<Luminaire> listeLuminaire) {
		this.listeLuminaire = listeLuminaire;
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
	 * Retourne la liste des infrastructures
	 * 
	 * @return Liste des infrastructures
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeInfrastructure() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = infrastructureService.getAllReferentielByCriteres(true, false);
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
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}
	
	/**
	 * Retourne la liste des types de zone
	 * 
	 * @return Liste des types de zone
     * @throws TechnicalException Exception technique
	 */
	public List<CodeLabel> getListeTypeZone() throws TechnicalException {

		List<CodeLabel> liste;
		try {
			liste = typeZoneService.getAllReferentiel();
			CodeLabel codeLabel = new CodeLabel();
			codeLabel.setLabel(" ");
			liste.add(0,codeLabel);
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
	 * @param supportNumInventaire Num d'inventaire à sélectionner
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") EclairagePublic eclairagePublic,
			@ExecutionArgParam("supportNumInventaire") String supportNumInventaire,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);

		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedRecord = new EclairagePublic();
			this.selectedRecord.setConversion(new CodeLabel());
			this.selectedRecord.setInfrastructure(new CodeLabel());
			this.selectedRecord.setDirection(new CodeLabel());
			this.selectedRecord.setListeSupport(new ArrayList<Support>());
			this.selectedRecord.setTypeZone(new CodeLabel());

		}

		if (recordMode.equals(RecordMode.EDIT)) {
			this.selectedRecord = eclairagePublic;
			if (this.selectedRecord.getTypeZone() == null)
				this.selectedRecord.setTypeZone(new CodeLabel());
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = eclairagePublic;
		}

		// Préselection du support exact ou de celui le plus proche
		if (supportNumInventaire != null && !supportNumInventaire.isEmpty()) {
			int matchNumInventaire = 0;
			Support supportToSelect = null;
			boolean exactMatch = false;

			for (Support support : this.selectedRecord.getListeSupport()) {
				if (support.getNumInventaire().equals(supportNumInventaire)) {
					supportToSelect = support;
					exactMatch = true;
				} else if (support.getNumInventaire().contains(supportNumInventaire)) {
					matchNumInventaire++;
					supportToSelect = support;
				}
					
				if (exactMatch || matchNumInventaire > 1)
					break;
			}
			if (exactMatch || matchNumInventaire == 1)
				this.selectedSupport = supportToSelect;
		}
	}

	/**
	 * Sauvegarde d'un éclairage public
	 * 
	 * @param close Booléen de fermeture de l'onglet
	 * @param pageId Identifiant de la page
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange({"selectedRecord","nouveau"})
	public void save(@BindingParam("close") boolean close,
			@BindingParam("pageId") PageId pageId) throws TechnicalException {

		// Majuscule sur le n° de poste
		if (this.selectedRecord.getNoPoste() != null)
			this.selectedRecord.setNoPoste(this.selectedRecord.getNoPoste()
					.toUpperCase());

		// Majuscule sur le nom de poste
		if (this.selectedRecord.getNomPoste() != null)
			this.selectedRecord.setNomPoste(this.selectedRecord.getNomPoste()
					.toUpperCase());
		
		// Traitement du type de zone
		if (this.selectedRecord.getTypeZone().getCode() == null)
			this.selectedRecord.setTypeZone(null);
		

		try {
			if (recordMode.equals(RecordMode.NEW)) {
				this.selectedRecord = eclairagePublicService
						.create(this.selectedRecord);
				setRecordMode(RecordMode.EDIT);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("ep", selectedRecord);
				BindUtils.postGlobalCommand(null, null, "epCreated", params);
			} else if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = eclairagePublicService
						.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("ep", selectedRecord);
				BindUtils.postGlobalCommand(null, null, "epUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		if (this.selectedRecord.getTypeZone() == null)
			this.selectedRecord.setTypeZone(new CodeLabel());
		
		getWindowManager().setDirtyOnCurrentPage(false);
		getWindowManager().showNotification(
				new Notification("L'éclairage public a été enregistré"));
		if (close)
			try {
				getWindowManager().closePage(pageId);
			} catch (PageNotExistException e) {
				throw new WebTechnicalException(e.getMessage());
			}

	}

	/**
	 * dirty ?
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
	 * @param eclairagePublic EP à mettre à jour
	 * @param support Support à mettre à jour
	 * @param luminaire Luminaire à mettre à jour
     * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "supportUpdated", "luminaireUpdated" })
	@NotifyChange({ "listeSupport", "listeLuminaire", "dirty" })
	public void miseAJour(
			@BindingParam("eclairagePublic") EclairagePublic eclairagePublic,
			@BindingParam("support") Support support,
			@BindingParam("luminaire") Luminaire luminaire)
			throws TechnicalException {

		if (eclairagePublic.getId() == selectedRecord.getId()) {

			getWindowManager().setDirtyOnCurrentPage(true);

			if (support != null)
				setSelectedSupport(support);

			if (luminaire != null)
				setSelectedLuminaire(luminaire);
		}
	}

	/**
	 * Rafraîchissement des listes
	 * 
	 */
	@Command
	@NotifyChange({ "listeSupport", "listeLuminaire" })
	public void refreshListe() {

	}

	/**
	 * Gestion du double-clic sur la liste des supports
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void listSupportDoubleClicked() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedEP", selectedRecord);
		map.put("selectedRecord", selectedSupport);
		map.put("recordMode", RecordMode.CONSULT);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/ep/supportEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Création d'un support
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void createSupport() throws TechnicalException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedEP", selectedRecord);
		map.put("recordMode", RecordMode.NEW);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/ep/supportEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un support
	 * 
	 * @param support Support à supprimer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void deleteSupport(
			@BindingParam("supportRecord") final Support support)
			throws TechnicalException {
		this.selectedSupport = support;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("support.suppression",
				new Object[] { support.getNumInventaire() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							selectedRecord.getListeSupport().remove(support);
							listeSupport.remove(support);
							selectedSupport = null;
							Map<String, Object> params = new Hashtable<String, Object>();
							params.put("eclairagePublic", selectedRecord);
							BindUtils.postGlobalCommand(null, null,
									"supportUpdated", params);

						}
					}
				});
	}

	/**
	 * Gestion du double-clic sur la liste des luminaires
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void listLuminaireDoubleClicked() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedEP", selectedRecord);
		map.put("selectedSupport", selectedSupport);
		map.put("selectedRecord", selectedLuminaire);
		map.put("recordMode", RecordMode.CONSULT);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/ep/luminaireEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Création d'un luminaire
	 * 
	 * @param support Support à affecter au luminaire
     * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange("listeAdresse")
	public void createLuminaire(@BindingParam("supportRecord") Support support)
			throws TechnicalException {

		this.selectedSupport = support;

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedEP", selectedRecord);
		map.put("selectedSupport", selectedSupport);
		map.put("recordMode", RecordMode.NEW);
		map.put("readOnly", readOnly);

		Window window = (Window) Executions.createComponents(
				"zul/ep/luminaireEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un luminaire
	 * 
	 * @param luminaire Luminaire à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void deleteLuminaire(
			@BindingParam("luminaireRecord") final Luminaire luminaire)
			throws TechnicalException {
		this.selectedLuminaire = luminaire;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("luminaire.suppression",
				new Object[] {}, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							selectedRecord
									.getListeSupport()
									.get(selectedRecord.getListeSupport()
											.indexOf(selectedSupport))
									.getListeLuminaire().remove(luminaire);
							listeSupport
									.get(selectedRecord.getListeSupport()
											.indexOf(selectedSupport))
									.getListeLuminaire().remove(luminaire);
							selectedSupport.getListeLuminaire().remove(
									luminaire);
							listeLuminaire.remove(luminaire);
							setSelectedLuminaire(null);

							Map<String, Object> params = new Hashtable<String, Object>();
							params.put("eclairagePublic", selectedRecord);
							params.put("support", selectedSupport);
							BindUtils.postGlobalCommand(null, null,
									"supportUpdated", params);

						}
					}
				});

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
			if (securityService.isAdministrateurEP()
					|| securityService.isContributeurEP()) {
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
