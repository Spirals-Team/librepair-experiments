package YingYingMonster.LetsDo_Phase_I.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Tag 标签类，记录了各种属性、语句、语句分词、几何标签
 * @author 17678
 *
 */
public class Tag {

	private String dataSetId,imgId;//对应的数据集id,图片id
	
	private Map<String,String>tags;//各种属性的标签
	
	private Map<Integer,String>sentences;//描述性的语句
	
	private Map<Integer,ArrayList<String>>tokens;//语句分词
	
	private ArrayList<GeometryTag>coordinates;//几何标记
	
	public Tag(){
		
		tags=new HashMap<>();
		sentences=new HashMap<>();
		tokens=new HashMap<>();
		coordinates=new ArrayList<>();
		
	}
	
	

	public String getDataSetId() {
		return dataSetId;
	}



	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}



	public String getImgId() {
		return imgId;
	}



	public void setImgId(String imgId) {
		this.imgId = imgId;
	}



	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public Map<Integer, String> getSentences() {
		return sentences;
	}

	public void setSentences(Map<Integer, String> sentences) {
		this.sentences = sentences;
	}

	public Map<Integer, ArrayList<String>> getTokens() {
		return tokens;
	}

	public void setTokens(Map<Integer, ArrayList<String>> tokens) {
		this.tokens = tokens;
	}

	public ArrayList<GeometryTag> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<GeometryTag> coordinates) {
		this.coordinates = coordinates;
	}
	
}
