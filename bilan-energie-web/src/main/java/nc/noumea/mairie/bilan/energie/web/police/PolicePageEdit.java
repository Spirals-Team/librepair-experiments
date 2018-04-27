package nc.noumea.mairie.bilan.energie.web.police;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class PolicePageEdit extends PolicePageCreate implements PageInfo{
	
	private RecordMode recordMode;
	
	private Police police;
	
	private String title;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		map.put("selectedRecord", police);
		map.put("recordMode", recordMode);
		
		return map;
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * @return the recordMode
	 */
	public RecordMode getRecordMode() {
		return recordMode;
	}

	/**
	 * @param recordMode Mode d'édition the recordMode to set
	 */
	public void setRecordMode(RecordMode recordMode) {
		this.recordMode = recordMode;
	}

	/**
	 * @return the police
	 */
	public Police getPolice() {
		return police;
	}

	/**
	 * @param police the police to set
	 */
	public void setPolice(Police police) {
		this.police = police;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = "Police " + title;
	}


}
