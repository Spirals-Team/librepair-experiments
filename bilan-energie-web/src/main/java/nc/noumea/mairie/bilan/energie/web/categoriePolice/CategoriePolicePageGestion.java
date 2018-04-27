package nc.noumea.mairie.bilan.energie.web.categoriePolice;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 * 
 * @author Greg Dujardin
 *
 */
public class CategoriePolicePageGestion implements PageInfo{
	
	private CategoriePoliceGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/categoriePolice/categoriePoliceGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des catégories de police";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(CategoriePoliceGestionPageId pageId) {
		this.pageId = pageId;
	}




}
