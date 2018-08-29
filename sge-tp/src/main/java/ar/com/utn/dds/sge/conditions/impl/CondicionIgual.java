package ar.com.utn.dds.sge.conditions.impl;

import ar.com.utn.dds.sge.conditions.Condicion;

public class CondicionIgual<T> extends Condicion<T> {

	public CondicionIgual(Comparable<T> condicionante) {
		super(condicionante);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean comparar(Comparable<T> valor) {
		return valor.equals(this.getCondicionante());
	}

}
