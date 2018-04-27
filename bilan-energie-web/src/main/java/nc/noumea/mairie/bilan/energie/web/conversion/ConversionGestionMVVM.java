package nc.noumea.mairie.bilan.energie.web.conversion;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Conversion;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.ConversionService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
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
 * MVVM de l'écran de gestion des taux de conversion
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConversionGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les conversions
	 */
	@WireVariable
	private ConversionService conversionService;

	@WireVariable
	private ParametrageService parametrageService;

	// Service de sécurité
	@WireVariable
	private SecurityService securityService;

	@WireVariable
	private MessageSource messageSource;
	
	/**
	 * Item sélectionné dans la grille
	 */
	private Conversion selectedItem;

	/**
	 * Liste des items des
	 */
	private List<Conversion> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public Conversion getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(Conversion selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	/**
	 * Retourne le service de sécurité
	 * 
	 * @return service de sécurité
	 */
	public SecurityService getSecurityService() {
		return securityService;
	}

	/**
	 * AfterCompose
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
	 * Récupération de la liste des conversion
	 * 
	 * @return Liste des paramétrages
	 */
	public List<Conversion> getDataSet() {
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
					.getByParametre("CONVERSION_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Création d'un nouveau taux de conversion
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/conversion/conversionEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Recherche des taux de conversion
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "conversionCreated", "conversionUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement des données
		try {
			dataSet = conversionService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Consultation d'un taux de conversion
	 * 
	 * @param conversion Conversion à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("conversionRecord") Conversion conversion)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", conversion);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/conversion/conversionEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un conversion
	 * 
	 * @param conversion Conversion à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("conversionRecord") final Conversion conversion)
			throws TechnicalException {
		this.selectedItem = conversion;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("tauxconversion.suppression",
				new Object[] { conversion.getUniteGes() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							try {
								conversionService.delete(conversion);
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
																"tauxconversion.suppressionimpossible",
																new Object[] {},
																Locale.FRANCE));
								return;
							}
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									ConversionGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
