package br.com.una.projeto.repository;
import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.*;
import lombok.Builder;
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
public class PedidoRepositoryTest {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    BairroRepository bairroRepository;

    @Autowired
    RuaRepository ruaRepository;

    Pedido pedido;
    Pedido busca;
    Estado estado;
    Cidade cidade;
    Cliente cliente;
    Endereco endereco;
    Bairro bairro;
    Rua rua;

    @Before
    public void setup() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        enderecoRepository.deleteAll();
        bairroRepository.deleteAll();
        cidadeRepository.deleteAll();
        estadoRepository.deleteAll();

        estado = Estado.builder().nome("MG").build();
        estadoRepository.save(estado);

        cidade = Cidade.builder().nome("Uberlandia").build();
        cidadeRepository.save(cidade);

        bairro = Bairro.builder().nome("Tubalina").cidade(cidade).build();
        bairroRepository.save(bairro);

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

        rua = Rua.builder().cep("38412-000").logradouro("Av. Nicomedes Alves dos Santos").build();
        ruaRepository.save(rua);

        endereco = Endereco.builder().rua(rua).bairro(bairro).numero(666).cliente(cliente).build();
        enderecoRepository.save(endereco);

        pedido = Pedido.builder().cliente(cliente).endereco(endereco).build();
    }

    @Test
    public void should_save_pedido() {
        pedidoRepository.save(pedido);
        Assert.assertNotNull(pedido);
    }

    @Test
    public void should_find_pedido() {
        pedidoRepository.save(pedido);
        busca = pedidoRepository.findOne(pedido.getId());
        Assert.assertNotNull(busca);
    }

    @Test
    public void should_delete_pedido() {
        pedidoRepository.save(pedido);
        pedidoRepository.delete(pedido.getId());
        busca = pedidoRepository.findOne(pedido.getId());
        Assert.assertNull(busca);
    }
}
