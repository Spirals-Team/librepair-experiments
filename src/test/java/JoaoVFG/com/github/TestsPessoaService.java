package JoaoVFG.com.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.Telefone;
import JoaoVFG.com.github.entity.TipoPessoa;
import JoaoVFG.com.github.service.CepService;
import JoaoVFG.com.github.service.EnderecoService;
import JoaoVFG.com.github.service.PessoaService;
import JoaoVFG.com.github.service.TelefoneService;
import JoaoVFG.com.github.service.TipoPessoaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsPessoaService {
	
	@Autowired
	TipoPessoaService tipoPessoaService;
	
	@Autowired
	PessoaService pessoaService;
	
	@Autowired
	TelefoneService telefoneService;
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	CepService cepService;
	
	@Test
	public void testeTipoPessoaBuscaTodos() {
		List<TipoPessoa> tiposPessoa= tipoPessoaService.findAll();
		assertNotNull(tiposPessoa);
	}
	
	@Test
	public void testeTipoPessoaBuscaNome() {
		TipoPessoa tipoPessoa = tipoPessoaService.findBydescricao("FISICA");
		assertEquals("1", tipoPessoa.getId().toString());
	}
	
	@Test
	public void testeTipoPessoaBusca() {
		TipoPessoa tipoPessoa = tipoPessoaService.findById(2);
		assertEquals("JURIDICA", tipoPessoa.getDescricao());
	}
	
	@Test
	public void testeInserePessoaJuridica() {
		Pessoa pessoa = new Pessoa(null, tipoPessoaService.findBydescricao("JURIDICA"), "ENVIAMOSJA","78474501000398");
		pessoaService.create(pessoa);
	}
	
	@Test
	public void testeInserePessoaFisica() {
		Pessoa pessoa = new Pessoa(null, tipoPessoaService.findBydescricao("FISICA"), "TESTE", "45665445689", "20/08/1977", "F");
		pessoaService.create(pessoa);
	}
	
	@Test
	public void testeBuscaPessoaPorId(){
		Pessoa pessoa = pessoaService.findById(1);
		assertEquals("JV", pessoa.getNome());
	}
	
	@Test
	public void testeBuscaTodasPessoas() {
		List<Pessoa> pessoas = pessoaService.findAll();
		assertNotNull(pessoas);
	}
	
	@Test
	public void testeBuscaPessoaPorCpf() {
		Pessoa pessoa = pessoaService.findByCpf("11593054807");
		assertEquals("JJ", pessoa.getNome());
	}
	
	@Test
	public void testeBuscaPessoaPorCnpj() {
		Pessoa pessoa = pessoaService.findByCnpj("4485478220008");
		assertEquals("trans B", pessoa.getRazaoSocial());
	}
	
	@Test
	public void testeBuscaPorRazaoSocial() {
		List<Pessoa> pessoas = pessoaService.findByrazaoSocial("trans");
		assertNotNull(pessoas);
	}
	
	@Test
	public void testeCreateTel() {
		Telefone telefone = new Telefone(null, "Celular", "12991918066", pessoaService.findById(5));
		telefoneService.create(telefone);
	}
	
	@Test
	public void testebuscaTelefonePorId() {
		Telefone telefone = telefoneService.findById(1);
		assertEquals("12991157861", telefone.getNumero());
	}
	
	@Test
	public void testeBuscaTelefonePorIdPessoa() {
		List<Telefone> telefones = telefoneService.findByPessoa(1);
		assertNotNull(telefones);
	}
	
	@Test
	public void testeBuscaTelefoneTodos() {
		List<Telefone> telefones = telefoneService.findAll();
		assertNotNull(telefones);
	}
	
	@Test
	public void testeBuscaTelPessoaETipoNum() {
		List<Telefone> telefones = telefoneService.findByPessoaTipoNum(pessoaService.findByCnpj("898725950008").getId(), "Celular");
		assertEquals(1, telefones.size());
	}
	
	@Test
	public void testeBuscaEnderecoPorId() {
		Endereco endereco = enderecoService.findById(3);
		assertEquals(718,Integer.parseInt(endereco.getNumeroLogradouro().toString()));
	}
	
	@Test
	public void testeBuscaEnderecoTodos() {
		List<Endereco> enderecos = enderecoService.findAll();
		assertNotNull(enderecos);
	}
	/**
	@Test
	public void testeBucscaEnderecoPorPessoa() {
		List<Endereco> enderecos = enderecoService.findByPessoa(pessoaService.findByCnpj("4478969850008").getId());
		assertEquals(cepService.findByCep("12289368").getId(), enderecos.get(0).getCep().getId());
	}
	**/
	/**
	@Test
	public void testeBuscaEnderecoPorCep() {
		List<Endereco> enderecos = enderecoService.findByCep("12288560");
		assertNotNull(enderecos);
	}
	**/
}
