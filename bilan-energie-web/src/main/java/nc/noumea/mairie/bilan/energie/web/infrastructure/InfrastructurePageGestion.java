package nc.noumea.mairie.bilan.energie.web.infrastructure;

import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class InfrastructurePageGestion implements PageInfo{
	
	private InfrastructureGestionPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		return null;
	}

	@Override
	public String getZul() {
		return "zul/infrastructure/infrastructureGestion.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Gestion des infrastructures";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}
	
	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(InfrastructureGestionPageId pageId) {
		this.pageId = pageId;
	}




}
