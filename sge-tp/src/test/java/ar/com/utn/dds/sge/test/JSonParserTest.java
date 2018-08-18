package ar.com.utn.dds.sge.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.com.utn.dds.sge.constants.ConstantesErrores;
import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;
import ar.com.utn.dds.sge.exceptions.JsonMapperException;
import ar.com.utn.dds.sge.exceptions.JsonNoEncontradoException;
import ar.com.utn.dds.sge.mappers.JsonMapper;
import ar.com.utn.dds.sge.models.Cliente;

public class JSonParserTest {

	JsonMapper mapper = new JsonMapper();
	List<Cliente> clientesJson = new ArrayList<>();
	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();
	String PATH_JSON_CLIENTES = "src/test/Clientes.json";
	String PATH_JSON_CLIENTES_EXCEPTION = "src/test/resources/ClientesException.json";
	String PATH_JSON_ADMINISTRADORES_EXCEPTION = "src/test/resources/AdministradoresException.json";

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void crearDatosDePrueba() throws IOException, ParseException {
		expectedEx.expect(JsonMapperException.class);
		expectedEx.expectMessage(ConstantesErrores.MAPPEO_ERRONEO_CLIENTE);

		// Se obtiene lista de clientes mapeados desde archivo json
		clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES_EXCEPTION));

		prueba.crearDatosDePrueba();
	}

	@Test
	public void pruebaExcepcionJson() throws IOException {
		expectedEx.expect(JsonNoEncontradoException.class);
		expectedEx.expectMessage(ConstantesErrores.ARCHIVO_NO_ENCONTRADO);

		// Prueba ruta del json incorrecta
		clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));

	}

	@Test
	public void testJsonFormatoErroneo() throws IOException, ParseException {
		expectedEx.expect(JsonMapperException.class);
		expectedEx.expectMessage(ConstantesErrores.MAPPEO_ERRONEO_CLIENTE);

		// Se obtiene lista de clientes mapeados desde archivo json
		clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_ADMINISTRADORES_EXCEPTION));

		prueba.crearDatosDePrueba();
	}

}
