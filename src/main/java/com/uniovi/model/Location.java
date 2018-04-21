package com.uniovi.model;

import javax.persistence.Embeddable;

/**
 * Clase que guarda la localizacion de los usuarios
 * 
 * @author Tania Alvarez Diaz
 *
 */
@Embeddable
public class Location {

    private double latitude; // 0° a 90 °: Hemisferio Norte; 0° a -90°: Hemisferio Sur
    private double longitud; // 0° a 180°: Al este del meridiano de Greenwich; 0° a -180°: Al oeste del
    // meridiano de Greenwich
    private boolean exist;

    public Location() {

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(longitud);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Location other = (Location) obj;
	if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
	    return false;
	if (Double.doubleToLongBits(longitud) != Double.doubleToLongBits(other.longitud))
	    return false;
	return true;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
	return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    private void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
	return longitud;
    }

    /**
     * @param longitud
     *            the longitud to set
     */
    private void setLongitud(double longitud) {
	this.longitud = longitud;
    }

    /**
     * @return the exist
     */
    public boolean isExist() {
	return exist;
    }

    /**
     * @param exist
     *            the exist to set
     */
    public void setExist(boolean exist) {
	this.exist = exist;
    }

    /**
     * Constructor
     * 
     * @param latitude
     * @param longitud
     */
    public Location(double latitude, double longitud) {
	setLatitude(latitude);
	setLongitud(longitud);
	setExist(true); // Por defecto si existe, si no tiene tendremos que cambiarle el valor
    }

    @Override
    public String toString() {
	if (!isExist())
	    return " ";
	return latitude + " ; " + longitud;
    }

}