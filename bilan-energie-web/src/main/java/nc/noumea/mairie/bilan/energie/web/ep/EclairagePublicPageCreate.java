package nc.noumea.mairie.bilan.energie.web.ep;

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
public class EclairagePublicPageCreate implements PageInfo{
	
	private EclairagePublicEditPageId pageId;

	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();

		map.put("recordMode", RecordMode.NEW);
		
		return map;
	}

	@Override
	public String getZul() {
		return "zul/ep/eclairagePublicEdit.zul";
	}

	@Override
	public PageId getId() {
		return pageId;
	}

	@Override
	public String getTitle() {
		return "EP : Nouveau";
	}

	@Override
	public boolean isCloseable() {
		return true;
	}

	/**
	 * @return the pageId
	 */
	public EclairagePublicEditPageId getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(EclairagePublicEditPageId pageId) {
		this.pageId = pageId;
	}

}
