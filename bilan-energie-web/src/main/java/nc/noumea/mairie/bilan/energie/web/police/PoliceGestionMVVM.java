package nc.noumea.mairie.bilan.energie.web.police;

import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.PoliceSimple;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.contract.service.PoliceService;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 * MVVM de l'écran de gestion des polices
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PoliceGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les polices
	 */
	@WireVariable
	private PoliceService policeService;
	
	@WireVariable
	private ParametrageService parametrageService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille des polices
	 */
	private PoliceSimple selectedItem;

	/**
	 * Liste des items des polices
	 */
	private List<PoliceSimple> dataSet = null;

	/**
	 * Numéro de police recherché
	 */
	@Wire("#textboxNumeroPolice")
	private Textbox textboxNumeroPolice;

	@Wire("#checkboxEP")
	Checkbox checkboxEP;

	@Wire("#checkboxBatiment")
	Checkbox checkboxBatiment;

	@Wire("#checkboxHistorique")
	Checkbox checkboxHistorique;

	/**
	 * @return selectedItem
	 */
	public PoliceSimple getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(PoliceSimple selectedItem) {
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

		try {
			checkboxEP.setChecked(securityService.isUtilisateurEP());
			checkboxBatiment
					.setChecked(securityService.isUtilisateurBatiment());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Recherche des polices
		search();
	}

	/**
	 * Récupération de la liste des polices
	 * 
	 * @return Liste des polices
	 */
	public List<PoliceSimple> getDataSet() {
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
					.getByParametre("POLICE_LST_PAGINATION");
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
	 * Création d'une nouvelle police
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		PoliceEditPageId id = new PoliceEditPageId();
		PolicePageCreate pageInfo = new PolicePageCreate();
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
	 * Recherche des polices
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "policeCreated", "policeUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		String searchNumeroPolice;
		if (textboxNumeroPolice == null)
			searchNumeroPolice = "";
		else
			searchNumeroPolice = textboxNumeroPolice.getValue();

		searchNumeroPolice = searchNumeroPolice.trim();

		// Chargement de toutes les polices
		try {
			dataSet = policeService.getAllByNumeroPolice(searchNumeroPolice,
					checkboxHistorique.isChecked(), checkboxEP.isChecked(),
					checkboxBatiment.isChecked());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Si la recherche ne remonte qu'une seule police, on ouvre la fenêtre
		// d'édition
		if (dataSet.size() == 1) {
			setSelectedItem(dataSet.get(0));
			edit(getSelectedItem());
		}
	}

	/**
	 * Edition d'une police
	 * 
	 * @param policeSimple Police à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("policeRecord") PoliceSimple policeSimple)
			throws TechnicalException {

		Police police;

		// Chargement de la police depuis la BDD
		try {
			police = policeService.read(policeSimple.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Ouverture d'une page avec la police
		PoliceEditPageId id = new PoliceEditPageId();
		id.setId(police.getId());

		PolicePageEdit pageInfo = new PolicePageEdit();
		pageInfo.setPageId(id);
		pageInfo.setPolice(police);

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
		pageInfo.setTitle(police.getNumPolice());

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
	 * Suppression d'une police
	 * 
	 * @param policeSimple Police à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(@BindingParam("policeRecord") PoliceSimple policeSimple)
			throws TechnicalException {
		this.selectedItem = policeSimple;

		final Police police;

		// Chargement de la police depuis la BDD
		try {
			police = policeService.read(policeSimple.getId());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		// Vérification si des compteurs sont rattachés à la police
		if (police.getListeCompteur() != null && police.getListeCompteur().size() > 0) {
			
			// Compteurs rattachés --> Suppression interdite
			getWindowManager()
			.showError(
			messageSource
					.getMessage(
							"titre.suppressionimpossible",
							new Object[] {},
							Locale.FRANCE),
			messageSource
					.getMessage(
							"police.suppressioninterdite",
							new Object[] {},
							Locale.FRANCE));
		} else {
			// Message de confirmation de la suppression
			String str = messageSource.getMessage("police.suppression",
					new Object[] { police.getNumPolice() }, Locale.FRANCE);
	
			Messagebox.show(str, messageSource.getMessage("titre.suppression",
					new Object[] {}, Locale.FRANCE), Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener<Event>() {
	
						public void onEvent(Event event) throws Exception {
	
							if (((Integer) event.getData()).intValue() == Messagebox.OK) {
	
								// Si clic OK, alors suppression définitive
								policeService.delete(police);
								dataSet.remove(dataSet.indexOf(selectedItem));
								BindUtils.postNotifyChange(null, null,
										PoliceGestionMVVM.this, "dataSet");
	
							}
						}
					});
		}
	}

}
