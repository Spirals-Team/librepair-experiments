package uo.asw.junitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import inciManager.uo.asw.InciManagerE3aApplication;
import inciManager.uo.asw.chatbot.Chat;

/**
 * Prueba la clase Chat
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class ChatTest {
	private Chat chat;
	
	@Before
	public void setUp() {
		chat = new Chat();
	}

	@Test
	public void inicializarTest() {
		assertTrue (chat.getMensajes().size() > 0);
	}
	
	@Test
	public void calcularRespuestaTest1() {
		//crear incidencia
		int num_mensajes = chat.getMensajes().size();
		chat.calcularRespuesta("CREAR");
		assertNotNull(chat.getInci());
		assertTrue(num_mensajes < chat.getMensajes().size());
	}

	@Test
	public void calcularRespuestaTest2() {
		//opcion listar
		int num_mensajes = chat.getMensajes().size();
		chat.calcularRespuesta("LISTA");
		assertNull(chat.getInci());
		assertTrue(num_mensajes < chat.getMensajes().size());
	}
	
	@Test
	public void calcularRespuestaTest3() {
		//saludo
		int num_mensajes = chat.getMensajes().size();
		chat.calcularRespuesta("HOLA");
		assertNull(chat.getInci());
		assertEquals(num_mensajes, chat.getMensajes().size());
	}
	
	@Test
	public void calcularRespuestaTest4() {
		//saludo
		int num_mensajes = chat.getMensajes().size();
		chat.calcularRespuesta("dfgsfgsg");
		assertNull(chat.getInci());
		assertTrue(num_mensajes < chat.getMensajes().size());
		assertEquals("Lo siento, no le he entendido", 
				chat.getMensajes().get(num_mensajes).getContenido());
	}
	
	@Test
	public void crearIncidenciaCorrectaTest() {
		assertEquals(chat.getMensajes().size(), 2);
		chat.calcularRespuesta("CREAR");
		//Comenzamos a crear la incidencia, introducimos su nombre.
		assertEquals(5, chat.getMensajes().size());
		chat.calcularRespuesta("Accidente aéreo");
		//Introducimos la descripción
		assertEquals(6, chat.getMensajes().size());
		chat.calcularRespuesta("Mucho fuego");
		//Introducimos la fecha
		assertEquals(8, chat.getMensajes().size());
		chat.calcularRespuesta("15/05/2018");
		//Introducimos las categorías
		assertEquals(10, chat.getMensajes().size());
		chat.calcularRespuesta("fuego,accidente_aereo");
		//Introducimos las propiedades
		assertEquals(12, chat.getMensajes().size());
		chat.calcularRespuesta("temperatura/120");
		assertEquals(14, chat.getMensajes().size());
	}
	
	@Test
	public void crearIncidenciaIncorrectaTest() {
		assertEquals(chat.getMensajes().size(), 2);
		chat.calcularRespuesta("CREAR");
		
		//Comenzamos a crear la incidencia, introducimos su nombre.
		assertEquals(5, chat.getMensajes().size());
		chat.calcularRespuesta("Accidente aéreo");
		
		//Introducimos la descripción
		assertEquals(6, chat.getMensajes().size());
		chat.calcularRespuesta("Mucho fuego");
		
		//Introducimos la fecha incorrecta
		assertEquals(8, chat.getMensajes().size());
		chat.calcularRespuesta("1505/2018");
		assertEquals(10, chat.getMensajes().size());
		//Comprobamos el ultimo mensaje
		assertEquals("Vuelva a introducir la fecha de caducidad que ha escogido para su incidencia", 
				chat.getMensajes().get(chat.getMensajes().size()-1).getContenido());
		
		//Introducimos la fecha correcta
		chat.calcularRespuesta("15/05/2018");
		assertEquals(12, chat.getMensajes().size());
		
		//Introducimos categorias erróneas
		chat.calcularRespuesta("fuego-inundacion");
		assertEquals(14, chat.getMensajes().size());
		//Comprobamos el ultimo mensaje
		assertEquals("Vuelva a introducir todas categorias que quiera", 
						chat.getMensajes().get(chat.getMensajes().size()-1).getContenido());
		
		//Introducimos categorias correctas		
		chat.calcularRespuesta("fuego,accidente_aereo");
		assertEquals(16, chat.getMensajes().size());
		
		//Introducimos las propiedades erroneas
		chat.calcularRespuesta("temperatura/hola");
		assertEquals(18, chat.getMensajes().size());
		//Comprobamos el ultimo mensaje
		assertEquals("Ups... no se han podido reconocer todas las propiedades. El valor de la propiedad debe ser un valor numérico", 
								chat.getMensajes().get(chat.getMensajes().size()-2).getContenido());
		
		//Introducimos las propiedades erroneas
		chat.calcularRespuesta("temperatura120/120");
		assertEquals(20, chat.getMensajes().size());
		//Comprobamos el ultimo mensaje
		assertEquals("Ups... no se han podido reconocer todas las propiedades. Las propiedades TEMPERATURA120/120 no están permitidas", 
										chat.getMensajes().get(chat.getMensajes().size()-2).getContenido());
		
		//Introducimos las propiedades correctas
		chat.calcularRespuesta("temperatura/120");
		assertEquals(22, chat.getMensajes().size());
	}
	
	

}
