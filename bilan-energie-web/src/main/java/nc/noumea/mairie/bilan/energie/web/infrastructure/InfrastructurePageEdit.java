package nc.noumea.mairie.bilan.energie.web.infrastructure;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Infrastructure;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class InfrastructurePageEdit extends InfrastructurePageCreate implements PageInfo{
	
	private RecordMode recordMode;
	
	private Infrastructure infrastructure;
	
	private String title;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		map.put("selectedRecord", infrastructure);
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
	 * @return the infrastructure
	 */
	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	/**
	 * @param infrastructure the infrastructure to set
	 */
	public void setInfrastructure(Infrastructure infrastructure) {
		this.infrastructure = infrastructure;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = "Infrastructure " + title;
	}


}
