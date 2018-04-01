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

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.AviaoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DaoAviaoInterfaceTest
{
	@MockBean
	private AviaoRepositorio aviaoRepositorio;
	
	@Autowired
	private DaoAviaoInterface daoAviaoInterface;
	
	private static final Long AVIAO_CODIGO = 1L;
	
	@Before
	public void inicializar() throws Exception
	{
		BDDMockito.given(this.aviaoRepositorio.findByCodigo(Mockito.anyLong())).willReturn(new Aviao());
		BDDMockito.given(this.aviaoRepositorio.save(Mockito.any(Aviao.class))).willReturn(new Aviao());
		BDDMockito.given(this.aviaoRepositorio.findAll()).willReturn(new ArrayList<Aviao>());
	}
	
	@Test
	public void testBuscaAviaoPorCodigo()
	{
		Optional<Aviao> aviao = this.daoAviaoInterface.buscarPorId(AVIAO_CODIGO);
		assertTrue(aviao.isPresent());
	}
	
	@Test
	public void testInserir()
	{
		Optional<Aviao> aviao = this.daoAviaoInterface.inserir(new Aviao());
		assertTrue(aviao.isPresent());
	}
	
	@Test
	public void testListar()
	{
		List<Aviao> alAviao = this.daoAviaoInterface.listar();
		assertNotNull(alAviao);
	}
}
