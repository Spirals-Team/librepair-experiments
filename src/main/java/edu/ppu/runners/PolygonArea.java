package edu.ppu.runners;

import org.geotools.referencing.GeodeticCalculator;

public class PolygonArea {

	public static void main(String[] args) {
		GeodeticCalculator c = new GeodeticCalculator();
		c.setStartingGeographicPoint(48.764148, 9.168008);

		c.setDestinationGeographicPoint(48.690815, 9.194553);
		System.out.println(c.getOrthodromicDistance());
		System.out.println("Hello World");
	}

}
