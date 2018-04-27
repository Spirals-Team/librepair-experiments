package nc.noumea.mairie.bilan.energie.web.client;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Client;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.exceptions.SuppressionImpossibleException;
import nc.noumea.mairie.bilan.energie.contract.service.ClientService;
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
 * MVVM de l'écran de gestion des clients
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ClientGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les clients
	 */
	@WireVariable
	private ClientService clientService;

	@WireVariable
	private ParametrageService parametrageService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille
	 */
	private Client selectedItem;

	/**
	 * Liste des items des clients
	 */
	private List<Client> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public Client getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(Client selectedItem) {
		this.selectedItem = selectedItem;
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
	 * Récupération de la liste de clients
	 * 
	 * @return Liste des paramétrages
	 */
	public List<Client> getDataSet() {
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
	 * Création d'un nouveau client
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/client/clientEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Recherche des clients
	 * 
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "clientCreated", "clientUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement des données
		try {
			dataSet = clientService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Consultation d'un client
	 * 
	 * @param client Client à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("clientRecord") Client client)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", client);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/client/clientEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un client
	 * 
	 * @param client Client à supprimer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("clientRecord") final Client client)
			throws TechnicalException {
		this.selectedItem = client;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("client.suppression",
				new Object[] { client.getLibelle() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							try {
								clientService.delete(client);
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
																"client.suppressionimpossible",
																new Object[] {},
																Locale.FRANCE));
								return;
							}
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									ClientGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
