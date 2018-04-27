package nc.noumea.mairie.bilan.energie.web.facture;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class FacturePageEdit implements PageInfo{
	
	private RecordMode recordMode;
	
	private FichierFactureSimple fichierFacture;
	
	private String title;
	
	private FactureEditPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		map.put("selectedRecord", fichierFacture);
		map.put("recordMode", recordMode);
		
		return map;
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param recordMode Mode d'édition the recordMode to set
	 */
	public void setRecordMode(RecordMode recordMode) {
		this.recordMode = recordMode;
	}

	/**
	 * @return the fichierFacture
	 */
	public FichierFactureSimple getFichierFacture() {
		return fichierFacture;
	}

	/**
	 * @param fichierFacture the fichierFacture to set
	 */
	public void setFichierFacture(FichierFactureSimple fichierFacture) {
		this.fichierFacture = fichierFacture;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = "Fichier " + title;
	}

	@Override
	public String getZul() {
		return "zul/facture/factureEdit.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @return the pageId
	 */
	public FactureEditPageId getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(FactureEditPageId pageId) {
		this.pageId = pageId;
	}


}
