package nc.noumea.mairie.bilan.energie.web.infrastructure;

import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Infrastructure;
import nc.noumea.mairie.bilan.energie.contract.dto.InfrastructureSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 * MVVM de l'écran de gestion des infrastructures
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InfrastructureGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les infrastructures
	 */
	@WireVariable
	private InfrastructureService infrastructureService;

	@WireVariable
	private ParametrageService parametrageService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille des infrastructures
	 */
	private InfrastructureSimple selectedItem;

	/**
	 * Liste des items des infrastructures
	 */
	private List<InfrastructureSimple> dataSet = null;

	/**
	 * Désignation recherchée
	 */
	@Wire("#textboxDesignation")
	private Textbox textboxDesignation;

	@Wire("#checkboxEP")
	Checkbox checkboxEP;

	@Wire("#checkboxBatiment")
	Checkbox checkboxBatiment;

	@Wire("#checkboxHistorique")
	Checkbox checkboxHistorique;

	/**
	 * @return selectedItem
	 */
	public InfrastructureSimple getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(InfrastructureSimple selectedItem) {
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
	 * @param view Composant view
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		try {
			checkboxEP.setChecked(securityService.isUtilisateurEP());
			checkboxBatiment
					.setChecked(securityService.isUtilisateurBatiment());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Recherche des infrastructures
		search();
	}

	/**
	 * Récupération de la liste des infrastructures
	 * 
	 * @return Liste d'éclairage public
	 */
	public List<InfrastructureSimple> getDataSet() {
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
					.getByParametre("INFRASTRUCTURE_LST_PAGINATION");
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
	public boolean getDroitModification() throws TechnicalException {

		boolean droitModification = false;

		try {
			droitModification = (securityService.isAdministrateurEP()
					|| securityService.isContributeurEP()
					|| securityService.isAdministrateurBatiment() || securityService
					.isContributeurBatiment());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		return droitModification;
	}

	/**
	 * Création d'une nouvelle infrastructure
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		InfrastructureEditPageId id = new InfrastructureEditPageId();
		InfrastructurePageCreate pageInfo = new InfrastructurePageCreate();
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
	 * Recherche des infrastructures
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "infrastructureCreated", "infrastructureUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		String searchDesignation;
		if (textboxDesignation == null)
			searchDesignation = "";
		else
			searchDesignation = textboxDesignation.getValue();

		searchDesignation = searchDesignation.trim();

		// Chargement de toutes les infrastructures
		try {
			dataSet = infrastructureService.getAllByDesignation(
					searchDesignation, checkboxHistorique.isChecked(),
					checkboxEP.isChecked(), checkboxBatiment.isChecked());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
	}

	/**
	 * Edition d'une infrastructures
	 * 
	 * @param infrastructureSimple Infrastructure à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(
			@BindingParam("infrastructureRecord") InfrastructureSimple infrastructureSimple)
			throws TechnicalException {

		Infrastructure infrastructure;

		// Chargement de l'infrastructure depuis la BDD
		try {
			infrastructure = infrastructureService.read(infrastructureSimple
					.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Ouverture d'une page avec l'infrastructure
		InfrastructureEditPageId id = new InfrastructureEditPageId();
		id.setId(infrastructure.getId());

		InfrastructurePageEdit pageInfo = new InfrastructurePageEdit();
		pageInfo.setPageId(id);
		pageInfo.setInfrastructure(infrastructure);

		// Ouverture en consultation ou édition selon les droits de
		// l'utilisateur
		try {
			if (securityService.isAdministrateurEP()
					|| securityService.isContributeurEP()
					|| securityService.isAdministrateurBatiment()
					|| securityService.isContributeurBatiment())
				pageInfo.setRecordMode(RecordMode.EDIT);
			else
				pageInfo.setRecordMode(RecordMode.CONSULT);
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage(), e);
		}

		pageInfo.setTitle(infrastructure.getDesignation());

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
	 * Suppression d'une infrastructure
	 * 
	 * @param infrastructureSimple Infrastructure à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("infrastructureRecord") InfrastructureSimple infrastructureSimple)
			throws TechnicalException {

		this.selectedItem = infrastructureSimple;

		final Infrastructure infrastructure;

		// Chargement de l'infrastructure depuis la BDD
		try {
			infrastructure = infrastructureService.read(infrastructureSimple
					.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Si des structures sont rattachées à l'infra, message d'erreur
		if (infrastructure.getListeStructure().size() > 0) {
			getWindowManager().showError(
					messageSource.getMessage("titre.suppressionimpossible",
							new Object[] {}, Locale.FRANCE),
					messageSource.getMessage(
							"infrastructure.suppressionimpossible",
							new Object[] {}, Locale.FRANCE));
			return;
		}

		// Message de confirmation de la suppression
		String str = messageSource
				.getMessage("infrastructure.suppression",
						new Object[] { infrastructure.getDesignation() },
						Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							infrastructureService.delete(infrastructure);

							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									InfrastructureGestionMVVM.this, "dataSet");

						}
					}
				});
	}
}
