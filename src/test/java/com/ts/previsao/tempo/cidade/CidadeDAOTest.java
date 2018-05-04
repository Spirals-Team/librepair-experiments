package com.ts.previsao.tempo.cidade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CidadeDAOTest {

	private CidadeDAO cidadeDao;
	private CidadeRepository cidadeRepository;

	@Before
	public void setUp() {
		this.cidadeDao = new CidadeDAO();
		this.cidadeRepository = new CidadeRepository();
		this.createCidadeRepositoryStub();
	}

	@Test
	public void testa_se_gera_a_mesma_instancia() {
		assertSame(CidadeDAO.getCidadeDao(), CidadeDAO.getCidadeDao());
	}

	@Test
	public void testa_se_cria_tabela_corretamente() {
		assertTrue(this.cidadeDao.createTableCidade());
	}

	@Test
	public void testa_se_insere_cidade_corretamente() throws Exception {
		assertTrue(this.cidadeDao.insertCidade(this.cidadeRepository));
	}

	@Test
	public void testa_se_nao_insere_cidade_se_estiver_incorreto() throws Exception {
		assertFalse(this.cidadeDao.insertCidade(null));
	}

	@Test
	public void testa_se_seleciona_todas_cidades() {
		assertTrue(this.cidadeDao.selectAllCidade() != null);
	}

	@Test
	public void testa_se_atualiza_com_sucesso() {
		assertTrue(this.cidadeDao.atualizaCidade(this.cidadeRepository));
	}

	@Test
	public void testa_se_nao_atualiza_com_sucesso() {
		assertFalse(this.cidadeDao.atualizaCidade(null));
	}

	public void createCidadeRepositoryStub() {
		this.cidadeRepository.setAtualizacao("01/01/2001");
		this.cidadeRepository.setId(001);
		this.cidadeRepository.setNome("test");
		this.cidadeRepository.setUf("aa");
	}

}
