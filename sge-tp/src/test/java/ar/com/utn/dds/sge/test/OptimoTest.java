package ar.com.utn.dds.sge.test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sun.el.parser.ParseException;


import ar.com.utn.dds.sge.commands.Optimo;
import ar.com.utn.dds.sge.creationals.CasosDePruebaBuilder;


public class OptimoTest {

	CasosDePruebaBuilder prueba = new CasosDePruebaBuilder();
	/**
	 * Se crean todas las entidades que se deberían crear en memoria luego de
	 * parsear el archivo json.
	 * 
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */

	@Before
	public void crearDatosDePrueba() throws ParseException, java.text.ParseException {
		prueba.crearDatosDePrueba();
	}
	@Test
	public void calcularSimplexPasadoConsumoTest() {
		System.out.println("Se correra la funcionalidad de Consumo optimo para alguien que se paso de consumo:");		
		System.out.println("Se correra la funcionalidad de Consumo optimo:");
		Optimo optimo = new Optimo(prueba.cliente1, prueba.cliente1.getDispositivos().size());
		System.out.println("El Optimo para el cliente 1 es:");
		System.out.println(prueba.cliente1.getConsumoOptimo());
		for (int i = 0; i<prueba.cliente1.getDispositivos().size(); i++){
			System.out.println("El dispositivo:");
			System.out.println(prueba.cliente1.getDispositivos().get(i).getNombre());
			System.out.println("Horas Restantes:");
			System.out.println(prueba.cliente1.getDispositivos().get(i).getHsRestantes());	   
			  }
	}
		@Test
		public void calcularSimplexConsumoOkTest() {
			System.out.println("Se correra la funcionalidad de Consumo optimo:");
			Optimo optimo = new Optimo(prueba.cliente3, prueba.cliente3.getDispositivos().size());
			System.out.println("El Optimo para el cliente 3 es:");
			System.out.println(prueba.cliente3.getConsumoOptimo());
			for (int i = 0; i<prueba.cliente3.getDispositivos().size(); i++){
				System.out.println("El dispositivo:");
				System.out.println(prueba.cliente3.getDispositivos().get(i).getNombre());
				System.out.println("Horas Restantes:");
				System.out.println(prueba.cliente3.getDispositivos().get(i).getHsRestantes());	   
				  }
		
	}

}
