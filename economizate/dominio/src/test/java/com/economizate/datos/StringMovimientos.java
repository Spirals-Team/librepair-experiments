package com.economizate.datos;

import java.util.Arrays;
import java.util.List;

public class StringMovimientos {

	private List<String> movimientos;

	public StringMovimientos() {
		super();
		this.movimientos = Arrays.asList(
				"compra;chino;50;5",
				"compra;chino;50b;2" );
	}
	
	public List<String> getMovimientos() {
		return movimientos;
	}	
	
}
