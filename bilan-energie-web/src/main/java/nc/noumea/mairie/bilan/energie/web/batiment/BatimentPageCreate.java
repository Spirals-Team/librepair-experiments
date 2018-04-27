package nc.noumea.mairie.bilan.energie.web.batiment;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * PageInfo de la création des batiments
 * 
 * @author Greg Dujardin
 *
 */
public class BatimentPageCreate implements PageInfo{
	
	private BatimentEditPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();

		map.put("recordMode", RecordMode.NEW);
		
		return map;
	}

	@Override
	public String getZul() {
		return "zul/batiment/batimentEdit.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Batiment : Nouveau";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}

	/**
	 * @return the pageId
	 */
	public BatimentEditPageId getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(BatimentEditPageId pageId) {
		this.pageId = pageId;
	}


}
