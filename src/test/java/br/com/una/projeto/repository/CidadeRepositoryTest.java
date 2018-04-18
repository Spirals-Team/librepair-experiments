package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Cidade;
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
public class CidadeRepositoryTest {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    Cidade cidade;
    Cidade busca;
    Estado estado;

    @Before
    public void setup() {
        cidadeRepository.deleteAll();
        estadoRepository.deleteAll();

        estado = Estado.builder().nome("Minas Gerais").uf("MG").build();

        estado = estadoRepository.save(estado);
    }

    @Test
    public void should_save_cidade() {
        cidade = cidadeRepository.save(Cidade.builder().nome("uberlandia").estado(estado).build());
        Assert.assertTrue(cidadeRepository.exists(cidade.getId()));
    }

    @Test
    public void should_find_cidade() {
        cidade = cidadeRepository.save(Cidade.builder().nome("uberlandia").estado(estado).build());
        busca = cidadeRepository.findOne(cidade.getId());
        Assert.assertTrue(cidadeRepository.exists(busca.getId()));
    }

    @Test
    public void should_delete_cidade() {
        cidade = cidadeRepository.save(Cidade.builder().nome("uberlandia").estado(estado).build());
        cidadeRepository.delete(cidade.getId());
        busca = cidadeRepository.findOne(cidade.getId());
        Assert.assertNull(busca);
    }
}
