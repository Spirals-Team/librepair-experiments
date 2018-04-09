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

    CarrinhoRepository carrinhoRepository;
    ItemRepository itemRepository;
    ProdutoRepository produtoRepository;
    MarcaRepository marcaRepository;

    @Autowired
    CarrinhoRepositoryTest(CarrinhoRepository carrinhoRepository, ItemRepository itemRepository,
                            ProdutoRepository produtoRepository, MarcaRepository marcaRepository){
        this.carrinhoRepository = carrinhoRepository;
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
        this.marcaRepository = marcaRepository;
    }

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
                .precoVenda(12.0).precoCusto(7.00).descontoMax(50.0)
                .marca(marca)
                .descricao("Desodorante aerosol")
                .unidadeMedida("vidro").build());

        item = itemRepository.save(Item.builder().produto(produto).build());
    }

    @Test
    public void should_save_carrinho() {
        carrinho = carrinhoRepository.save(Carrinho.builder().produto(produto).item(item).build());
        Assert.assertTrue(carrinhoRepository.exists(carrinho.getId()));
    }

    @Test
    public void should_find_carrinho() {
        carrinho = carrinhoRepository.save(Carrinho.builder().produto(produto).item(item).build());
        busca = carrinhoRepository.findOne(carrinho.getId());
        Assert.assertTrue(carrinhoRepository.exists(busca.getId()));
    }

    @Test
    public void should_delete_carrinho() {
        carrinho = carrinhoRepository.save(Carrinho.builder().produto(produto).item(item).build());
        carrinhoRepository.delete(carrinho.getId());
        busca = carrinhoRepository.findOne(carrinho.getId());
        Assert.assertNull(busca);
    }
}
