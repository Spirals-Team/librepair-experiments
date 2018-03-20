package YingYingMonster.LetsDo_Phase_I.model;

import java.util.ArrayList;

/**
 * 几何标记，包含了颜色值、坐标
 * @author 17678
 *
 */
public class GeometryTag {

	private int rgb;//颜色值，每个分量占int的一个字节
	
	private ArrayList<Integer>coordinates;//坐标

	public int getRgb() {
		return rgb;
	}

	public void setRgb(int rgb) {
		this.rgb = rgb;
	}

	public ArrayList<Integer> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<Integer> coordinates) {
		this.coordinates = coordinates;
	}
	
	public GeometryTag(){
		coordinates=new ArrayList<>();
	}
}
