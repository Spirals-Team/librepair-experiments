package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
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
public class ItemRepositoryTest {


    ItemRepository itemRepository;
    ProdutoRepository produtoRepository;
    MarcaRepository marcaRepository;

    @Autowired
    ItemRepositoryTest(ItemRepository itemRepository, ProdutoRepository produtoRepository,
                       MarcaRepository marcaRepository){
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
        this.marcaRepository = marcaRepository;
    }

    Item item;
    Item busca;
    Produto produto;
    Marca marca;

    @Before
    public void setup() {
        itemRepository.deleteAll();
        produtoRepository.deleteAll();
        marcaRepository.deleteAll();

        marca = marcaRepository.save(Marca.builder().descricao("Johnson").build());

        produto = produtoRepository.save(Produto.builder().nome("Desodorante")
                .precoVenda(12.0).precoCusto(7.00).descontoMax(50.0)
                .marca(marca)
                .descricao("Desodorante aerosol")
                .unidadeMedida("vidro").build());

        item = Item.builder().produto(produto).build();
    }

    @Test
    public void should_save_item() {
        itemRepository.save(item);
        Assert.assertTrue(itemRepository.exists(item.getId()));
    }

    @Test
    public void should_find_item() {
        itemRepository.save(item);
        busca = itemRepository.findOne(item.getId());
        Assert.assertTrue(itemRepository.exists(busca.getId()));
    }

    @Test
    public void should_delete_item() {
        itemRepository.save(item);
        itemRepository.delete(item.getId());
        busca = itemRepository.findOne(item.getId());
        Assert.assertNull(busca);
    }
}
