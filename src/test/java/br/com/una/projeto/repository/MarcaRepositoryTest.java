package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Marca;
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
public class MarcaRepositoryTest {

    @Autowired
    MarcaRepository marcaRepository;

    Marca marca;
    Marca busca;

    @Before
    public void setup() {
        marcaRepository.deleteAll();
        marca = Marca.builder().descricao("Limpeza").build();
    }

    @Test
    public void should_save_marca() {
        marcaRepository.save(marca);
        Assert.assertNotNull(marca);
    }

    @Test
    public void should_find_marca() {
        marcaRepository.save(marca);
        busca = marcaRepository.findOne(marca.getId());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_marca() {
        marcaRepository.save(marca);
        marcaRepository.delete(marca.getId());
        busca = marcaRepository.findOne(marca.getId());
        Assert.assertNull(busca);
    }
}
