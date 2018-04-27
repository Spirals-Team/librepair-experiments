package nc.noumea.mairie.bilan.energie.web.direction;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class DirectionPageGestion implements PageInfo{
	
	private DirectionGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/direction/directionGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des directions";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(DirectionGestionPageId pageId) {
		this.pageId = pageId;
	}




}
