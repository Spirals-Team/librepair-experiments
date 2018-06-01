package com.economizate;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.economizate.datos.ListaUsuarios;
import com.economizate.entidades.Usuario;


public class UsuarioTest {
	
	private ListaUsuarios usuarios;
	
	@Before
	public void llenarLista() {
		usuarios = new ListaUsuarios();
		usuarios.agregarUsuario("pepe", "gonzalez", "pepeGonzalez@gmail.com");
		usuarios.agregarUsuario("juan", "sanchez", "juanSanchez@gmail.com");
		usuarios.agregarUsuario("carlos", "gaitan", "elGaita@gmail.com");
	}
	
	@Test
	public void buscarUnUsuarioPorEmailYEncontrarElUsuarioOK() throws Exception {
		Usuario usuario = usuarios.buscarUsuarioPorEmail("pepeGonzalez@gmail.com");
		assertEquals("Buscar usuario: ", usuario!= null ? usuario.getEmail() : null, "pepeGonzalez@gmail.com");
		assertEquals("Buscar usuario: ", usuario!= null ? usuario.getApellido() : null, "gonzalez");
		assertEquals("Buscar usuario: ", usuario!= null ? usuario.getNombre() : null, "pepe");
	}
	
	@Test
	public void buscarUnUsuarioQueNoEstaEnLaListaYObtenerExcepcion() {
		try{
			usuarios.buscarUsuarioPorEmail("pepu@gmail.com");
			Assert.fail("Test fallido");
		}catch(Exception e) {
			assertEquals("Excepción no encuentro usuario", e.getMessage(), "No se encontró el usuario");
		}
	}
	
	@Test
	public void buscarUnUsuarioPorEmailYChequearSuSaldoInicialEnCeroConExito() throws Exception {
		Usuario usuario = usuarios.buscarUsuarioPorEmail("pepeGonzalez@gmail.com");
		assertEquals("Buscar saldo en cero: ", usuario!= null ? usuario.getSaldo().getTotal() : null, new Double(0));
	}
	
	@Test
	public void crearUsuarioVacioLuegoModificarloYObtenerConsultasDeUsuaioOK() {
		Usuario usuario = new Usuario();
		usuario.setNombre("seba");
		usuario.setApellido("sanchaez");
		usuario.setEmail("sebaSanchez@gmail.com");
		
		assertTrue("Usuario tiene nombre ok: ", "seba".equals(usuario.getNombre()));
		assertTrue("Usuario tiene apellido ok: ", "sanchaez".equals(usuario.getApellido()));
		assertTrue("Usuario tiene mail ok: ", "sebaSanchez@gmail.com".equals(usuario.getEmail()));
	}
	
}
