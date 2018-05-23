package com.economizate;

import static org.junit.Assert.assertFalse;
import org.junit.Test;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.impl.ConcreteValidadorRegistroStrategy;


public class ValidarMovimientoTest {

	@Test
	public void validarDescripcionVaciaTest()  {
				
		MovimientoMonetario mov = new MovimientoMonetario("", "Sueldo", 25000.0);
		mov.setValidador(new ConcreteValidadorRegistroStrategy());
		assertFalse(mov.isValid());
	}
	
	@Test
	public void validarObservacionVaciaTest()  {
				
		MovimientoMonetario mov = new MovimientoMonetario("Sueldo", "", 25000.0);
		mov.setValidador(new ConcreteValidadorRegistroStrategy());
		assertFalse(mov.isValid());
	}

	@Test
	public void validarImporteCeroTest()  {
				
		MovimientoMonetario mov = new MovimientoMonetario("Sueldo", "Sueldo", 0.0);
		mov.setValidador(new ConcreteValidadorRegistroStrategy());
		assertFalse(mov.isValid());
	}

	@Test
	public void validarImporteNullTest()  {
				
		MovimientoMonetario mov = new MovimientoMonetario("Sueldo", "Sueldo", null);
		mov.setValidador(new ConcreteValidadorRegistroStrategy());
		assertFalse(mov.isValid());
	}
}
