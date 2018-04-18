package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Marca;
import br.com.una.projeto.entity.Produto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProdutoRepositoryTest {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    Produto produto;
    Produto busca;
    Marca marca;

    @Before
    public void setup() {
        produtoRepository.deleteAll();
        marcaRepository.deleteAll();

        marca = marcaRepository.save(Marca.builder().descricao("Sadia").build());

        produto = Produto.builder()
                .nome("Hamburger")
                .precoCusto(200.0)
                .precoVenda(200.0)
                .descontoMax(0.0)
                .marca(marca)
                .descricao("carne bovina")
                .unidadeMedida("100g")
                .build();
    }

    @Test
    public void should_save_produto() {
        produto = produtoRepository.save(produto);
        Assert.assertNotNull(produto);
    }

    @Test
    public void should_find_produto() {
        produto = produtoRepository.save(produto);
        busca = produtoRepository.findOne(produto.getId());
        Assert.assertNotNull(produto);
    }

    @Test
    public void should_delete_produto() {
        produto = produtoRepository.save(produto);
        produtoRepository.delete(produto.getId());
        busca = produtoRepository.findOne(produto.getId());
        Assert.assertNull(busca);
    }
}
