package ar.com.utn.dds.sge.conditions.impl;

import ar.com.utn.dds.sge.conditions.Condicion;

public class CondicionMenor<T> extends Condicion<T> {

	public CondicionMenor(Comparable<T> condicionante) {
		super(condicionante);
	}

	@Override
	public Boolean comparar(Comparable<T> valor) {
		// TODO Auto-generated method stub
		return valor.compareTo((T) this.getCondicionante())< 0;
	}

}
