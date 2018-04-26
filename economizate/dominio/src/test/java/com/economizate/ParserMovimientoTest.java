package com.economizate;

import static org.junit.Assert.*;
import java.text.ParseException;
import org.junit.Test;
import com.economizate.datos.StringMovimientos;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.impl.ConcreteValidadorRegistroStrategy;
import com.economizate.servicios.impl.ParserRegistroMovimiento;

public class ParserMovimientoTest {

	@Test
	public void parsearMovimientoPuntoAndComa() throws ParseException {
		
		StringMovimientos movsPrueba = new StringMovimientos();		
		ParserRegistroMovimiento parser = new ParserRegistroMovimiento(movsPrueba.getMovimientos().get(0), ";");
		MovimientoMonetario mov = parser.parse();
		mov.setValidador(new ConcreteValidadorRegistroStrategy());
		assertTrue(mov.isValid());
	}

}
