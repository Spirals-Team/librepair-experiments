package br.com.una.projeto.repository;
import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.*;
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
public class EnderecoRepositoryTest {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    RuaRepository ruaRepository;

    @Autowired
    BairroRepository bairroRepository;

    @Autowired
    ClienteRepository clienteRepository;

    Endereco endereco;
    Endereco busca;
    Estado estado;
    Cidade cidade;
    Bairro bairro;
    Cliente cliente;
    Rua rua;

    @Before
    public void setup() {
        clienteRepository.deleteAll();
        enderecoRepository.deleteAll();
        ruaRepository.deleteAll();
        bairroRepository.deleteAll();
        cidadeRepository.deleteAll();
        estadoRepository.deleteAll();

        estado = Estado.builder().nome("Minas Gerais").build();
        estadoRepository.save(estado);

        cidade = Cidade.builder().nome("Uberlândia").estado(estado).build();
        cidadeRepository.save(cidade);

        bairro = Bairro.builder().nome("Tubalina").cidade(cidade).build();
        bairroRepository.save(bairro);

        rua = Rua.builder().cep("38412-000").logradouro("Av. Nicomedes Alves dos Santos").build();
        ruaRepository.save(rua);

        cliente = Cliente.builder()
                .nome("Jose")
                .sobrenome("Buscapé")
                .senha("abc")
                .cpf("12345678910")
                .email("jose.b@gmail.com")
                .telefone("99982-3784")
                .telefone("3216-8392")
                .build();
        clienteRepository.save(cliente);

        endereco = Endereco.builder().rua(rua).bairro(bairro).numero(666).cliente(cliente).build();
    }

    @Test
    public void should_save_endereco() {
        enderecoRepository.save(endereco);
        Assert.assertNotNull(endereco);
    }

    @Test
    public void should_find_endereco() {
        enderecoRepository.save(endereco);
        busca = enderecoRepository.findOne(endereco.getId());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_endereco() {
        enderecoRepository.save(endereco);
        enderecoRepository.delete(endereco.getId());
        busca = enderecoRepository.findOne(endereco.getId());
        Assert.assertNull(busca);
    }
}
