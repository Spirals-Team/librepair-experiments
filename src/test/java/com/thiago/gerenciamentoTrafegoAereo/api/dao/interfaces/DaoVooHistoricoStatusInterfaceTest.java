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

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.VooHistoricoStatus;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.VooHistoricoStatusRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DaoVooHistoricoStatusInterfaceTest
{
	
	@MockBean
	private VooHistoricoStatusRepositorio vooHistoricoStatusRepositorio;
	
	@Autowired
	private DaoVooHistoricoStatusInterface daoVooHistoricoStatusInterface;
	
	private static final Long VOO_HISTORICO_STATUS_CODIGO = 1L;
	
	@Before
	public void inicializar() throws Exception
	{
		BDDMockito.given(this.vooHistoricoStatusRepositorio.findByCodigo(Mockito.anyLong())).willReturn(new VooHistoricoStatus());
		BDDMockito.given(this.vooHistoricoStatusRepositorio.save(Mockito.any(VooHistoricoStatus.class))).willReturn(new VooHistoricoStatus());
		BDDMockito.given(this.vooHistoricoStatusRepositorio.findAll()).willReturn(new ArrayList<VooHistoricoStatus>());
	}
	
	@Test
	public void testBuscaPorId()
	{
		Optional<VooHistoricoStatus> vooHistoricoStatus = daoVooHistoricoStatusInterface.buscarPorId(VOO_HISTORICO_STATUS_CODIGO);
		assertTrue(vooHistoricoStatus.isPresent());
	}
	
	@Test
	public void testInserir()
	{
		Optional<VooHistoricoStatus> vooHistoricoStatus = daoVooHistoricoStatusInterface.inserir(new VooHistoricoStatus());
		assertTrue(vooHistoricoStatus.isPresent());
	}
	
	@Test
	public void testListar()
	{
		List<VooHistoricoStatus> vooHistoricoStatus = daoVooHistoricoStatusInterface.listar();
		assertNotNull(vooHistoricoStatus);
	}

}
