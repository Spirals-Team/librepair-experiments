package nc.noumea.mairie.bilan.energie.web.typePolice;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class TypePolicePageGestion implements PageInfo{
	
	private TypePoliceGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/typePolice/typePoliceGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des types de police";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(TypePoliceGestionPageId pageId) {
		this.pageId = pageId;
	}




}
