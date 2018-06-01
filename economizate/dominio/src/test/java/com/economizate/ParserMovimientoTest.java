package com.economizate;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import com.economizate.datos.StringMovimientos;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.impl.ConcreteValidadorRegistroStrategy;
import com.economizate.servicios.impl.ParserRegistroConCuota;
import com.economizate.servicios.impl.ParserRegistroFechaSinCuota;
import com.economizate.servicios.impl.ParserRegistroMovimiento;
import com.economizate.servicios.impl.ParserRegistroSinCuota;

public class ParserMovimientoTest {

	StringMovimientos movsPrueba = new StringMovimientos();	
		
	@Test
	public void parsearMovimientoConCuota() throws ParseException {
		
			
		ParserRegistroMovimiento parser = new ParserRegistroMovimiento(movsPrueba.getMovimientoConCuota(), ";", new ParserRegistroConCuota());
		MovimientoMonetario mov = parsearMovimientoAndSetearValidador(parser);
		assertTrue(mov.isValid());
	}

	private MovimientoMonetario parsearMovimientoAndSetearValidador(
			ParserRegistroMovimiento parser) throws ParseException {
		MovimientoMonetario mov = parser.parse();
		mov.setValidador(new ConcreteValidadorRegistroStrategy());
		return mov;
	}

	@Test
	public void parsearMovimientoSinCuota() throws ParseException {
		
		StringMovimientos movsPrueba = new StringMovimientos();		
		ParserRegistroMovimiento parser = new ParserRegistroMovimiento(movsPrueba.getMovimientosSinCuota(), ";", new ParserRegistroSinCuota());
		MovimientoMonetario mov = parsearMovimientoAndSetearValidador(parser);
		assertTrue(mov.isValid());
	}
	
	@Test
	public void parsearMovimientoFechaSinCuota() throws ParseException {
		
		StringMovimientos movsPrueba = new StringMovimientos();		
		ParserRegistroMovimiento parser = new ParserRegistroMovimiento(movsPrueba.getMovimientosFechaSinCuota(), ";", new ParserRegistroFechaSinCuota());
		MovimientoMonetario mov = parsearMovimientoAndSetearValidador(parser);
		assertTrue(mov.isValid());
	}
}
