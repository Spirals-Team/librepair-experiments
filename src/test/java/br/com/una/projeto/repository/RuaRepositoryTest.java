package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Marca;
import br.com.una.projeto.entity.Rua;
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
public class RuaRepositoryTest {

    @Autowired
    RuaRepository ruaRepository;

    Rua rua;
    Rua busca;

    @Before
    public void setup() {
        ruaRepository.deleteAll();
        rua = Rua.builder().cep("38412-000").logradouro("Av. Nicomedes Alves dos Santos").build();
    }

    @Test
    public void should_save_rua() {
        ruaRepository.save(rua);
        Assert.assertNotNull(rua);
    }

    @Test
    public void should_find_rua() {
        ruaRepository.save(rua);
        busca = ruaRepository.findOne(rua.getCep());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_rua() {
        ruaRepository.save(rua);
        ruaRepository.delete(rua.getCep());
        busca = ruaRepository.findOne(rua.getCep());
        Assert.assertNull(busca);
    }
}
