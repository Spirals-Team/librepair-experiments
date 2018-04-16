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
import br.com.reclameaqui.teste.dtos.EmpresaDTO;
import br.com.reclameaqui.teste.dtos.EnderecoDTO;
import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.ReclamacaoNaoEncontradaException;
import br.com.reclameaqui.teste.service.ReclamacaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestReclamacaoController {
	
	@Mock
	private ReclamacaoService reclamacaoService;

	@InjectMocks
	private ReclamacaoController reclamacaoController;

	private ReclamacaoDTO reclamacaoSamsungMatheus, reclamacaoSamsungOne, reclamacaoSamsungTwo;

	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);

		EmpresaDTO empresaSamsung = EmpresaDTO.builder().cnpj("52306301000141").nome("Samsung").build();
		
		EnderecoDTO endereco = EnderecoDTO.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();

		reclamacaoSamsungTwo = ReclamacaoDTO.builder().nome("A TV pifou").descricao("Comprei uma tv e ela parou de funcionar").empresa(empresaSamsung).endereco(endereco).idCliente("id154345fgC").build();

		reclamacaoSamsungOne = ReclamacaoDTO.builder().id("4534JSFSDI324").nome("Tv com imagem ruim").descricao("Imagem muito ruim").empresa(empresaSamsung).endereco(endereco).idCliente("id12345ABC").build();

		reclamacaoSamsungMatheus = ReclamacaoDTO.builder().nome("Tv com imagem ruim").descricao("Imagem muito ruim").empresa(empresaSamsung).endereco(endereco).idCliente("id12345ABC").build();

	}

	@Test
	public void incluiReclamacaoTest() {

		Mockito.when(reclamacaoService.incluir(reclamacaoSamsungMatheus)).thenReturn(reclamacaoSamsungOne);

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.incluir(reclamacaoSamsungMatheus);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertEquals(response.getBody().getId(), reclamacaoSamsungOne.getId());

	}

	@Test
	public void buscarTodosTest() {

		List<ReclamacaoDTO> listClientes = Arrays.asList(reclamacaoSamsungOne, reclamacaoSamsungTwo);

		Mockito.when(reclamacaoService.buscarTodos()).thenReturn(listClientes);

		ResponseEntity<List<ReclamacaoDTO>> response = reclamacaoController.buscarTodasReclamacoes();

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().size(), 2);
	}

	@Test
	public void consultarReclamacaoTest() throws ReclamacaoNaoEncontradaException {

		Mockito.when(reclamacaoService.consultar(reclamacaoSamsungOne.getId())).thenReturn(reclamacaoSamsungOne);

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.consultar(reclamacaoSamsungOne.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getId(), reclamacaoSamsungOne.getId());
	}
	
	@Test
	public void consultarReclamacaoInexistenteTest() throws ReclamacaoNaoEncontradaException {

		Mockito.when(reclamacaoService.consultar(reclamacaoSamsungOne.getId())).thenThrow(new ReclamacaoNaoEncontradaException("Reclamacao nao encontrado"));

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.consultar(reclamacaoSamsungOne.getId());

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void deletarReclamacaoeTest() throws ReclamacaoNaoEncontradaException {

		ResponseEntity<ReclamacaoDTO> expectedResponse = ResponseEntity.noContent().build();

		Mockito.when(reclamacaoService.deletar(reclamacaoSamsungOne.getId())).thenReturn(expectedResponse);

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.deletar(reclamacaoSamsungOne.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deletarReclamacaoInexistenteTest() throws ReclamacaoNaoEncontradaException {

		Mockito.when(reclamacaoService.deletar(reclamacaoSamsungOne.getId())).thenThrow(new ReclamacaoNaoEncontradaException("Reclamacao nao encontrado"));

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.deletar(reclamacaoSamsungOne.getId());

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}


	@Test
	public void atualizarReclamacaoTest() throws ReclamacaoNaoEncontradaException {
		reclamacaoSamsungOne.setNome("Meu celular não carrega");

		Mockito.when(reclamacaoService.atualizar(reclamacaoSamsungOne.getId(), reclamacaoSamsungOne)).thenReturn(reclamacaoSamsungOne);

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.atualizar(reclamacaoSamsungOne.getId(), reclamacaoSamsungOne);

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		assertEquals(response.getBody().getId(), reclamacaoSamsungOne.getId());
	}
	
	@Test
	public void atualizarReclamacaoInexistenteTest() throws ReclamacaoNaoEncontradaException {

		Mockito.when(reclamacaoService.atualizar(reclamacaoSamsungOne.getId(), reclamacaoSamsungOne)).thenThrow(new ReclamacaoNaoEncontradaException("Reclamacao nao encontrado"));

		ResponseEntity<ReclamacaoDTO> response = reclamacaoController.atualizar(reclamacaoSamsungOne.getId(), reclamacaoSamsungOne);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
