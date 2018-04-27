package nc.noumea.mairie.bilan.energie.web.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.Infrastructure;
import nc.noumea.mairie.bilan.energie.contract.dto.StructureLabel;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeStructure;
import nc.noumea.mairie.bilan.energie.contract.service.BatimentService;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
import nc.noumea.mairie.bilan.energie.contract.service.InfrastructureService;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.AbstractMVVM;
import nc.noumea.mairie.bilan.energie.web.Notification;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.batiment.BatimentEditPageId;
import nc.noumea.mairie.bilan.energie.web.batiment.BatimentPageEdit;
import nc.noumea.mairie.bilan.energie.web.ep.EclairagePublicEditPageId;
import nc.noumea.mairie.bilan.energie.web.ep.EclairagePublicPageEdit;
import nc.noumea.mairie.bilan.energie.web.exceptions.WebTechnicalException;
import nc.noumea.mairie.bilan.energie.web.wm.PageExistException;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;
import nc.noumea.mairie.bilan.energie.web.wm.PageNotExistException;
import nc.noumea.mairie.bilan.energie.web.wm.TabComposer;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;

/**
 * MVVM de l'écran de création / édition / consultation d'une infrastructure
 * 
 * @author Greg Dujardin
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InfrastructureEditMVVM extends AbstractMVVM {

	/**
	 * Injection des services
	 */
	@WireVariable
	private InfrastructureService infrastructureService;
	
	@WireVariable
	private EclairagePublicService eclairagePublicService;

	@WireVariable
	private BatimentService batimentService;

	// Service de séurité
	@WireVariable
	private SecurityService securityService;

	/** Infrastructure en cours d'édition */
	private Infrastructure selectedRecord;

	/** Mode d'ouverture de la page */
	private RecordMode recordMode;

	/** Accès read only ? */
	private boolean readOnly;

	/** Structure sélectionné dans la listbox */
	private StructureLabel selectedStructure;

	/** Checkbox AffichageHistorique */
	@Wire
	private Checkbox afficheHistorique;

	/** Listbox des structures */
	@Wire
	private Listbox listboxStructure;

	/** Liste des structure à afficher */
	private List<StructureLabel> listeStructure;
	
	/**
	 * @return the selectedRecord
	 */
	public Infrastructure getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            the selectedRecord to set
	 */
	public void setSelectedRecord(Infrastructure selectedRecord) {
		this.selectedRecord = selectedRecord;
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
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param recordMode Mode d'édition
	 *            the recordMode to set
	 */
	public void setRecordMode(RecordMode recordMode) {
		this.recordMode = recordMode;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return the selectedStructure
	 */
	public StructureLabel getSelectedStructure() {
		return selectedStructure;
	}

	/**
	 * @param selectedStructure
	 *            the selectedStructure to set
	 */
	public void setSelectedStructure(StructureLabel selectedStructure) {
		this.selectedStructure = selectedStructure;
	}
	
	/**
	 * Liste des types de structure
	 * 
	 * @return Liste des types de structure
	 */
	public List<TypeStructure> getListeTypeStructure() {
		return new ArrayList<TypeStructure>(Arrays.asList(TypeStructure.values()));
	}

	/**
	 * Retourne la liste des structures à afficher en fonction de la checkbox
	 * 'Historique'
	 * 
	 * @return Liste des structures à afficher
	 */
	public List<StructureLabel> getListeStructure() {

		if (afficheHistorique.isChecked())
			listeStructure = selectedRecord.getListeStructure();
		else {
			List<StructureLabel> listeRetour = new ArrayList<StructureLabel>();

			for (StructureLabel structure : selectedRecord.getListeStructure())
				if (structure.getDateFin() == null)
					listeRetour.add(structure);
			listeStructure = listeRetour;
		}
		
		return listeStructure;
	}

	/**
	 * @param listeStructure the listeStructure to set
	 */
	public void setListeStructure(List<StructureLabel> listeStructure) {
		this.listeStructure = listeStructure;
	}

	/**
	 * Initialisation de l'affichage
	 * 
	 * @param view Composant View
	 * @param infrastructure Infrastructure à éditer
	 * @param recordMode Mode d'édition
	 * @throws TechnicalException Exception technique
	 */
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Infrastructure infrastructure,
			@ExecutionArgParam("recordMode") RecordMode recordMode)
			throws TechnicalException {

		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);

		if (recordMode.equals(RecordMode.NEW)) {
			this.selectedRecord = new Infrastructure();
			this.selectedRecord.setListeStructure(new ArrayList<StructureLabel>());
		}

		if (recordMode.equals(RecordMode.EDIT)) {
			this.selectedRecord = infrastructure;
		}

		if (recordMode.equals(RecordMode.CONSULT)) {
			setReadOnly(true);
			this.selectedRecord = infrastructure;
		}

		if (selectedRecord.getListeStructure().size() > 0) 
			selectedStructure = selectedRecord.getListeStructure().get(0);
	}

	/**
	 * Sauvegarde d'une infrastructure
	 * 
	 * @param close Option de fermeture de l'onglet
	 * @param pageId Identifiant de la page
	 * @throws TechnicalException Exception technique
	 */
	@Command
	public void save(@BindingParam("close") boolean close,
			@BindingParam("pageId") PageId pageId) throws TechnicalException {

		try {
			if (recordMode.equals(RecordMode.NEW)) {
				this.selectedRecord = infrastructureService.create(this.selectedRecord);

				setRecordMode(RecordMode.EDIT);
				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("infrastructure", selectedRecord);
				BindUtils
						.postGlobalCommand(null, null, "infrastructureCreated", params);
			} else if (recordMode.equals(RecordMode.EDIT)) {
				this.selectedRecord = infrastructureService.update(this.selectedRecord);

				Map<String, Object> params = new Hashtable<String, Object>();
				params.put("infrastructure", selectedRecord);
				BindUtils
						.postGlobalCommand(null, null, "infrastructureUpdated", params);
			}

		} catch (BusinessException e) {
			throw new WebTechnicalException(e.getMessage());
		}

		getWindowManager().setDirtyOnCurrentPage(false);
		getWindowManager().showNotification(
				new Notification("L'infrastructure a été enregistrée"));
		if (close)
			try {
				getWindowManager().closePage(pageId);
			} catch (PageNotExistException e) {
				throw new WebTechnicalException(e.getMessage());
			}

	}

	/**
	 * dirty ?
	 *
	 * @return booléen
	 * @throws TechnicalException Exception technique
	 */
	public boolean isDirty() throws TechnicalException {
		return getWindowManager().isDirty(getWindowManager().getCurrentPage().getInfo().getId());
	}
	
	
	/**
	 * Command de mise à jour d'un élément de la page
	 * 
	 * @param infrastructure Infrastructure à mettre à jour
     * @throws TechnicalException Exception technique
	 */
	@Command
	@NotifyChange({ "dirty"})
	public void miseAJour(
			@BindingParam("infrastructure") Infrastructure infrastructure) throws TechnicalException {

		if (infrastructure.getId() == selectedRecord.getId()) {

			getWindowManager().setDirtyOnCurrentPage(true);

		}
	}

	/**
	 * Rafraîchissement des listes
	 * 
	 */
	@Command
	@NotifyChange({ "listeStructure"})
	public void refreshListe() {

	}

	/**
	 * Gestion du double-clic sur la liste des structures
	 * 
     * @throws TechnicalException Exception technique
	 */
	@Command
	public void listStructureDoubleClicked() throws TechnicalException {

		PageInfo pageInfo = null;
		
		TabComposer wm = getWindowManager();
		
		if (selectedRecord.getType().equals(TypeStructure.EP)) {
			
			EclairagePublic eclairagePublic ;
			
			// Chargement de la structure depuis la BDD
			try {
				eclairagePublic = eclairagePublicService.read(getSelectedStructure().getId());
			} catch (BusinessException e) {
				throw new WebTechnicalException(e.getMessage());
			}

			// Ouverture d'une page avec l'éclairage Public
			EclairagePublicEditPageId id = new EclairagePublicEditPageId();
			id.setId(eclairagePublic.getId());
	
			pageInfo = new EclairagePublicPageEdit();
			((EclairagePublicPageEdit) pageInfo).setPageId(id);
			((EclairagePublicPageEdit) pageInfo).setEclairagePublic(eclairagePublic);
			((EclairagePublicPageEdit) pageInfo).setTitle(eclairagePublic.getDesignation());
			((EclairagePublicPageEdit) pageInfo).setSupportNumInventaire("");
		
			try {
				if (securityService.isAdministrateurEP() || securityService.isContributeurEP()) {
					((EclairagePublicPageEdit) pageInfo).setRecordMode(RecordMode.EDIT);
	
				} else {
					((EclairagePublicPageEdit) pageInfo).setRecordMode(RecordMode.CONSULT);
				}
			} catch (BusinessException e) {
				throw new WebTechnicalException(e.getMessage());
			}
		} else {
			Batiment batiment ;
			
			// Chargement de la structure depuis la BDD
			try {
				batiment = batimentService.read(getSelectedStructure().getId());
			} catch (BusinessException e) {
				throw new WebTechnicalException(e.getMessage());
			}

			// Ouverture d'une page avec le batiment
			BatimentEditPageId id = new BatimentEditPageId();
			id.setId(batiment.getId());
	
			pageInfo = new BatimentPageEdit();
			((BatimentPageEdit) pageInfo).setPageId(id);
			((BatimentPageEdit) pageInfo).setBatiment(batiment);
			((BatimentPageEdit) pageInfo).setTitle(batiment.getDesignation());
		
			try {
				if (securityService.isAdministrateurBatiment() || securityService.isContributeurBatiment()) {
					((BatimentPageEdit) pageInfo).setRecordMode(RecordMode.EDIT);
	
				} else {
					((BatimentPageEdit) pageInfo).setRecordMode(RecordMode.CONSULT);
				}
			} catch (BusinessException e) {
				throw new WebTechnicalException(e.getMessage());
			}
			
		}

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
