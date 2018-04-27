package nc.noumea.mairie.bilan.energie.web.ep;

import java.util.Hashtable;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.web.RecordMode;
import nc.noumea.mairie.bilan.energie.web.wm.PageInfo;

/**
 * Implémentation de PageInfo
 *
 * @author Greg Dujardin
 *
 */
public class EclairagePublicPageEdit extends EclairagePublicPageCreate implements PageInfo{
	
	private RecordMode recordMode;
	
	private EclairagePublic eclairagePublic;
	
	private String supportNumInventaire;
	
	private String title;


	@Override
	public Map<String, Object> getParameter() {
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		map.put("selectedRecord", eclairagePublic);
		map.put("supportNumInventaire", supportNumInventaire);
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
	 * @return the eclairagePublic
	 */
	public EclairagePublic getEclairagePublic() {
		return eclairagePublic;
	}

	/**
	 * @param eclairagePublic the eclairagePublic to set
	 */
	public void setEclairagePublic(EclairagePublic eclairagePublic) {
		this.eclairagePublic = eclairagePublic;
	}

	/**
	 * @return the supportNumInventaire
	 */
	public String getSupportNumInventaire() {
		return supportNumInventaire;
	}

	/**
	 * @param supportNumInventaire the supportNumInventaire to set
	 */
	public void setSupportNumInventaire(String supportNumInventaire) {
		this.supportNumInventaire = supportNumInventaire;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


}
