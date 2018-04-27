package nc.noumea.mairie.bilan.energie.web.utilisateur;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * MVVM de l'écran de gestion des utilisateurs
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UtilisateurGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les utilisateurs
	 */
	@WireVariable
	private UtilisateurService utilisateurService;

	@WireVariable
	private ParametrageService parametrageService;

	@WireVariable
	private MessageSource messageSource;

	/**
	 * Item sélectionné dans la grille des utilisateurs
	 */
	private Utilisateur selectedItem;

	/**
	 * Liste des items des utilisateurs
	 */
	private List<Utilisateur> dataSet = null;

	/**
	 * Nom ou login de l'utilisateur recherché
	 */
	@Wire("#textboxNom")
	private Textbox textboxNom;

	@Wire("#checkboxHistorique")
	private Checkbox checkboxHistorique;

	/**
	 * @return selectedItem
	 */
	public Utilisateur getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(Utilisateur selectedItem) {
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

		// Recherche des utilisateurs
		search();
	}

	/**
	 * Récupération de la liste des utilisateurs
	 * 
	 * @return Liste des utilisateurs
	 */
	public List<Utilisateur> getDataSet() {
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
					.getByParametre("UTILISATEUR_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}

	/**
	 * Création d'un nouvel utilisateur
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void create() throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("recordMode", RecordMode.NEW);

		Window window = (Window) Executions.createComponents(
				"zul/utilisateur/utilisateurEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Recherche des données
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "utilisateurCreated", "utilisateurUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		String searchNom;
		if (textboxNom == null)
			searchNom = "";
		else
			searchNom = textboxNom.getValue();

		searchNom = searchNom.trim();

		// Chargement de tous les utilisateurs
		try {
			dataSet = utilisateurService.getAllByNom(searchNom,
					checkboxHistorique.isChecked());
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

	}

	/**
	 * Consultation d'un utilisateur
	 * 
	 * @param utilisateur Utilisateur à éditer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("utilisateurRecord") Utilisateur utilisateur)
			throws TechnicalException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", utilisateur);
		map.put("recordMode", RecordMode.CONSULT);

		Window window = (Window) Executions.createComponents(
				"zul/utilisateur/utilisateurEdit.zul", null, map);
		window.doModal();
	}

	/**
	 * Suppression d'un utilisateur
	 * 
	 * @param utilisateur Utilisateur à supprimer
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void delete(
			@BindingParam("utilisateurRecord") final Utilisateur utilisateur)
			throws TechnicalException {
		this.selectedItem = utilisateur;

		// Message de confirmation de la suppression
		String str = messageSource.getMessage("utilisateur.suppression",
				new Object[] { utilisateur.getNom() + " " + utilisateur.getPrenom() }, Locale.FRANCE);

		Messagebox.show(str, messageSource.getMessage("titre.suppression",
				new Object[] {}, Locale.FRANCE), Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

						if (((Integer) event.getData()).intValue() == Messagebox.OK) {

							// Si clic OK, alors suppression définitive
							utilisateurService.delete(utilisateur);
							dataSet.remove(dataSet.indexOf(selectedItem));
							BindUtils.postNotifyChange(null, null,
									UtilisateurGestionMVVM.this, "dataSet");

						}
					}
				});
	}

}
