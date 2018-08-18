
package ar.com.utn.dds.sge.mappers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.utn.dds.sge.constants.ConstantesErrores;
import ar.com.utn.dds.sge.exceptions.FieldValidationException;
import ar.com.utn.dds.sge.exceptions.JsonFormatoErroneo;
import ar.com.utn.dds.sge.exceptions.JsonMapperException;
import ar.com.utn.dds.sge.exceptions.JsonNoEncontradoException;
import ar.com.utn.dds.sge.models.Administrador;
import ar.com.utn.dds.sge.models.Categoria;
import ar.com.utn.dds.sge.models.Cliente;
import ar.com.utn.dds.sge.validators.FieldsValidator;

/**
 * Clase encargada de extraer entidades desde archivos Json y mappearlas como
 * objetos en memoria.
 * 
 * @author Grupo 2
 *
 */
public class JsonMapper {

	private ObjectMapper objectMapper;

	public JsonMapper() {
		objectMapper = new ObjectMapper();
	}

	/**
	 * Metodo que extrae lista de clientes desde un archivo json
	 * 
	 * @param pathJsonClientes Ruta del archivo json de clientes
	 * @return Lista de clientes obtenidos desde el archivo json
	 * @throws IOException
	 */
	
	public byte[] leerArchivo(String pathJson) throws IOException {
		try {
			return Files.readAllBytes(Paths.get(pathJson));
		} catch(IOException e) {
			// Error de entrada al intentar leer el archivo
			throw new JsonNoEncontradoException(ConstantesErrores.ARCHIVO_NO_ENCONTRADO);
		}		
	}
	
	
	public void escribirArchivo(String pathJson, byte[] jsonData ) throws IOException {
		try {
			Files.write(Paths.get(pathJson), jsonData, StandardOpenOption.CREATE);//readAllBytes();
		} catch(IOException e) {
			// Error de entrada al intentar leer el archivo
			throw new JsonNoEncontradoException(ConstantesErrores.ARCHIVO_NO_ENCONTRADO);
		}		
	}
		
	public List<Cliente> extraerClientesJson(byte[] jsonData)
			throws JsonParseException, JsonMappingException, IOException, FieldValidationException {
		
		List<Cliente> clientes = null;
		try {
			// Obtiene clientes
			clientes = objectMapper.readValue(jsonData, new TypeReference<List<Cliente>>() {
			});
		} catch (JsonParseException e) {
			// Error cuando el archivo recibido no es un json.
			throw new JsonFormatoErroneo(ConstantesErrores.PARSEO_ERRONEO_CLIENTE);
		} catch (JsonMappingException e) {
			// Error de mapeo cuadno la clase que se quiere obtener es distinta al contenido
			// del json.
			throw new JsonMapperException(ConstantesErrores.MAPPEO_ERRONEO_CLIENTE);
		}

		//Se validan los atributos minimos
		FieldsValidator<Cliente> fv = new FieldsValidator<Cliente>();
		fv.validarAtributosRequeridos(clientes);
		
		// retorna lista de objetos leídos
		return clientes;
	}

	public  byte[] crearClientesJson(List<Cliente> clientes)
			throws JsonParseException, JsonMappingException, IOException, FieldValidationException {
		byte[] jsonData;
		
		try {
			// Obtiene clientes
			jsonData = objectMapper.writeValueAsBytes(clientes); {
			};
		} catch (JsonParseException e) {
			// Error cuando el archivo recibido no es un json.
			throw new JsonFormatoErroneo(ConstantesErrores.PARSEO_ERRONEO_CLIENTE);
		} catch (JsonMappingException e) {
			// Error de mapeo cuadno la clase que se quiere obtener es distinta al contenido
			// del json.
			throw new JsonMapperException(ConstantesErrores.MAPPEO_ERRONEO_CLIENTE);
		}

		//Se validan los atributos minimos
		FieldsValidator<Cliente> fv = new FieldsValidator<Cliente>();
		fv.validarAtributosRequeridos(clientes);
		
		// retorna lista de objetos leídos
		return jsonData;
	}
	
	
	/**
	 * Metodo que extrae lista de administradores desde un archivo json
	 * 
	 * @param pathJsonAdmin Ruta del archivo json de administradores
	 * @return Lista de administradores obtenidos desde el archivo json
	 * @throws IOException
	 */
	public List<Administrador> extraerAdministradoresJson(byte[] jsonData)
			throws JsonParseException, JsonMappingException, IOException, FieldValidationException {
		
		List<Administrador> administradores = null;
		try {
			// Obtiene clientes
			administradores = objectMapper.readValue(jsonData, new TypeReference<List<Administrador>>() {
			});
		} catch (JsonParseException e) {
			// Error cuando el archivo recibido no es un json.
			throw new JsonFormatoErroneo(ConstantesErrores.PARSEO_ERRONEO_ADMINISTRADOR);
		} catch (JsonMappingException e) {
			// Error de mapeo cuadno la clase que se quiere obtener es distinta al contenido
			// del json.
			throw new JsonMapperException(ConstantesErrores.MAPPEO_ERRONEO_ADMINISTRADOR);
		}

		//Se validan los atributos minimos
		FieldsValidator<Administrador> fv = new FieldsValidator<Administrador>();
		fv.validarAtributosRequeridos(administradores);
		
		// retorna lista de objetos leídos
		return administradores;
	}

	/**
	 * Metodo que extrae lista de categorias desde un archivo json
	 * 
	 * @param nombreJsonCateg Ruta del archivo json de categorias
	 * @return Lista de categorias obtenidas desde el archivo json
	 * @throws IOException
	 */
	public List<Categoria> extraerCategoriasJson(byte[] jsonData)
			throws JsonParseException, JsonMappingException, IOException, FieldValidationException {

		List<Categoria> categorias = null;
		try {
			// Obtiene categorias
			categorias = objectMapper.readValue(jsonData, new TypeReference<List<Categoria>>() {
			});
		} catch (JsonParseException e) {
			// Error cuando el archivo recibido no es un json.
			throw new JsonFormatoErroneo(ConstantesErrores.PARSEO_ERRONEO_CATEGORIA);
		} catch (JsonMappingException e) {
			// Error de mapeo cuadno la clase que se quiere obtener es distinta al contenido
			// del json.
			throw new JsonMapperException(ConstantesErrores.MAPPEO_ERRONEO_CATEGORIA);
		}

		//Se validan los atributos minimos
		FieldsValidator<Categoria> fv = new FieldsValidator<Categoria>();
		fv.validarAtributosRequeridos(categorias);

		// retorna lista de categorias
		return categorias;
	}
}