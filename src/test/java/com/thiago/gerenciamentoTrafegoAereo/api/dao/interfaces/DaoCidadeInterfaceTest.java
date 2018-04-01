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

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.CidadeRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DaoCidadeInterfaceTest
{
	@MockBean
	private CidadeRepositorio cidadeRepositorio;
	
	@Autowired
	private DaoCidadeInterface daoCidadeInterface;
	
	private static final Long CIDADE_CODIGO = 1L;
	
	@Before
	public void inicializar() throws Exception
	{
		BDDMockito.given(this.cidadeRepositorio.findByCodigo(Mockito.anyLong())).willReturn(new Cidade());
		BDDMockito.given(this.cidadeRepositorio.save(Mockito.any(Cidade.class))).willReturn(new Cidade());
		BDDMockito.given(this.cidadeRepositorio.findAll()).willReturn(new ArrayList<Cidade>());
	}
	
	@Test
	public void testBuscaPorId()
	{
		Optional<Cidade> cidade = daoCidadeInterface.buscarPorId(CIDADE_CODIGO);
		assertTrue(cidade.isPresent());
	}
	
	@Test
	public void testInserir()
	{
		Optional<Cidade> cidade = daoCidadeInterface.inserir(new Cidade());
		assertTrue(cidade.isPresent());
	}
	
	@Test
	public void testListar()
	{
		List<Cidade> alCidade = daoCidadeInterface.listar();
		assertNotNull(alCidade);
	}
}
