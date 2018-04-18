package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Estado;
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
public class EstadoRepositoryTest {

    @Autowired
    EstadoRepository estadoRepository;

    Estado estado;
    Estado busca;

    @Before
    public void setup() {
        estado = Estado.builder().nome("Minas Gerais").uf("MG").build();
        estadoRepository.deleteAll();
    }

    @Test
    public void should_save_item() {
        estadoRepository.save(estado);
        Assert.assertNotNull(estado);
    }

    @Test
    public void should_find_item() {
        estadoRepository.save(estado);
        busca = estadoRepository.findOne(estado.getId());
        Assert.assertNotNull(estado);
    }

    @Test
    public void should_delete_item() {
        estadoRepository.save(estado);
        estadoRepository.delete(estado.getId());
        busca = estadoRepository.findOne(estado.getId());
        Assert.assertNull(busca);
    }
}
