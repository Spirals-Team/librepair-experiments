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
public class CategoriaRepositoryTest {

    @Autowired
    CategoriaRepository categoriaRepository;

    Categoria categoria;
    Categoria busca;

    @Before
    public void setup() {
        categoria = Categoria.builder().nome("Limpeza").build();
    }

    @Test
    public void should_save_item() {
        categoriaRepository.save(categoria);
        Assert.assertNotNull(categoria);
    }

    @Test
    public void should_find_item() {
        categoriaRepository.save(categoria);
        busca = categoriaRepository.findOne(categoria.getId());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_item() {
        categoriaRepository.save(categoria);
        categoriaRepository.delete(categoria.getId());
        busca = categoriaRepository.findOne(categoria.getId());
        Assert.assertNull(busca);
    }
}
