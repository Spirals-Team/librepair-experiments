package com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.PilotoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DaoPilotoInterfaceTest
{
	@MockBean
	private PilotoRepositorio pilotoRepositorio;
	
	@Autowired
	private DaoPilotoInterface daoPilotoInterface;
	
	private static final Long PILOTO_CODIGO = 1L;
	
	@Before
	public void inicializar() throws Exception
	{
		BDDMockito.given(this.pilotoRepositorio.findByCodigo(Mockito.anyLong())).willReturn(new Piloto());
		BDDMockito.given(this.pilotoRepositorio.save(Mockito.any(Piloto.class))).willReturn(new Piloto());
		BDDMockito.given(this.pilotoRepositorio.findAll()).willReturn(new ArrayList<Piloto>());
	}
	
	@Test
	public void testBuscaPorId()
	{
		Optional<Piloto> piloto = daoPilotoInterface.buscarPorId(PILOTO_CODIGO);
		assertTrue(piloto.isPresent());
	}
	
	@Test
	public void testInserir()
	{
		Optional<Piloto> piloto = daoPilotoInterface.inserir(new Piloto());
		assertTrue(piloto.isPresent());
	}
	
	@Test
	public void testListar()
	{
		List<Piloto> alPiloto = daoPilotoInterface.listar();
		assertNotNull(alPiloto);
	}
}
