package com.concrete.ecommerce.api.repositories;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.ecommerce.api.entities.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoRepositoryTest {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	private Long produtoId;

	@Before
	public void setUp() throws Exception {
		Produto produto = this.produtoRepository.save(obterDadosProduto());
		this.produtoId = produto.getId();
	}

	@After
	public void tearDown() throws Exception {
		this.produtoRepository.deleteAll();
	}

	@Test
	public void testBuscarProdutoPorUsuarioId() {
		Produto produto = this.produtoRepository.findOne(produtoId);
		assertNotNull(produto);
	}
	
	
	private Produto obterDadosProduto() {
		Produto produto = new Produto();
		produto.setValor(459.00);
		produto.setDescricao("Televisão");
		return produto;
	}



}
