package nc.noumea.mairie.bilan.energie.web.batiment;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * PageInfo de l'édition des batiments
 * 
 * @author Greg Dujardin
 *
 */
public class BatimentPageEdit extends BatimentPageCreate implements PageInfo{
	
	private RecordMode recordMode;
	
	private Batiment batiment;
	
	private String title;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		map.put("selectedRecord", batiment);
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
	 *  the recordMode to set
	 * 
	 * @param recordMode Mode d'édition
	 */
	public void setRecordMode(RecordMode recordMode) {
		this.recordMode = recordMode;
	}

	/**
	 * @return the batiment
	 */
	public Batiment getBatiment() {
		return batiment;
	}

	/**
	 * @param batiment the batiment to set
	 */
	public void setBatiment(Batiment batiment) {
		this.batiment = batiment;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = "Batiment " + title;
	}


}
