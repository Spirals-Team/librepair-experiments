package ar.com.utn.dds.sge.exceptions;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;

/**
 * Excepcion para manejar errores de parseo de Json
 * @author Grupo 2
 *
 */
public class JsonFormatoErroneo extends JsonParseException {

	
	private static final long serialVersionUID = 2555318219846113881L;

	public JsonFormatoErroneo(String mensaje) {
		super(mensaje, JsonLocation.NA);
	}

}
