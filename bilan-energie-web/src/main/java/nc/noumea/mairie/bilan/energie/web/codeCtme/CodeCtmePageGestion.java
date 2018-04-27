package nc.noumea.mairie.bilan.energie.web.codeCtme;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 * 
 * @author Greg Dujardin
 *
 */
public class CodeCtmePageGestion implements PageInfo{
	
	private CodeCtmeGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/codeCtme/codeCtmeGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des codes CTME";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(CodeCtmeGestionPageId pageId) {
		this.pageId = pageId;
	}




}
