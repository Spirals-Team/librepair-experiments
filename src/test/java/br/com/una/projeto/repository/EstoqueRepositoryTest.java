package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Estoque;
import br.com.una.projeto.entity.Marca;
import br.com.una.projeto.entity.Produto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EstoqueRepositoryTest {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

    @Autowired
    MarcaRepository marcaRepository;

    Estoque estoque;
    Estoque busca;
    Marca marca;
    Produto produto;

    @Before
    public void setup() {
        estoqueRepository.deleteAll();
        produtoRepository.deleteAll();
        marcaRepository.deleteAll();

        marca = marcaRepository.save(Marca.builder().descricao("Johnson").build());

        produto = produtoRepository.save(Produto.builder().nome("Desodorante")
                .precoVenda(12.0).precoCusto(7.00).descontoMax(50.0)
                .marca(marca)
                .descricao("Desodorante aerosol")
                .unidadeMedida("vidro").build());

        estoque = Estoque.builder().produto(produto).quantidade(50).build();
    }

    @Test
    public void should_save_item() {
        estoqueRepository.save(estoque);
        Assert.assertNotNull(estoque);
    }

    @Test
    public void should_find_item() {
        estoqueRepository.save(estoque);
        busca = estoqueRepository.findOne(estoque.getId());
        Assert.assertNotNull(estoque);
    }

    @Test
    public void should_delete_item() {
        estoqueRepository.save(estoque);
        estoqueRepository.delete(estoque.getId());
        busca = estoqueRepository.findOne(estoque.getId());
        Assert.assertNull(busca);
    }

}
