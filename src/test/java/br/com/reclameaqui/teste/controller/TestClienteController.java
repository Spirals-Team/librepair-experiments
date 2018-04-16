package br.com.reclameaqui.teste.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.reclameaqui.teste.TesteApplication;
import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.EnderecoDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.exceptions.ClienteNaoEncontradoException;
import br.com.reclameaqui.teste.service.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestClienteController {

	@Mock
	private ClienteService clienteService;

	@InjectMocks
	private ClienteController clienteController;

	private ClienteDTO clienteOne, clienteTwo, clienteMatheus;

	private ReclamantesRequestDTO reclamantesResquest;

	private ReclamantesResponseDTO reclamantesResponse;

	
	@Before
	public void initialize() {
		
		MockitoAnnotations.initMocks(this);

		EnderecoDTO enderecoOne = EnderecoDTO.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();

		EnderecoDTO enderecoTwo = EnderecoDTO.builder().cep("09516426").logradouro("Rua Otávio").numero("111")
				.bairro("Jardim Independente").cidade("Campinas").build();

		clienteOne = ClienteDTO.builder().id("id12345ABC").nome("Matheus").telefone("1945784854").cpf("40934361932")
				.endereco(enderecoOne).build();

		clienteTwo = ClienteDTO.builder().id("id14234gfd").nome("Rubens").telefone("1198563256").cpf("25042859730")
				.endereco(enderecoTwo).build();

		clienteMatheus = ClienteDTO.builder().nome("Matheus").telefone("1945784854").cpf("90004258592")
				.endereco(enderecoOne).build();

		reclamantesResquest = ReclamantesRequestDTO.builder().cidade("Campinas").empresa("Bradesco").build();

		reclamantesResponse = ReclamantesResponseDTO.builder().clientes(Arrays.asList(clienteOne, clienteTwo))
				.clientesQueReclamaram(2).build();

	}
/*
	@Test
	public void quantidadeDeReclamantesTest() {

		Mockito.when(clienteService.buscarClientesQueReclamam(reclamantesResquest)).thenReturn(reclamantesResponse);

		ResponseEntity<ReclamantesResponseDTO> response = clienteController.buscarClientesQueReclamam(reclamantesResquest);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getClientesQueReclamaram(), 2);
		assertEquals(response.getBody().getClientes(), Arrays.asList(clienteOne,clienteTwo));
	}

	@Test
	public void incluirClienteTest() {

		Mockito.when(clienteService.incluir(clienteMatheus)).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteController.incluir(clienteMatheus);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertEquals(response.getBody().getId(), clienteOne.getId());

	}

	@Test
	public void buscarTodosTest() {

		List<ClienteDTO> listClientes = Arrays.asList(clienteOne, clienteTwo);

		Mockito.when(clienteService.buscarTodos()).thenReturn(listClientes);

		ResponseEntity<List<ClienteDTO>> response = clienteController.buscarTodasClientes();

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().size(), 2);
	}

	@Test
	public void consultarClienteTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.consultar(clienteOne.getId())).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteController.consultar(clienteOne.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getId(), clienteOne.getId());
	}

	@Test
	public void consultarClientePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.consultarPorCpf(clienteOne.getCpf())).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteController.consultarPorCpf(clienteOne.getCpf());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getCpf(), clienteOne.getCpf());
	}
	
	@Test
	public void consultarClienteInexistenteTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.consultar("dasaSDK4535DF")).thenThrow(new ClienteNaoEncontradoException("Cliente nao encontrado"));

		ResponseEntity<ClienteDTO> response = clienteController.consultar("dasaSDK4535DF");

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void consultarClienteInexistentePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.consultarPorCpf("12589240643")).thenThrow(new ClienteNaoEncontradoException("Cliente nao encontrado"));

		ResponseEntity<ClienteDTO> response = clienteController.consultarPorCpf("12589240643");

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void deletarClienteTest() throws ClienteNaoEncontradoException {

		ResponseEntity<ClienteDTO> expectedResponse = ResponseEntity.noContent().build();

		Mockito.when(clienteService.deletar(clienteOne.getId())).thenReturn(expectedResponse);

		ResponseEntity<ClienteDTO> response = clienteController.deletar(clienteOne.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deletarClienteInexistenteTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.deletar(clienteOne.getId())).thenThrow(new ClienteNaoEncontradoException("Cliente nao encontrado"));

		ResponseEntity<ClienteDTO> response = clienteController.deletar(clienteOne.getId());

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void deletarClientePorCpfTest() throws ClienteNaoEncontradoException {

		ResponseEntity<ClienteDTO> expectedResponse = ResponseEntity.noContent().build();

		Mockito.when(clienteService.deletarPorCpf(clienteOne.getCpf())).thenReturn(expectedResponse);

		ResponseEntity<ClienteDTO> response = clienteController.deletarPorCpf(clienteOne.getCpf());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deletarClienteInexistentePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.deletarPorCpf(clienteOne.getCpf())).thenThrow(new ClienteNaoEncontradoException("Cliente nao encontrado"));

		ResponseEntity<ClienteDTO> response = clienteController.deletarPorCpf(clienteOne.getCpf());

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void atualizarClienteTest() throws ClienteNaoEncontradoException {
		clienteOne.setNome("Felipe");

		Mockito.when(clienteService.atualizar(clienteOne.getId(), clienteOne)).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteController.atualizar(clienteOne.getId(), clienteOne);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		assertEquals(response.getBody().getId(), clienteOne.getId());
	}
	
	@Test
	public void atualizarClienteInexistenteTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.atualizar(clienteOne.getId(),clienteOne)).thenThrow(new ClienteNaoEncontradoException("Cliente nao encontrado"));

		ResponseEntity<ClienteDTO> response = clienteController.atualizar(clienteOne.getId(),clienteOne);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void atualizarClientePorCpfTest() throws ClienteNaoEncontradoException {
		clienteOne.setNome("Felipe");

		Mockito.when(clienteService.atualizarPorCpf(clienteOne.getCpf(), clienteOne)).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteController.atualizarPorCpf(clienteOne.getCpf(), clienteOne);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		assertEquals(response.getBody().getNome(), clienteOne.getNome());
	}
	
	@Test
	public void atualizarClienteInexistentePorCpfTest() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.atualizarPorCpf(clienteOne.getCpf(),clienteOne)).thenThrow(new ClienteNaoEncontradoException("Cliente nao encontrado"));

		ResponseEntity<ClienteDTO> response = clienteController.atualizarPorCpf(clienteOne.getCpf(),clienteOne);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
*/
}
