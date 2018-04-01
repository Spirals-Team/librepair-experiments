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

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Voo;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.VooRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DaoVooInterfaceTest
{
	@MockBean
	private VooRepositorio vooRepositorio;
	
	@Autowired
	private DaoVooInterface daoVooInterface;
	
	private static final Long VOO_HISTORICO_STATUS_CODIGO = 1L;
	
	@Before
	public void inicializar() throws Exception
	{
		BDDMockito.given(this.vooRepositorio.findByCodigo(Mockito.anyLong())).willReturn(new Voo());
		BDDMockito.given(this.vooRepositorio.save(Mockito.any(Voo.class))).willReturn(new Voo());
		BDDMockito.given(this.vooRepositorio.findAll()).willReturn(new ArrayList<Voo>());
	}
	
	@Test
	public void testBuscaPorId()
	{
		Optional<Voo> voo = daoVooInterface.buscarPorId(VOO_HISTORICO_STATUS_CODIGO);
		assertTrue(voo.isPresent());
	}
	
	@Test
	public void testInserir()
	{
		Optional<Voo> voo = daoVooInterface.inserir(new Voo());
		assertTrue(voo.isPresent());
	}
	
	@Test
	public void testListar()
	{
		List<Voo> alVoo = daoVooInterface.listar();
		assertNotNull(alVoo);
	}
}
