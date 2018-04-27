package nc.noumea.mairie.bilan.energie.web.police;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageId;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class PolicePageCreate implements PageInfo{
	
	private PoliceEditPageId pageId;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();

		map.put("recordMode", RecordMode.NEW);
		
		return map;
	}

	@Override
	public String getZul() {
		return "zul/police/policeEdit.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "Police : Nouveau";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}

	/**
	 * @return the pageId
	 */
	public PoliceEditPageId getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(PoliceEditPageId pageId) {
		this.pageId = pageId;
	}


}
