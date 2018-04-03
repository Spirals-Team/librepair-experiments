package uo.asw.inciManager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de utilidad para conversiones de fechas
 * @version marzo 2018
 *
 */
public class DateUtil {
	/**
	 * Convierte a formato Date un String con una fecha
	 * @param fecha
	 * @return Date dd/MM/yyyy
	 */
	public static Date stringToDate(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaDate;
	}

}
