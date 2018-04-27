package nc.noumea.mairie.bilan.energie.web.codeCtme;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeCtme;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.CodeCtmeService;
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
 * MVVM de l'écran de gestion des codes CTME
 * 
 * @author Greg Dujardin
 */
/**
 * @author Greg Dujardin
 *
 */
/**
 * @author Greg Dujardin
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CodeCtmeGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les codes CTME
	 */
	@WireVariable
	private CodeCtmeService codeCtmeService;

	@WireVariable
	private ParametrageService parametrageService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille
	 */
	private CodeCtme selectedItem;

	/**
	 * Liste des items des
	 */
	private List<CodeCtme> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public CodeCtme getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(CodeCtme selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * AfterCompose
	 * 
	 * @param view Vue à afficher
	 * @throws TechnicalException Exception à afficher
	 */
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);

		// Recherche des items
		search();
	}

	/**
	 * Récupération de la liste des codes CTME
	 * 
	 * @return Liste des paramétrages
	 */
	public List<CodeCtme> getDataSet() {
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
					.getByParametre("CODE_CTME_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Création d'un nouveau code CTME
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/codeCtme/codeCtmeEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Recherche des codes CTME
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "codeCtmeCreated", "codeCtmeUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement des données
		try {
			dataSet = codeCtmeService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Consultation d'un nouveau code CTME
	 * 
	 * @param codeCtme Code CTME à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("codeCtmeRecord") CodeCtme codeCtme)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", codeCtme);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/codeCtme/codeCtmeEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un code CTME
	 * 
	 * @param codeCtme Code CTME à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("codeCtmeRecord") final CodeCtme codeCtme)
			throws TechnicalException {
		this.selectedItem = codeCtme;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("codectme.suppression",
				new Object[] { codeCtme.getLibelle() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							try {
								codeCtmeService.delete(codeCtme);
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
														"codectme.suppressionimpossible",
														new Object[] {},
														Locale.FRANCE));
								return;
							}
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									CodeCtmeGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
