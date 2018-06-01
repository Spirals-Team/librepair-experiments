package com.economizate.servicios;

import java.util.List;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;

public interface Criterio {

	public Movimientos filtrarMovimientos(List<MovimientoMonetario> movimientos);
}
