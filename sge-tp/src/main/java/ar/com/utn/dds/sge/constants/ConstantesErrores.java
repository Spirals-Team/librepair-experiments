package ar.com.utn.dds.sge.constants;

/**
 * Clase donde se registran los mensajes de error del sistema.
 * @author Grupo 2
 *
 */
public class ConstantesErrores {
	
	//Errores de archivo no encontrado
	public static final String ARCHIVO_NO_ENCONTRADO = "No se encontro el archivo json";
	
	//Errores de parseo
	public static final String PARSEO_ERRONEO_CLIENTE = "El formato del archivo json de clientes no es el correcto";
	public static final String PARSEO_ERRONEO_ADMINISTRADOR = "El formato del archivo json de administradores no es el correcto";
	public static final String PARSEO_ERRONEO_CATEGORIA = "El formato del archivo json de categorias no es el correcto";
	
	//Errores de mappeo
	public static final String MAPPEO_ERRONEO_CLIENTE = "La estructura del archivo Json de clientes es incorrecta";
	public static final String MAPPEO_ERRONEO_ADMINISTRADOR = "La estructura del archivo Json de administradores es incorrecta";
	public static final String MAPPEO_ERRONEO_CATEGORIA = "La estructura del archivo Json de categorias es incorrecta";
	
	//Errores de validacion
	public static final String CAMPOS_REQUERIDOS_VACIOS = "Debido a los campos faltantes no fue posible importar la entidad correctamente.";
}
