package ar.com.utn.dds.sge.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;
import ar.com.utn.dds.sge.mappers.JsonMapper;
import ar.com.utn.dds.sge.models.Administrador;
import ar.com.utn.dds.sge.models.Categoria;
import ar.com.utn.dds.sge.models.Cliente;

public class JsonMapperTest {

	JsonMapper mapper = new JsonMapper();
	List<Cliente> clientesJson = new ArrayList<>();
	List<Categoria> categoriasJson = new ArrayList<>();
	List<Administrador> administradoresJson = new ArrayList<>();
	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();
	String PATH_JSON_CLIENTES = "src/test/resources/Clientes.json";
	String PATH_JSON_ADMINISTRADORES = "src/test/resources/Administradores.json";
	String PATH_JSON_CATEGORIAS = "src/test/resources/Categorias.json";

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * @throws IOException Se crean todas las entidades que se deberían crear en
	 * memoria luego de parsear el archivo json. @throws ParseException @throws
	 */

	@Before
	public void crearDatosDePrueba() throws ParseException, IOException {

		// Se obtiene lista de clientes mapeados desde archivo json
		clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));
		administradoresJson = mapper.extraerAdministradoresJson(mapper.leerArchivo(PATH_JSON_ADMINISTRADORES));
		categoriasJson = mapper.extraerCategoriasJson(mapper.leerArchivo(PATH_JSON_CATEGORIAS));

		prueba.crearDatosDePrueba();
	}

	@Test
	public void testCargaDeClienteJson() throws IOException {
		assertEquals(prueba.cliente1, clientesJson.get(0));
	}

	@Test
	public void testCargaCategoriaDeCliente() throws IOException {
		assertEquals(prueba.categorias.get("R1"), clientesJson.get(0).getCategoria());
	}

	@Test
	public void testCargaDispositivosDeUnCliente() throws IOException {
		assertEquals(prueba.cliente1.getDispositivos(), clientesJson.get(0).getDispositivos());
	}

	@Test
	public void testCargaDeDispositivoDeUnCliente() throws IOException {
		assertEquals(prueba.cliente1.getDispositivos().get(0), clientesJson.get(0).getDispositivos().get(0));
	}

	@Test
	public void testCargaDeDispositivoEncendidoDeCliente() throws IOException {
		assertTrue(clientesJson.get(1).getDispositivos().get(0).getEstado());
	}

}
