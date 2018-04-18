package com.economizate.datos;

import java.util.Arrays;
import java.util.List;
import com.economizate.entidades.MovimientoMonetario;

public class ListaMovimientos {

	private List<MovimientoMonetario> movimientos;

	public ListaMovimientos() {
		super();
		this.movimientos = Arrays.asList(
				new MovimientoMonetario("Luz", "Abril", -300.0),
				new MovimientoMonetario("Sueldo", "Me descontaron ganancias", 37214.52)
				); 
	}
	
	public void agregarMovimiento(String descripcion, String observacion, double importe) {
		MovimientoMonetario usuario = new MovimientoMonetario(descripcion, observacion, importe);

		movimientos.add(usuario);
	}
	
	public List<MovimientoMonetario> getMovimientos() {
		return movimientos;
	}	


}
