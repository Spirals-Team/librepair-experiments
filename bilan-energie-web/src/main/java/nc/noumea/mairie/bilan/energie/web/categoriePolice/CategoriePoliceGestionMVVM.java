package nc.noumea.mairie.bilan.energie.web.categoriePolice;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.CategoriePolice;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.CategoriePoliceService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
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
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de gestion des catégories de police
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CategoriePoliceGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les catégories de police
	 */
	@WireVariable
	private CategoriePoliceService categoriePoliceService;

	@WireVariable
	private ParametrageService parametrageService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille
	 */
	private CategoriePolice selectedItem;

	/**
	 * Liste des items des catégories de police
	 */
	private List<CategoriePolice> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public CategoriePolice getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(CategoriePolice selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * After Compose
	 * 
	 * @param view Vue à afficher
	 * @throws TechnicalException Exception Technique
	 */
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		// Recherche des items
		search();
	}

	/**
	 * Récupération de la liste de catégories de police
	 * 
	 * @return Liste des paramétrages
	 */
	public List<CategoriePolice> getDataSet() {
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
					.getByParametre("CLIENT_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Création d'une nouvel catégorie de police
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/categoriePolice/categoriePoliceEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Rechercher des catégories de police
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "categoriePoliceCreated", "categoriePoliceUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement des données
		try {
			dataSet = categoriePoliceService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Consultation d'une catégorie de police
	 * 
	 * @param categoriePolice Catégorie de police à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("categoriePoliceRecord") CategoriePolice categoriePolice)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", categoriePolice);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/categoriePolice/categoriePoliceEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'une catégorie de police
	 * 
	 * @param categoriePolice Catégorie de police à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("categoriePoliceRecord") final CategoriePolice categoriePolice)
			throws TechnicalException {
		this.selectedItem = categoriePolice;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("categoriePolice.suppression",
				new Object[] { categoriePolice.getLibelle() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							try {
								categoriePoliceService.delete(categoriePolice);
							} catch (SuppressionImpossibleException e) {
								getWindowManager()
										.showError(
												messageSource
														.getMessage(
																"titre.suppressionimpossible",
																new Object[] {},
																Locale.FRANCE),
												messageSource
														.getMessage(
																"categoriePolice.suppressionimpossible",
																new Object[] {},
																Locale.FRANCE));
								return;
							}
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									CategoriePoliceGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
