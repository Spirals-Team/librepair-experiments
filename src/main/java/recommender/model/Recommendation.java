package recommender.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Recommendation {
	
	private String name;
	private String type;
	private double percentage;
	
	public Recommendation(String name, String type, double percentage) {
		this.name=name;
		this.type = type;
		this.percentage =percentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	 private float round(double d, int places) {
	        if (places < 0) throw new IllegalArgumentException();

	        BigDecimal bd = new BigDecimal(d);
	        bd = bd.setScale(places, RoundingMode.HALF_UP);
	        return bd.floatValue();
	 }
	 
	 public String getType() {
			return type;
	}

	public void setType(String type) {
			this.type = type;
	}

	@Override
	public String toString() {
		
		return "Name: "+name+" Type: "+type+" Percentage: "+round(percentage,2)+"%";
	}

}
