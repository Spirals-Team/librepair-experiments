package ar.com.utn.dds.sge.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Excepcion para manejar errores de mappeo de Json
 * @author Grupo 2
 *
 */
public class JsonMapperException extends JsonMappingException {


	private static final long serialVersionUID = 6195063823617329710L;

	public JsonMapperException(String mensaje) {
		super(mensaje);
		// TODO Auto-generated constructor stub
	}
	

}
