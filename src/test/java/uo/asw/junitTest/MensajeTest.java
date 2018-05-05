package uo.asw.junitTest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uo.asw.InciManagerE3aApplication;
import uo.asw.chatbot.Mensaje;
import uo.asw.inciManager.util.DateUtil;

/**
 * Prueba la clase Mensaje utilizada en el Chat bot
 * 
 * @version abril 2018
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InciManagerE3aApplication.class)
@WebAppConfiguration
public class MensajeTest {

	private Mensaje mensaje;
	
	@Before
	public void setUp() {
		mensaje = new Mensaje(DateUtil.stringToDate("01/04/2018"), "prueba", "autor");
	}
	
	@Test
	public void mensajeTest() {
		mensaje.setFechaHora(DateUtil.stringToDate("01/05/2018"));
		assertEquals(DateUtil.stringToDate("01/05/2018"), mensaje.getFechaHora());
		mensaje.setContenido("contenido");
		assertEquals("contenido", mensaje.getContenido());
		mensaje.setAutor("otroAutor");
		assertEquals("otroAutor", mensaje.getAutor());
		
		
		
	}

}
