package ar.com.utn.dds.sge.exceptions;

import java.io.IOException;

/**
 * Excepcion para manejar errores cuando no se encuentra un archivo Json
 * @author Grupo 2
 *
 */
public class JsonNoEncontradoException extends IOException{

	private static final long serialVersionUID = -5945739017407319433L;

	public JsonNoEncontradoException(String mensaje) {
		super(mensaje);
	}
	
}
