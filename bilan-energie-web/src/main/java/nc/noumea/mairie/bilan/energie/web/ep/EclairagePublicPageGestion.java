package nc.noumea.mairie.bilan.energie.web.ep;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class EclairagePublicPageGestion implements PageInfo{
	
	private EclairagePublicGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/ep/eclairagePublicGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des EP";
	}

	@Override
	public boolean isCloseable() {
		return false;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(EclairagePublicGestionPageId pageId) {
		this.pageId = pageId;
	}




}
