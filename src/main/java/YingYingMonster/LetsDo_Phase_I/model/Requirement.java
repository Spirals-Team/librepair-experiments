package YingYingMonster.LetsDo_Phase_I.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Requirement {
	ArrayList<Integer> color;
	int thickness;
	int shape;

	public ArrayList<Integer> getColor(){
		return color;
		
	}
	public int getThickness(){
		return thickness;
		
	}
	public int getShape(){
		return shape;
		
	}
	public void setColor(ArrayList<Integer> color) {
		this.color = color;
	}
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	public void setShape(int shape) {
		this.shape = shape;
	}
	
	public String[] toStringCSV(){
		String colors="";
		for(int n:color){
			colors+=n+"&";
		}
		return new String[]{colors,thickness+"",shape+""};

	}
}
