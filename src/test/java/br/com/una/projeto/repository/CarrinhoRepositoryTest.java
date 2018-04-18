package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Carrinho;
import br.com.una.projeto.entity.Item;
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
public class CarrinhoRepositoryTest {

    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    Carrinho carrinho;
    Carrinho busca;
    Marca marca;
    Produto produto;
    Item item;

    @Before
    public void setup() {
        carrinhoRepository.deleteAll();
        itemRepository.deleteAll();
        produtoRepository.deleteAll();
        marcaRepository.deleteAll();

        marca = marcaRepository.save(Marca.builder().descricao("Johnson").build());

        produto = produtoRepository.save(Produto.builder().nome("Desodorante")
                .precoVenda(12.0).precoCusto(7.0).descontoMax(50.0)
                .marca(marca)
                .descricao("Desodorante aerosol")
                .unidadeMedida("vidro").build());

        item = itemRepository.save(Item.builder().produto(produto).build());

        carrinho = Carrinho.builder().item(item).build();
    }

    @Test
    public void should_save_carrinho() {
        carrinho = carrinhoRepository.save(carrinho);
        Assert.assertNotNull(carrinho);
    }

    @Test
    public void should_find_carrinho() {
        carrinho = carrinhoRepository.save(carrinho);
        busca = carrinhoRepository.findOne(carrinho.getId());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_carrinho() {
        carrinho = carrinhoRepository.save(carrinho);
        carrinhoRepository.delete(carrinho.getId());
        busca = carrinhoRepository.findOne(carrinho.getId());
        Assert.assertNull(busca);
    }
}
