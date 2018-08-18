package ar.com.utn.dds.sge.conditions.impl;

import ar.com.utn.dds.sge.conditions.Condicion;

public class CondicionMayor<T> extends Condicion<T>{

	public CondicionMayor(Comparable<T> condicionante) {
		super(condicionante);
	}

	@Override
	public Boolean comparar(Comparable<T> valor) {
		return valor.compareTo((T) this.getCondicionante())>0;
	}





}
