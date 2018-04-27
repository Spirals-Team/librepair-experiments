package nc.noumea.mairie.bilan.energie.web.facture;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentatin de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class FacturePageGestion implements PageInfo{
	
	private FactureGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/facture/factureGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des factures";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @return the pageId
	 */
	public FactureGestionPageId getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(FactureGestionPageId pageId) {
		this.pageId = pageId;
	}




}
