package ar.com.utn.dds.sge.models;

public class Posicion {
	
	private double x;
	private double y;
	
	public Posicion(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}


}
