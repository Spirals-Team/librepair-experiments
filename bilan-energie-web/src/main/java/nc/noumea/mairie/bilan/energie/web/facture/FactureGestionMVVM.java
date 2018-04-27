package nc.noumea.mairie.bilan.energie.web.facture;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.service.FichierFactureService;
import nc.noumea.mairie.bilan.energie.contract.service.ParametrageService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.wm.PageExistException;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;
import nc.noumea.mairie.bilan.energie.web.wm.TabComposer;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

/**
 * MVVM de l'écran de gestion des factures
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FactureGestionMVVM extends AbstractMVVM {

	/**
	 * Injection du service sur les factures
	 */
	@WireVariable
	private FichierFactureService fichierFactureService;
	
	@WireVariable
	private ParametrageService parametrageService;

	/**
	 * Item sélectionné dans la grille des factures
	 */
	private FichierFactureSimple selectedItem;

	/**
	 * Liste des items des factures
	 */
	private List<FichierFactureSimple> dataSet = null;

	/**
	 * @return selectedItem
	 */
	public FichierFactureSimple getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem Item sélectionné
	 */
	public void setSelectedItem(FichierFactureSimple selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * Initialisation du vue-model
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Init
	public void init() throws TechnicalException {

		// Chargement de toutes les polices
		try {
			dataSet = fichierFactureService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
	}

	/**
	 * Récupération de la liste des fichiers facture
	 * 
	 * @return Liste des polices
	 */
	public List<FichierFactureSimple> getDataSet() {
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
					.getByParametre("FACTURE_LST_PAGINATION");
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		Long pagination;
		pagination = Long.parseLong(parametrage.getValeur());
		return pagination;
	}


	/**
	 * Recherche des fichiers de facture
	 *
	 * @throws TechnicalException Exception technique
	 */
	@Command
	@GlobalCommand({ "fichierFactureUpdated" })
	@NotifyChange("dataSet")
	public void search() throws TechnicalException {

		// Chargement de toutes les polices
		try {
			dataSet = fichierFactureService.getAll();
		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}
		
	}
	
	/**
	 * Edition d'un fichier de facture
	 *
	 * @param fichierFacture FichierFacture à éditer
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void edit(@BindingParam("fichierFactureRecord") FichierFactureSimple fichierFacture) throws TechnicalException {

		// Ouverture d'une page avec le fichier
		FactureEditPageId id = new FactureEditPageId();
		id.setId(getSelectedItem().getId());

		FacturePageEdit pageInfo = new FacturePageEdit();
		pageInfo.setPageId(id);
		pageInfo.setFichierFacture(getSelectedItem());
		pageInfo.setRecordMode(RecordMode.EDIT);
		pageInfo.setTitle(getSelectedItem().getNom());

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
}
