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
	
	public boolean perteneceTransformadorALaZona(Transformador transformador) {
		return this.posicion.getDistancia(transformador.getPosicion()) <= radio;
	}
	
	public void asignarTransformadorACliente(Cliente cliente) {
		if(perteneceClienteALaZona(cliente))
			cliente.asignarTransformador(transformadores);
	}
	
	public boolean perteneceClienteALaZona(Cliente cliente) {
		return this.posicion.getDistancia(cliente.getPosicion()) <= radio;
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
