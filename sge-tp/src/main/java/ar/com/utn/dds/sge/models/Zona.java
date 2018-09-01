package ar.com.utn.dds.sge.models;

import java.util.ArrayList;
import java.util.List;

public class Zona {

	private Double x;
	private Double y;
	private List<Transformador> transformadores = new ArrayList<>();
	private double radio;
	
	public Zona(Double x, Double y, Double radio) {
		setX(x);
		setY(y);
		setRadio(radio);
	}
	
	public Float consumo() {
		Float consumo = 0.00f;
		for(Transformador transformador : transformadores) 
			consumo += transformador.consumo();
		return consumo;
	}
	
	public void agregarTransformador(Transformador transformador) {
		if(perteneceTransformadorALaZona(transformador))
			transformadores.add(transformador);
	}
	
	public boolean perteneceTransformadorALaZona(Transformador transformador) {
		return Posicion.getDistancia(x, y, transformador.getX(), transformador.getY()) <= radio;
	}
	
	public void asignarTransformadorACliente(Cliente cliente) {
		cliente.asignarTransformador(transformadores);
	}
	
	public Double getX() {
		return x;
	}
	
	public void setX(Double x) {
		this.x = x;
	}
	
	public Double getY() {
		return y;
	}
	
	public void setY(Double y) {
		this.y = y;
	}
	
	public List<Transformador> getTransformadores() {
		return transformadores;
	}
	
	public void setTransformadores(List<Transformador> transformadores) {
		this.transformadores = transformadores;
	}

	public Double getRadio() {
		return radio;
	}

	public void setRadio(Double radio) {
		this.radio = radio;
	}
	
}
