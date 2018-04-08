package uniovi.es.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class Coordinates {

	private double longitude;
	private double latitude;
	private Random r = new Random();
	
	public Coordinates() {
		this.longitude = r.nextDouble()*360 - 180;
		this.latitude = r.nextDouble()*360 - 180;
	}

	public double getLongitud() {
		return longitude;
	}

	public double getLatitud() {
		return latitude;
	}

	public String getCoordinates()
	{
		DecimalFormat df = new DecimalFormat("#.####");  
		return df.format(getLatitud()) + " || " + df.format(getLongitud());
	}

}