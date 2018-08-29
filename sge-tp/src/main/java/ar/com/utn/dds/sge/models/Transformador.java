package ar.com.utn.dds.sge.models;

import java.util.ArrayList;
import java.util.List;

public class Transformador {

	private Double x;
	private Double y;
	private List<Cliente> clientes = new ArrayList<>();
	private boolean activo;
	
	public Transformador(Double x, Double y) {
		setX(x);
		setY(y);
	}
	
	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}

	public Float consumo() {
		Float consumo = 0.00f;
		for(Cliente cliente : clientes)
			consumo += cliente.calcularConsumo();
		return consumo;
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
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
