package com.ts.previsao.tempo.previsao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PrevisaoDAOTest {

	private PrevisaoDAO previsaoDao;
	private PrevisaoRepository previsaoRepository;
	private List<PrevisaoRepository> previsaoList;

	@Before
	public void setUp() {
		this.previsaoDao = new PrevisaoDAO();
		this.previsaoRepository = new PrevisaoRepository();
		this.previsaoList = new ArrayList<PrevisaoRepository>();
		this.previsaoList.add(previsaoRepository);
		this.createCidadeRepositoryStub();

	}

	@Test
	public void testa_se_gera_a_mesma_instancia() {
		assertSame(PrevisaoDAO.getPrevisaoDAO(), PrevisaoDAO.getPrevisaoDAO());
	}

	@Test
	public void testa_se_cria_tabela_corretamente() {
		assertTrue(this.previsaoDao.createTablePrevisao());
	}

	@Test
	public void testa_se_insere_cidade_corretamente() throws Exception {
		assertTrue(this.previsaoDao.insertPrevisao(this.previsaoRepository));
	}

	@Test
	public void testa_se_nao_insere_cidade_se_estiver_incorreto() throws Exception {
		assertFalse(this.previsaoDao.insertPrevisao(null));
	}

	@Test
	public void testa_se_seleciona_todas_cidades() {
		assertTrue(this.previsaoDao.selectAllPrevisao(1) != null);
	}

	@Test
	public void testa_se_remove_com_sucesso() {
		assertTrue(this.previsaoDao.removeAllPrevisao(1));
	}

	public void createCidadeRepositoryStub() {
		this.previsaoRepository.setDia("01/01/2001");
		this.previsaoRepository.setId(001);
		this.previsaoRepository.setIuv(0.0);
		this.previsaoRepository.setMaxima(0.0);
		this.previsaoRepository.setMinima(0.0);
		this.previsaoRepository.setTempo("aa");
	}

}
