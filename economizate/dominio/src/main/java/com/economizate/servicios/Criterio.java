package com.economizate.servicios;

import java.util.List;
import com.economizate.entidades.MovimientoMonetario;

public interface Criterio {

	public List<MovimientoMonetario> filtrarMovimientos(List<MovimientoMonetario> movimientos);
}
