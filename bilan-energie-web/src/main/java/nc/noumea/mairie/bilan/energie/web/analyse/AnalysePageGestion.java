package nc.noumea.mairie.bilan.energie.web.analyse;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de pageInfo
 * 
 * @author Greg Dujardin
 *
 */
public class AnalysePageGestion implements PageInfo{
	
	private AnalyseGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/analyse/analyseGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des analyses";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(AnalyseGestionPageId pageId) {
		this.pageId = pageId;
	}




}
