package br.com.una.projeto.repository;
import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Bairro;
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
@Transactional
@SpringBootTest(classes = ProjetoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BairroRepositoryTest {

    @Autowired
    BairroRepository bairroRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    Bairro bairro;
    Bairro busca;
    Estado estado;
    Cidade cidade;

    @Before
    public void setup() {
        estadoRepository.deleteAll();
        cidadeRepository.deleteAll();
        bairroRepository.deleteAll();

        estado = Estado.builder().nome("Minas Gerais").build();
        estadoRepository.save(estado);

        cidade = Cidade.builder().nome("Uberaba").estado(estado).build();
        cidadeRepository.save(cidade);

        bairro = Bairro.builder().nome("Centro").cidade(cidade).build();
    }

    @Test
    public void should_save_bairro() {
        bairroRepository.save(bairro);
        Assert.assertNotNull(bairro);
    }

    @Test
    public void should_find_bairro() {
        bairroRepository.save(bairro);
        busca = bairroRepository.findOne(bairro.getId());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_bairro() {
        bairroRepository.save(bairro);
        bairroRepository.delete(bairro.getId());
        busca = bairroRepository.findOne(bairro.getId());
        Assert.assertNull(busca);
    }
}
