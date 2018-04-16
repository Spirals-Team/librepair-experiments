package br.com.reclameaqui.teste.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.reflect.TypeToken;

import br.com.reclameaqui.teste.TesteApplication;
import br.com.reclameaqui.teste.documents.Cliente;
import br.com.reclameaqui.teste.documents.Endereco;
import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.EnderecoDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.enums.TipoConsulta;
import br.com.reclameaqui.teste.exceptions.ClienteNaoEncontradoException;
import br.com.reclameaqui.teste.repository.ClienteRepository;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;
import br.com.reclameaqui.teste.service.ClienteServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestClienteService {
	
	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private ReclamacaoRepository reclamacaoRepository;

	@InjectMocks
	private ClienteServiceImpl clienteService;

	private ClienteDTO clienteOneDTO, clienteTwoDTO, clienteMatheusDTO;
	
	private Cliente clienteMatheus, clienteOne;

	private ReclamantesRequestDTO reclamantesResquest;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Before
	public void initialize() {
		
		MockitoAnnotations.initMocks(this);

		EnderecoDTO enderecoOneDTO = EnderecoDTO.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();

		EnderecoDTO enderecoTwoDTO = EnderecoDTO.builder().cep("09516426").logradouro("Rua Otávio").numero("111")
				.bairro("Jardim Independente").cidade("Campinas").build();

		clienteOneDTO = ClienteDTO.builder().nome("Matheus").telefone("1945784854").cpf("40934361932")
				.endereco(enderecoOneDTO).build();

		clienteTwoDTO = ClienteDTO.builder().nome("Rubens").telefone("1198563256").cpf("25042859730")
				.endereco(enderecoTwoDTO).build();

		clienteMatheusDTO = ClienteDTO.builder().nome("Matheus").telefone("1945784854").cpf("90004258592")
				.endereco(enderecoOneDTO).build();

		reclamantesResquest = ReclamantesRequestDTO.builder().cidade("Campinas").empresa("Bradesco").build();

		Endereco enderecoOne = Endereco.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();
		
		clienteMatheus = Cliente.builder().nome("Matheus").telefone("1945784854").cpf("90004258592")
				.endereco(enderecoOne).build();
		
		clienteOne = Cliente.builder().id("id12345ABC").nome("Matheus").telefone("1945784854").cpf("40934361932")
				.endereco(enderecoOne).build();
	}

	@Test
	public void quantidadeDeReclamantesTest() {
		
		List<ClienteDTO> clientesDTO = Arrays.asList(clienteOneDTO,clienteTwoDTO);
		
		Type listType = new TypeToken<List<ClienteDTO>>() {}.getType();
		List<Cliente> listClientes = modelMapper().map(clientesDTO, listType);
		

		Mockito.when(reclamacaoRepository.buscarClientesQueReclamam(TipoConsulta.EMPRESAECIDADE, reclamantesResquest)).thenReturn(listClientes);

		ReclamantesResponseDTO response = clienteService.buscarClientesQueReclamam(reclamantesResquest);

		assertThat(response).isNotNull();
		assertEquals(response.getClientes(), clientesDTO);
		assertEquals(response.getClientesQueReclamaram(), 2);
	}

	@Test
	public void incluirClienteTest() {

		Mockito.when(clienteRepository.save(clienteMatheus)).thenReturn(clienteMatheus);

		ClienteDTO response = clienteService.incluir(clienteMatheusDTO);

		assertThat(response).isNotNull();
		assertEquals(response.getCpf(), clienteMatheusDTO.getCpf());
	}
	
	
	@Test
	public void buscarTodosTest() throws ClienteNaoEncontradoException {
		
		List<ClienteDTO> clientesDTO = Arrays.asList(clienteOneDTO,clienteTwoDTO);
		
		Type listType = new TypeToken<List<ClienteDTO>>() {}.getType();
		List<Cliente> listClientes = modelMapper().map(clientesDTO, listType);

		Mockito.when(clienteRepository.findAll()).thenReturn(listClientes);

		List<ClienteDTO> response = clienteService.buscarTodos();

		assertThat(response).isNotNull();
		assertEquals(response.size(), 2);
	}
	
	@Test
	public void consultarClienteTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteRepository.findById(clienteOneDTO.getId())).thenReturn(clienteOne);

		ClienteDTO response = clienteService.consultar(clienteOneDTO.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getCpf(), clienteOneDTO.getCpf());
	}
	
	@Test
	public void consultarClientePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteRepository.findByCpf(clienteOneDTO.getCpf())).thenReturn(clienteOne);

		ClienteDTO response = clienteService.consultarPorCpf(clienteOneDTO.getCpf());

		assertThat(response).isNotNull();
		assertEquals(response.getNome(), clienteOneDTO.getNome());
	}
	
	
	
	@Test
	public void deletarClienteTest() throws ClienteNaoEncontradoException {
		
		Mockito.when(clienteRepository.findOne(clienteOneDTO.getId())).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteService.deletar(clienteOneDTO.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	

	@Test
	public void deletarClientePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteRepository.findByCpf(clienteOneDTO.getCpf())).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteService.deletarPorCpf(clienteOneDTO.getCpf());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void atualizarClienteTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteRepository.findOne(clienteOneDTO.getId())).thenReturn(clienteOne);

		ClienteDTO response = clienteService.atualizar(clienteOneDTO.getId(), clienteOneDTO);

		assertThat(response).isNotNull();
		assertEquals(response.getNome(), clienteOneDTO.getNome());
	}
	
	@Test
	public void atualizarClientePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteRepository.findByCpf(clienteOneDTO.getCpf())).thenReturn(clienteOne);

		ClienteDTO response = clienteService.atualizarPorCpf(clienteOneDTO.getCpf(), clienteOneDTO);

		assertThat(response).isNotNull();
		assertEquals(response.getNome(), clienteOneDTO.getNome());
	}
	
	@Test(expected = ClienteNaoEncontradoException.class)
	public void testeValidacao() throws ClienteNaoEncontradoException {
		clienteService.validarCliente(null);
	}
	
}
