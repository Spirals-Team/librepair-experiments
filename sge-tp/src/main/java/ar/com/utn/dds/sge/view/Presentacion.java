package ar.com.utn.dds.sge.view;

import java.util.Calendar;
import java.util.List;

import ar.com.utn.dds.sge.mappers.JsonMapper;
import ar.com.utn.dds.sge.models.Administrador;
import ar.com.utn.dds.sge.models.Categoria;
import ar.com.utn.dds.sge.models.Cliente;

/**
 * Clase creada solo para mostrar un ejemplo de funcionamiento por consola.
 * @author Grupo 2
 *
 */
public class Presentacion {

	private static final String PATH_JSON_CLIENTES = "src/main/resources/Clientes.json";
	private static final String PATH_JSON_ADMINISTRADORES = "src/main/resources/Administradores.json";
	private static final String PATH_JSON_CATEGORIAS = "src/main/resources/Categorias.json";
	
	public static void mostrarConsola() {
		int i, j;
		JsonMapper mapper = new JsonMapper();
		List<Cliente> clientesJson;

		try {
			clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));
			
			System.out.printf("%s\n\n","Clientes:");
			
			for (i = 0; i < clientesJson.size(); i++) {
				System.out.println("Cliente " + (i + 1));
				System.out.println("Usuario: " + clientesJson.get(i).getUserName());
				System.out.println("Password: " + clientesJson.get(i).getPassword());
				System.out.println("Nombre: " + clientesJson.get(i).getNombre());
				System.out.println("Apellido: " + clientesJson.get(i).getApellido());
				System.out.println("Domicilio: " + clientesJson.get(i).getDomicilio());
				System.out.println("Fecha de Alta: " + clientesJson.get(i).getFechaAlta().get(Calendar.DAY_OF_MONTH)
						+ "/" + clientesJson.get(i).getFechaAlta().get(Calendar.MONTH) + "/"
						+ clientesJson.get(i).getFechaAlta().get(Calendar.YEAR));
				System.out.println(
						"Documento: " + clientesJson.get(i).getTipoDoc() + " " + clientesJson.get(i).getNroDoc());
				System.out.println("Telefono: " + clientesJson.get(i).getTelefono());
				System.out.println("Categoria: " + clientesJson.get(i).getCategoria().getNombre());
				System.out.println("Dispositivos:");

				for (j = 0; j < clientesJson.get(i).getDispositivos().size(); j++) {
					System.out.println("   Dispositivo " + (j + 1));
					System.out.println("   Tipo: " + clientesJson.get(i).getDispositivos().get(j).getTipo());
					System.out.println("   Nombre: " + clientesJson.get(i).getDispositivos().get(j).getNombre());
					System.out.println("   Consumo: " + clientesJson.get(i).getDispositivos().get(j).getConsumo());
					System.out.println("------------------------------");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("");

		List<Administrador> administradoresJson;

		try {
			administradoresJson = mapper.extraerAdministradoresJson(mapper.leerArchivo(PATH_JSON_ADMINISTRADORES));

			System.out.println("Administradores:");
			System.out.println("");
			for (i = 0; i < administradoresJson.size(); i++) {
				System.out.println("ID: " + administradoresJson.get(i).getId());
				System.out.println("Usuario: " + administradoresJson.get(i).getUserName());
				System.out.println("Password: " + administradoresJson.get(i).getPassword());
				System.out.println("Nombre: " + administradoresJson.get(i).getNombre());
				System.out.println("Apellido: " + administradoresJson.get(i).getApellido());
				System.out.println("Domicilio: " + administradoresJson.get(i).getDomicilio());
				System.out.println(
						"Fecha de Alta: " + administradoresJson.get(i).getFechaAlta().get(Calendar.DAY_OF_MONTH) + "/"
								+ administradoresJson.get(i).getFechaAlta().get(Calendar.MONTH) + "/"
								+ administradoresJson.get(i).getFechaAlta().get(Calendar.YEAR));
				System.out.println("");
			}
			System.out.println("");

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		List<Categoria> categoriasJson;

		try {
			categoriasJson = mapper.extraerCategoriasJson(mapper.leerArchivo(PATH_JSON_CATEGORIAS));
			System.out.println("Categorias:");
			System.out.println("");
			for (i = 0; i < categoriasJson.size(); i++) {
				System.out.println("Categoria: " + categoriasJson.get(i).getNombre());
				System.out.println("Cargo fijo: " + categoriasJson.get(i).getCargoFijo());
				System.out.println("Cargo variable: " + categoriasJson.get(i).getCargoVariable());
				System.out.println("");
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}

	}

}
