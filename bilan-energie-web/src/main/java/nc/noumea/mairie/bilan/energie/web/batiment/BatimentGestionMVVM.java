package nc.noumea.mairie.bilan.energie.web.batiment;

import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.contract.dto.BatimentSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.InfrastructureSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.service.BatimentService;
import nc.noumea.mairie.bilan.energie.contract.service.InfrastructureService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.wm.PageExistException;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;
import nc.noumea.mairie.bilan.energie.web.wm.TabComposer;

import org.springframework.context.MessageSource;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 * MVVM de l'écran de gestion des batiments
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BatimentGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les batiments
	 */
	@WireVariable
	private BatimentService batimentService;

	@WireVariable
	private ParametrageService parametrageService;

	@WireVariable
	private InfrastructureService infrastructureService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille des batiments
	 */
	private BatimentSimple selectedItem;

	/**
	 * Liste des items des batiments
	 */
	private List<BatimentSimple> dataSet = null;

	/**
	 * Désignation recherchée
	 */
	@Wire
	private Textbox textboxDesignation;

	// Textbox du numéro de police
	@Wire
	private Textbox textboxNumPolice;

	// Textbox du numéro de compteur
	@Wire
	private Textbox textboxNumCompteur;

	// Textbox d'adresse
	@Wire
	private Textbox textboxAdresse;

	// Checkbox de l'affichage de l'historique
	@Wire
	private Checkbox checkboxHistorique;

	// Combobox des infrastructures
	@Wire
	private Combobox comboboxInfrastructure;

	/**
	 * @return selectedItem
	 */
	public BatimentSimple getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem
	 *            Item sélectionné
	 */
	public void setSelectedItem(BatimentSimple selectedItem) {
		this.selectedItem = selectedItem;
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
	 * Initialisation du vue-model
	 * 
	 * @param view
	 *            Composant View
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		// Recherche des batiments
		search();
	}

	/**
	 * Récupération de la liste des batiments
	 * 
	 * @return Liste de batiments
	 */
	public List<BatimentSimple> getDataSet() {
		return dataSet;
	}

	/**
	 * Retourne la taille de la pagination
	 * 
	 * @return pagination
	 * @throws TechnicalException
	 *             Exception technique
	 */
	public Long getPagination() throws TechnicalException {

		Parametrage parametrage;
		try {
			parametrage = parametrageService
					.getByParametre("BATIMENT_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Droit en modification ?
	 * 
	 * @return booléen
	 * @throws TechnicalException
	 *             Exception technique
	 */
	public boolean getDroitModification() throws TechnicalException {

		boolean droitModification = false;

		try {
			droitModification = (securityService.isAdministrateurBatiment() || securityService
					.isContributeurBatiment());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return droitModification;
	}

	/**
	 * Création d'un nouveau batiment
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		BatimentEditPageId id = new BatimentEditPageId();
		BatimentPageCreate pageInfo = new BatimentPageCreate();
		pageInfo.setPageId(id);

		TabComposer wm = getWindowManager();

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

	/**
	 * Recherche des batiments
	 * 
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	@GlobalCommand({ "batimentCreated", "batimentUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		String searchDesignation = "";
		if (textboxDesignation != null)
			searchDesignation = textboxDesignation.getValue().trim();

		String searchNumPolice = "";
		if (textboxNumPolice != null)
			searchNumPolice = textboxNumPolice.getValue().trim();

		String searchNumCompteur = "";
		if (textboxNumCompteur != null)
			searchNumCompteur = textboxNumCompteur.getValue().trim();

		String searchAdresse = "";
		if (textboxAdresse != null)
			searchAdresse = textboxAdresse.getValue().trim();

		Long searchIdInfrastructure = null;
		if (comboboxInfrastructure.getSelectedItem() != null
				&& comboboxInfrastructure.getSelectedItem().getValue() != null)
			searchIdInfrastructure = comboboxInfrastructure.getSelectedItem()
					.getValue();

		// Chargement de tous les batiments
		try {
			dataSet = batimentService.getAllByCriteres(searchDesignation,
					searchNumPolice, searchNumCompteur, searchAdresse,
					searchIdInfrastructure, checkboxHistorique.isChecked());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Si la recherche ne remonte qu'un seul Batiment, on ouvre la fenêtre
		// d'édition
		if (dataSet.size() == 1) {
			setSelectedItem(dataSet.get(0));
			edit(getSelectedItem());
		}

	}

	/**
	 * Retourne la liste des infrastructures
	 * 
	 * @return Liste des infrastructures
	 * @throws TechnicalException
	 *             Exception technique
	 */
	public List<InfrastructureSimple> getListeInfrastructure()
			throws TechnicalException {

		List<InfrastructureSimple> liste;
		try {
			liste = infrastructureService.getAllByDesignation("", false, false,
					true);
			InfrastructureSimple infrastructureNull = new InfrastructureSimple();
			infrastructureNull.setDesignation(" ");
			liste.add(0, infrastructureNull);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}

	/**
	 * Edition d'un batiment
	 * 
	 * @param batimentSimple
	 *            Batiment à éditer
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void edit(
			@BindingParam("batimentRecord") BatimentSimple batimentSimple)
			throws TechnicalException {

		Batiment batiment;

		// Chargement du batiment depuis la BDD
		try {
			batiment = batimentService.read(batimentSimple.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Ouverture d'une page avec le batiment
		BatimentEditPageId id = new BatimentEditPageId();
		id.setId(batiment.getId());

		BatimentPageEdit pageInfo = new BatimentPageEdit();
		pageInfo.setPageId(id);
		pageInfo.setBatiment(batiment);

		// Ouverture en consultation ou édition selon les droits de
		// l'utilisateur
		try {
			if (securityService.isAdministrateurBatiment()
					|| securityService.isContributeurBatiment())
				pageInfo.setRecordMode(RecordMode.EDIT);
			else
				pageInfo.setRecordMode(RecordMode.CONSULT);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage(), e);
		}

		pageInfo.setTitle(batiment.getDesignation());

		TabComposer wm = getWindowManager();

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

	/**
	 * Suppression d'un batiment
	 * 
	 * @param batimentSimple
	 *            Batiment à supprimer
	 * @throws TechnicalException
	 *             Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("batimentRecord") BatimentSimple batimentSimple)
			throws TechnicalException {

		this.selectedItem = batimentSimple;

		final Batiment batiment;

		// Chargement du batiment depuis la BDD
		try {
			batiment = batimentService.read(batimentSimple.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
		// Vérification si des polices sont rattachées au bâtiment
		if (batiment.getListePolice() != null && batiment.getListePolice().size() > 0) {
			
			// Polices rattachées --> Suppression interdite
			getWindowManager()
			.showError(
			messageSource
					.getMessage(
							"titre.suppressionimpossible",
							new Object[] {},
							Locale.FRANCE),
			messageSource
					.getMessage(
							"batiment.suppressioninterdite",
							new Object[] {},
							Locale.FRANCE));
		} else {
		
			// Message de confirmation de la suppression
			String str = messageSource.getMessage("batiment.suppression",
					new Object[] { batiment.getDesignation() }, Locale.FRANCE);
	
			Messagebox.show(str, messageSource.getMessage("titre.suppression",
					new Object[] {}, Locale.FRANCE), Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener<Event>() {
	
						public void onEvent(Event event) throws Exception {
	
							if (((Integer) event.getData()).intValue() == Messagebox.OK) {
	
								// Si clic OK, alors suppression définitive
								batimentService.delete(batiment);
	
								dataSet.remove(dataSet.indexOf(selectedItem));
								BindUtils.postNotifyChange(null, null,
										BatimentGestionMVVM.this, "dataSet");
	
							}
						}
					});
		}
	}
}
