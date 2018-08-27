package JoaoVFG.com.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.Telefone;
import JoaoVFG.com.github.repositories.CepRepository;
import JoaoVFG.com.github.repositories.EnderecoRepository;
import JoaoVFG.com.github.repositories.PessoaRepository;
import JoaoVFG.com.github.repositories.TelefoneRepository;
import JoaoVFG.com.github.repositories.TipoPessoaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsPessoaRepository {

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	TipoPessoaRepository tipo;

	@Autowired
	TelefoneRepository telefoneRepository;

	@Autowired
	EnderecoRepository endereceoRepository;

	@Autowired
	CepRepository cepRepository;

	@Test
	public void createPessoa() {
		Pessoa pessoaf = new Pessoa(null, tipo.findByid(1), "PAS", "11122233344", "18/06/1989", "M");
		pessoaRepository.save(pessoaf);
	}

	@Test
	public void findByTipo() {
		List<Pessoa> pessoas = pessoaRepository.findBytipo(tipo.findByid(1));
		for (List<Pessoa> p : Arrays.asList(pessoas)) {
			System.out.println(p.toString());
		}
	}

	@Test
	public void findByCpf() {
		Pessoa pessoa = pessoaRepository.findBycpf("45567860889");
		System.out.println(pessoa.getNome());
		assertEquals("JV", pessoa.getNome());

	}

	@Test
	public void findByCnpj() {
		Pessoa pessoa = pessoaRepository.findBycnpj("898725950008");
		assertEquals("mandou X", pessoa.getRazaoSocial());
	}

	@Test
	public void testFindRazaoSocialContains() {
		List<Pessoa> pessoas = pessoaRepository.findByrazaoSocialContains("trans");
		assertNotNull(pessoas);
	}

	@Test
	public void testFindTelefonesDePessoa() {
		List<Telefone> telefones = telefoneRepository.findBypessoa(pessoaRepository.findBycpf("45567860889"));
		assertNotNull(telefones);
	}

	@Test
	public void testFindTelefonesDePessoa2() {
		List<Telefone> telefones = telefoneRepository.findBypessoa(pessoaRepository.findBycnpj("485874810008"));
		assertNotNull(telefones);
	}

	@Test
	public void testeFindTelefonesByTipoTelefoneEPessoa() {
		List<Telefone> telefones = telefoneRepository
				.findBypessoaAndtipoNumero(pessoaRepository.findBycpf("58963221474").getId(), "Celular");
		assertNotNull(telefones);
	}
	/**
	@Test
	public void testFindEnderecoByPessoa() {
		List<Endereco> endereco = endereceoRepository.findBypessoa(pessoaRepository.findBycpf("45567860889"));
		assertNotNull(endereco);
	}**/

	@Test
	public void testFindEnderecosCep() {
		List<Endereco> enderecos = endereceoRepository.findBycep(cepRepository.findBycep("12288560"));
		assertNotNull(enderecos);
	}
}
