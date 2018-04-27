package nc.noumea.mairie.bilan.energie.web.typeZone;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.TypeZone;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.TypeZoneService;
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
 * MVVM de l'écran de gestion des types de zone
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TypeZoneGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les types de zone
	 */
	@WireVariable
	private TypeZoneService typeZoneService;

	@WireVariable
	private ParametrageService parametrageService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille
	 */
	private TypeZone selectedItem;

	/**
	 * Liste des items des
	 */
	private List<TypeZone> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public TypeZone getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(TypeZone selectedItem) {
		this.selectedItem = selectedItem;
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

		// Recherche des items
		search();
	}

	/**
	 * Récupération de la liste des types de zone
	 * 
	 * @return Liste des paramétrages
	 */
	public List<TypeZone> getDataSet() {
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
					.getByParametre("TYPE_ZONE_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Création d'un nouveau type de zone
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/typeZone/typeZoneEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Recherche des données
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "typeZoneCreated", "typeZoneUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement des données
		try {
			dataSet = typeZoneService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Consultation d'un nouveau type de zone
	 * 
	 * @param typeZone Type de zone à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("typeZoneRecord") TypeZone typeZone)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", typeZone);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/typeZone/typeZoneEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un type de zone
	 * 
	 * @param typeZone Type de zone à supprimer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("typeZoneRecord") final TypeZone typeZone)
			throws TechnicalException {
		this.selectedItem = typeZone;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("typezone.suppression",
				new Object[] { typeZone.getLibelle() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							try {
								typeZoneService.delete(typeZone);
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
														"typezone.suppressionimpossible",
														new Object[] {},
														Locale.FRANCE));
								return;
							}
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									TypeZoneGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
