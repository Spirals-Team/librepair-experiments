package ar.com.utn.dds.sge.models;

import java.util.ArrayList;
import java.util.List;

public class Zona {

	private Posicion posicion;
	private List<Transformador> transformadores = new ArrayList<>();
	private double radio;
	
	public Zona(Posicion posicion, Double radio) {
		setPosicion(posicion);
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
	
	public boolean perteneceTransformadorALaZona(Transformador t) {
		return (Math.sqrt (Math.pow((posicion.getX() - t.getPosicion().getX()), 2) +
				Math.pow((posicion.getY() - t.getPosicion().getY()), 2)) <= radio);
	}
	
	public void asignarTransformadorACliente(Cliente cliente) {
		if(perteneceClienteALaZona(cliente))
			cliente.asignarTransformador(transformadores);
	}
	
	public boolean perteneceClienteALaZona(Cliente c) {
		return (Math.sqrt (Math.pow((posicion.getX() - c.getPosicion().getX()), 2) +
				Math.pow((posicion.getY() - c.getPosicion().getY()), 2)) <= radio);
	}
	
	public Posicion getPosicion() {
		return posicion;
	}
	
	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
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
