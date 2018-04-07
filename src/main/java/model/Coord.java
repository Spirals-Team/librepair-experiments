package model;

import java.util.NoSuchElementException;

/**
 * This class builds valid coordinates in the geographic system.
 * @author usuario
 *
 */
public class Coord {

	private double lat;
	private double lng;

	/*
	 * Constructors.
	 */
	/**
	 * 
	 * @param lat= latitude
	 * @param lng=longitude
	 */
	public Coord(double lat, double lng){
		if (lat>=-90&& 90>=lat && lng>=-180 && 180>=lng){
			this.lat=lat;
			this.lng=lng;
		} else {
			throw new NoSuchElementException("Coordenada inexistente");
		}
	}
	
	/*
	 * Getters and Setters.
	 */
	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

}
