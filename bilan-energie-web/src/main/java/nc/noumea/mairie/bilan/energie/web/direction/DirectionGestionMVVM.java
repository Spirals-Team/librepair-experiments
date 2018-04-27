package nc.noumea.mairie.bilan.energie.web.direction;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Direction;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.DirectionService;
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
 * MVVM de l'écran de gestion des directions
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DirectionGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les directions
	 */
	@WireVariable
	private DirectionService directionService;
	
	@WireVariable
	private ParametrageService parametrageService;
	
	@WireVariable
	private MessageSource messageSource;
	
	/**
	 * Item sélectionné dans la grille
	 */
	private Direction selectedItem;

	/**
	 * Liste des items des
	 */
	private List<Direction> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public Direction getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(Direction selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * afterCompose
	 *
	 * @param view Vue à afficher
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws TechnicalException{
    	
        Selectors.wireComponents(view, this, false);
        
		// Recherche des items
		search();
    }

	/**
	 * Récupération de la liste des directions
	 * 
	 * @return Liste des paramétrages
	 */
	public List<Direction> getDataSet() {
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
					.getByParametre("DIRECTION_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Création d'une nouvel direction
	 * 
 	 * @throws TechnicalException Exception technique
 	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/direction/directionEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Recherche des directions
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "directionCreated", "directionUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement des données
		try {
			dataSet = directionService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}


	/**
	 * Consultation d'une direction
	 * 
	 * @param direction Direction à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("directionRecord") Direction direction)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", direction);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/direction/directionEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un direction
	 * 
	 * @param direction Direction à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(@BindingParam("directionRecord") final Direction direction)
			throws TechnicalException {
		this.selectedItem = direction;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("direction.suppression", new Object[]{ direction.getNom()}, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression", new Object[]{}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							try {
								directionService.delete(direction);
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
														"direction.suppressionimpossible",
														new Object[] {},
														Locale.FRANCE));
								return;
							}
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									DirectionGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
