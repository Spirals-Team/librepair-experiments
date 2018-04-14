package br.com.reclameaqui.teste;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo.AssertionFailedException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.reclameaqui.teste.controller.ClienteController;
import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.EmpresaDTO;
import br.com.reclameaqui.teste.dtos.EnderecoDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.exceptions.ClienteNaoEncontradoException;
import br.com.reclameaqui.teste.service.ClienteService;
import br.com.reclameaqui.teste.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCliente {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Mock
	private ClienteService clienteService;

	@InjectMocks
	private ClienteController clienteController;

	private ClienteDTO clienteOne, clienteTwo, clienteMatheus;

	ReclamantesRequestDTO reclamantesResquest;

	ReclamantesResponseDTO reclamantesResponse;

	private EnderecoDTO enderecoOne, enderecoTwo;

	private EmpresaDTO empresa;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);

		empresa = EmpresaDTO.builder().cnpj("52306301000141").nome("Bradesco").build();

		enderecoOne = EnderecoDTO.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();

		enderecoTwo = EnderecoDTO.builder().cep("09516426").logradouro("Rua Otávio").numero("111")
				.bairro("Jardim Independente").cidade("Campinas").build();

		clienteOne = ClienteDTO.builder().id("id12345ABC").nome("Jessica").telefone("1945784854").cpf("40934361932")
				.endereco(enderecoOne).build();

		clienteTwo = ClienteDTO.builder().id("id12345ABC").nome("Rubens").telefone("1198563256").cpf("25042859730")
				.endereco(enderecoTwo).build();

		clienteMatheus = ClienteDTO.builder().nome("Jessica").telefone("1945784854").cpf("90004258592")
				.endereco(enderecoOne).build();

		reclamantesResquest = ReclamantesRequestDTO.builder().cidade("Campinas").empresa("Bradesco").build();

		reclamantesResponse = ReclamantesResponseDTO.builder().clientes(Arrays.asList(clienteOne, clienteTwo))
				.clientesQueReclamaram(2).build();

	}

	@Test
	public void test() throws Exception {

		this.mockMvc.perform(get("/teste").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasToString("teste ok")));

	}

	@Test
	public void quantidadeDeReclamantesTest() throws Exception {

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
	public void buscarTodosTest() throws ClienteNaoEncontradoException {

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
	public void consultarClientePorTestClienteInexistente() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.consultar("dasaSDK4535DF")).thenThrow(new ClienteNaoEncontradoException());

		ResponseEntity<ClienteDTO> response = clienteController.consultar("dasaSDK4535DF");

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void consultarClientePorCpfTestClienteInexistente() throws ClienteNaoEncontradoException {

		Mockito.when(clienteService.consultarPorCpf("12589240643")).thenThrow(new ClienteNaoEncontradoException());

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
	public void deletarClientePorCpfTest() throws ClienteNaoEncontradoException {

		ResponseEntity<ClienteDTO> expectedResponse = ResponseEntity.noContent().build();

		Mockito.when(clienteService.deletarPorCpf(clienteOne.getCpf())).thenReturn(expectedResponse);

		ResponseEntity<ClienteDTO> response = clienteController.deletarPorCpf(clienteOne.getCpf());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
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
	public void atualizarClientePorCpfTest() throws ClienteNaoEncontradoException {
		clienteOne.setNome("Felipe");

		Mockito.when(clienteService.atualizarPorCpf(clienteOne.getCpf(), clienteOne)).thenReturn(clienteOne);

		ResponseEntity<ClienteDTO> response = clienteController.atualizarPorCpf(clienteOne.getCpf(), clienteOne);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		assertEquals(response.getBody().getNome(), clienteOne.getNome());
	}

	/*
	 * @InjectMocks private HelloController helloController;
	 * 
	 * @Before public void setUp() throws Exception {
	 * MockitoAnnotations.initMocks(this); mockMvc =
	 * MockMvcBuilders.standaloneSetup(helloController).build(); }
	 * 
	 * @Test public void hello() throws Exception { mockMvc.perform(get("/hello")
	 * .accept(MediaType.APPLICATION_JSON)) .andExpect(status().isOk())
	 * .andExpect(content().string("Hello"));
	 */

	/*
	 * 
	 * .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
	 * .andExpect(jsonPath("$.content[0].email").value("user1@example.com"))
	 * .andExpect(jsonPath("$.content[0].firstName").value("User"))
	 * .andExpect(jsonPath("$.content[0].lastName").value("One"))
	 * .andExpect(jsonPath("$.content[1].email").value("user2@example.com"))
	 * .andExpect(jsonPath("$.content[1].firstName").value("User"))
	 * .andExpect(jsonPath("$.content[1].lastName").value("Two"))
	 * .andExpect(jsonPath("$.totalElements").value(2))
	 * .andExpect(jsonPath("$.totalPages").value(1))
	 * .andExpect(jsonPath("$.size").value(20))
	 * .andExpect(jsonPath("$.number").value(0))
	 * .andExpect(jsonPath("$.first").value("true"))
	 * .andExpect(jsonPath("$.last").value("true"));
	 */


	@Test
	public void testQuantidadeReclamantes() throws Exception {

		this.mockMvc.perform(get("/reclamacoes").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(14)));
	}

	// @Test(expected = ArithmeticException.class)

	/*
	 * @Test public void testBuscarCepVariasTentativasColocando0ECepNaoEncontrado()
	 * throws Exception { Endereco vo = new Endereco(); vo.setCep("11111111"); Gson
	 * gson = new Gson(); String json = gson.toJson(vo);
	 * 
	 * this.mockMvc.perform(get("/buscarCepJson")
	 * .contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(status().isInternalServerError()); }
	 * 
	 * @Test public void testBuscarCepVariasTentativasColocando0() throws Exception
	 * { Endereco vo = new Endereco(); vo.setCep("09951385"); Gson gson = new
	 * Gson(); String json = gson.toJson(vo);
	 * 
	 * this.mockMvc.perform(get("/buscarCepJson")
	 * .contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(status().isOk()) .andExpect(jsonPath("$.cep" , is("09951380"))); }
	 * 
	 * @Test public void testBuscarCepInvalidoException() throws Exception {
	 * Endereco vo = new Endereco(); vo.setCep("023"); Gson gson = new Gson();
	 * String json = gson.toJson(vo);
	 * 
	 * this.mockMvc.perform(get("/buscarCepJson")
	 * .contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(status().isInternalServerError()); }
	 * 
	 * @Test public void testBuscarCepVazioException() throws Exception { Endereco
	 * vo = new Endereco(); vo.setCep(""); Gson gson = new Gson(); String json =
	 * gson.toJson(vo);
	 * 
	 * this.mockMvc.perform(get("/buscarCepJson")
	 * .contentType(MediaType.APPLICATION_JSON) .content(json))
	 * .andExpect(status().isInternalServerError()); }
	 * 
	 */
}
