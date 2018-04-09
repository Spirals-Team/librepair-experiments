package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Categoria;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = ProjetoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoriaRepositoryTeste {

    CategoriaRepository categoriaRepository;

    @Autowired
    CategoriaRepositoryTeste(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    Categoria categoria;
    Categoria busca = null;

    @Before
    public void setup() {
        Categoria estado = Categoria.builder().nome("Limpeza").build();
    }

    @Test
    public void should_save_item() {
        Assert.assertTrue(categoriaRepository.exists(categoria.getId()));
    }

    @Test
    public void should_find_item() {
        busca = categoriaRepository.findOne(categoria.getId());
        Assert.assertTrue(categoriaRepository.exists(busca.getId()));
    }

    @Test
    public void should_delete_item() {
        categoriaRepository.delete(categoria.getId());
        busca = categoriaRepository.findOne(categoria.getId());
        Assert.assertNull(busca);
    }

}
