package br.com.una.projeto.repository;

import br.com.una.projeto.ProjetoApplication;
import br.com.una.projeto.entity.Cliente;
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
public class ClienteRepositoryTest {

	 @Autowired
	 ClienteRepository clienteRepository;

	 Cliente cliente;
	 Cliente busca;

 	@Before
 	public void setup() {
	 	cliente = Cliente.builder()
				 .nome("Jose")
				 .sobrenome("Buscapé")
				 .senha("abc")
				 .cpf("12345678910")
				 .email("jose.b@gmail.com")
				 .telefone("99982-3784")
				 .telefone("3216-8392")
				 .build();
		clienteRepository.deleteAll();
 	}

 	@Test
	public void should_save_cliente() {
		clienteRepository.save(cliente);
		Assert.assertNotNull(cliente);
	}

	@Test
	public void should_find_cliente() {
		clienteRepository.save(cliente);
		busca = clienteRepository.findOne(cliente.getId());
		Assert.assertNotNull(busca);
	}

	@Test
	public void should_delete_cliente() {
		clienteRepository.save(cliente);
		clienteRepository.delete(cliente.getId());
		busca = clienteRepository.findOne(cliente.getId());
		Assert.assertNull(busca);
	}
}

