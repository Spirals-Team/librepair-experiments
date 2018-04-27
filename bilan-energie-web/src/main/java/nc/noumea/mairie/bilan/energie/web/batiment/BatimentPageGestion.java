package nc.noumea.mairie.bilan.energie.web.batiment;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * PageInfo des batiments
 * 
 * @author Greg Dujardin
 *
 */
public class BatimentPageGestion implements PageInfo{
	
	private BatimentGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/batiment/batimentGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des batiments";
	}

	@Override
	public boolean isCloseable() {
		return false;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(BatimentGestionPageId pageId) {
		this.pageId = pageId;
	}




}
