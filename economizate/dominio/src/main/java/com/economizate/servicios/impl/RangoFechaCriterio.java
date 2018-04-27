package com.economizate.servicios.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.xmlbeans.impl.regex.RegularExpression;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.entidades.Movimientos;
import com.economizate.servicios.Criterio;

public class RangoFechaCriterio implements Criterio {

	private Date fechaDesde;
	private Date fechaHasta;
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

	public RangoFechaCriterio(Date fechaDesde, Date fechaHasta) {
		if (fechaHasta.before(fechaDesde)) {
			throw new IllegalArgumentException(
					"La fecha hasta debe ser posterior a la fecha desde");
		}
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
	}

	/**
	 * 
	 * @param fechaDesde
	 *            : Se espera cadena con formato dd/MM/yyyy
	 * @param fechaHasta
	 *            : Se espera cadena con formato dd/MM/yyyy
	 * @throws ParseException
	 */
	public RangoFechaCriterio(String fechaDesde, String fechaHasta)
			throws ParseException {
		validarFormatoFecha(fechaDesde);
		validarFormatoFecha(fechaHasta);
		if (formater.parse(fechaHasta).before(formater.parse(fechaDesde))) {
			throw new IllegalArgumentException(
					"La fecha hasta debe ser posterior a la fecha desde");
		}
		this.fechaDesde = formater.parse(fechaDesde);
		this.fechaHasta = formater.parse(fechaHasta);
		
	}

	private void validarFormatoFecha(String fecha) throws ParseException {
		
		String regex = "^(29/02/(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
      + "|^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2}))$"
      + "|^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2}))$"
      + "|^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2}))$";
		RegularExpression reg = new RegularExpression(regex);
		if(!reg.matches(fecha)) {
			throw new ParseException("El formato de la fecha debe ser dd/MM/yyyy", -10);
		}
		
				
	}

	@Override
	public Movimientos filtrarMovimientos(
			List<MovimientoMonetario> movimientos) {

		Movimientos movsFecha = new Movimientos();

		for (MovimientoMonetario mov : movimientos) {
			if (mov.getFecha().after(fechaDesde)
					&& mov.getFecha().before(fechaHasta)) {
				movsFecha.agregarMovimiento(mov);
			}
		}
		return movsFecha;
	}

}
