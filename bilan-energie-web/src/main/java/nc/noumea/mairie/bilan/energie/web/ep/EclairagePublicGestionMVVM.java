package nc.noumea.mairie.bilan.energie.web.ep;

import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublicSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.InfrastructureSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
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
import org.zkoss.bind.annotation.Init;
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
 * MVVM de l'écran de gestion des éclairages publics
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EclairagePublicGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur l'éclairage public
	 */
	@WireVariable
	private EclairagePublicService eclairagePublicService;

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
	 * Item sélectionné dans la grille des éclairage publique
	 */
	private EclairagePublicSimple selectedItem;

	/**
	 * Liste des items des éclairage public
	 */
	private List<EclairagePublicSimple> dataSet = null;

	// Textbox du numéro de poste
	@Wire
	private Textbox textboxNumPoste;

	// Textbox du nom de poste
	@Wire
	private Textbox textboxNomPoste;

	// Textbox du numéro de police
	@Wire
	private Textbox textboxNumPolice;

	// Textbox du numéro de compteur
	@Wire
	private Textbox textboxNumCompteur;

	// Textbox d'adresse du compteur
	@Wire
	private Textbox textboxAdresseCompteur;

	// Textbox du numéro de support
	@Wire
	private Textbox textboxNumSupport;

	// Checkbox de l'affichage de l'historique
	@Wire
	private Checkbox checkboxHistorique;

	// Combobox des infrastructures
	@Wire
	private Combobox comboboxInfrastructure;

	/**
	 * @return selectedItem
	 */
	public EclairagePublicSimple getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(EclairagePublicSimple selectedItem) {
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
	 * @throws TechnicalException Exception technique
	 */
	@Init
	public void init() throws TechnicalException {

		// Chargement de tous les éclairages publics
		try {
			dataSet = eclairagePublicService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
	}

	/**
	 * afterCompose
	 *
	 * @param view Vue à afficher
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
			throws TechnicalException {
		Selectors.wireComponents(view, this, false);

		// Recherche des polices
		search();
	}

	/**
	 * Récupération de la liste des éclairages publics
	 * 
	 * @return Liste d'éclairage public
	 */
	public List<EclairagePublicSimple> getDataSet() {
		return dataSet;
	}

	/**
	 * Retourne la taille de la pagination
	 * 
	 * @return pagination
     * @throws TechnicalException Exception technique
	 */
	public Long getPagination() throws TechnicalException {

		Parametrage parametrage;
		try {
			parametrage = parametrageService
					.getByParametre("EP_LST_PAGINATION");
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
     * @throws TechnicalException Exception technique
	 */
	public boolean getDroitModification() throws TechnicalException  {
		
		boolean droitModification = false ;
		
		try {
			droitModification = (securityService.isAdministrateurEP() || securityService.isContributeurEP());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
		return droitModification;
	}
	
	/**
	 * Création d'un nouvel éclairage public
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		EclairagePublicEditPageId id = new EclairagePublicEditPageId();
		EclairagePublicPageCreate pageInfo = new EclairagePublicPageCreate();
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
	 * Recherche des EPs
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "epCreated", "epUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		String searchNumPoste = "";
		if (textboxNumPoste != null)
			searchNumPoste = textboxNumPoste.getValue().trim();

		String searchNomPoste = "";
		if (textboxNomPoste != null)
			searchNomPoste = textboxNomPoste.getValue().trim();

		String searchNumPolice = "";
		if (textboxNumPolice != null)
			searchNumPolice = textboxNumPolice.getValue().trim();

		String searchNumCompteur = "";
		if (textboxNumCompteur != null)
			searchNumCompteur = textboxNumCompteur.getValue().trim();

		String searchAdresseCompteur = "";
		if (textboxAdresseCompteur != null)
			searchAdresseCompteur = textboxAdresseCompteur.getValue().trim();

		String searchNumSupport = "";
		if (textboxNumSupport != null)
			searchNumSupport = textboxNumSupport.getValue().trim();

		Long searchIdInfrastructure = null;
		if (comboboxInfrastructure.getSelectedItem() != null
				&& comboboxInfrastructure.getSelectedItem().getValue() != null)
			searchIdInfrastructure = comboboxInfrastructure.getSelectedItem()
					.getValue();

		// Chargement de toutes les éclairages publics
		try {
			dataSet = eclairagePublicService.getAllByCriteres(searchNumPoste,
					searchNomPoste, searchNumPolice, searchNumCompteur,
					searchAdresseCompteur, searchNumSupport,
					searchIdInfrastructure, checkboxHistorique.isChecked());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Si la recherche ne remonte qu'un seul EP, on ouvre la fenêtre
		// d'édition
		if (dataSet.size() == 1) {
			setSelectedItem(dataSet.get(0));
			edit(getSelectedItem(),searchNumSupport.toUpperCase());
		}
	}

	/**
	 * Retourne la liste des infrastructures
	 * 
	 * @return Liste des infrastructures
     * @throws TechnicalException Exception technique
	 */
	public List<InfrastructureSimple> getListeInfrastructure()
			throws TechnicalException {

		List<InfrastructureSimple> liste;
		try {
			liste = infrastructureService.getAllByDesignation("", false, true,
					false);
			InfrastructureSimple infrastructureNull = new InfrastructureSimple();
			infrastructureNull.setDesignation(" ");
			liste.add(0, infrastructureNull);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return liste;
	}

	/**
	 * Edition d'un éclairage public
	 * 
	 * @param eclairagePublicSimple EP à éditer
	 * @param supportNumInventaire Num d'inventaire du support à sélectionner
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(
			@BindingParam("eclairagePublicRecord") EclairagePublicSimple eclairagePublicSimple,
			@BindingParam("supportNumInventaire") String supportNumInventaire)
			throws TechnicalException {
		
		EclairagePublic eclairagePublic;

		// Chargement de l'EP depuis la BDD
		try {
			eclairagePublic = eclairagePublicService.read(eclairagePublicSimple
					.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Ouverture d'une page avec l'EP
		EclairagePublicEditPageId id = new EclairagePublicEditPageId();
		id.setId(eclairagePublic.getId());

		EclairagePublicPageEdit pageInfo = new EclairagePublicPageEdit();
		pageInfo.setPageId(id);
		pageInfo.setEclairagePublic(eclairagePublic);
		pageInfo.setSupportNumInventaire(supportNumInventaire);
		
		// Ouverture en consultation ou édition selon les droits de
		// l'utilisateur
		try {
			if (securityService.isAdministrateurEP()
					|| securityService.isContributeurEP())
				pageInfo.setRecordMode(RecordMode.EDIT);
			else
				pageInfo.setRecordMode(RecordMode.CONSULT);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage(), e);
		}

		pageInfo.setTitle(eclairagePublic.getDesignation());

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
	 * Suppression d'un éclairage public
	 * 
	 * @param eclairagePublicSimple EP à supprimer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("eclairagePublicRecord") EclairagePublicSimple eclairagePublicSimple)
			throws TechnicalException {
		this.selectedItem = eclairagePublicSimple;

		final EclairagePublic eclairagePublic;

		// Chargement de l'EP depuis la BDD
		try {
			eclairagePublic = eclairagePublicService.read(eclairagePublicSimple
					.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
		// Vérification si des polices sont rattachées à l'EP
		if (eclairagePublic.getListePolice() != null && eclairagePublic.getListePolice().size() > 0) {
			
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
							"ep.suppressioninterdite",
							new Object[] {},
							Locale.FRANCE));
		} else {

			// Message de confirmation de la suppression
			String str = messageSource.getMessage("ep.suppression", new Object[]{ eclairagePublic.getDesignation()}, Locale.FRANCE);
			
			Messagebox.show(str, messageSource.getMessage("titre.suppression", new Object[]{}, Locale.FRANCE), Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener<Event>() {
	
						public void onEvent(Event event) throws Exception {
	
							if (((Integer) event.getData()).intValue() == Messagebox.OK) {
	
								// Si clic OK, alors suppression définitive
								eclairagePublicService.delete(eclairagePublic);
								dataSet.remove(dataSet.indexOf(selectedItem));
								BindUtils.postNotifyChange(null, null,
										EclairagePublicGestionMVVM.this, "dataSet");
	
							}
						}
					});
		}
	}

}
