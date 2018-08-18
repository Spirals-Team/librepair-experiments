package ar.com.utn.dds.sge.models;

import java.util.ArrayList;
import java.util.List;

public class Transformador {

	private Posicion posicion;
	private List<Cliente> clientes = new ArrayList<>();
	
	public Transformador(Posicion posicion) {
		setPosicion(posicion);
	}

	public Float consumo() {
		Float consumo = 0.00f;
		for(Cliente c : clientes)
			consumo += c.calcularConsumo();
		return consumo;
	}
	
	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
}
