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
import br.com.reclameaqui.teste.documents.Empresa;
import br.com.reclameaqui.teste.documents.Endereco;
import br.com.reclameaqui.teste.documents.Reclamacao;
import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.EmpresaDTO;
import br.com.reclameaqui.teste.dtos.EnderecoDTO;
import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.ReclamacaoNaoEncontradaException;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;
import br.com.reclameaqui.teste.service.ReclamacaoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestReclamacaoService {
	
	@Mock
	private ReclamacaoRepository reclamacaoRepository;

	@InjectMocks
	private ReclamacaoServiceImpl reclamacaoService;

	private ReclamacaoDTO reclamacaoSamsungMatheusDTO, reclamacaoSamsungOneDTO, reclamacaoSamsungTwoDTO;
	
	private Reclamacao reclamacaoSamsungMatheus, reclamacaoSamsungOne;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);

		EmpresaDTO empresaSamsungDTO = EmpresaDTO.builder().cnpj("52306301000141").nome("Samsung").build();
		
		Empresa empresaSamsung = Empresa.builder().cnpj("52306301000141").nome("Samsung").build();
		
		EnderecoDTO enderecoDTO = EnderecoDTO.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();
		
		Endereco endereco = Endereco.builder().cep("07346444").logradouro("Rua Liberdade").numero("1243")
				.bairro("Jardim Independente").cidade("Campinas").build();
		
		Cliente clienteMatheus = Cliente.builder().nome("Matheus").telefone("1945784854").cpf("90004258592")
				.endereco(endereco).build();

		reclamacaoSamsungTwoDTO = ReclamacaoDTO.builder().nome("A TV pifou").descricao("Comprei uma tv e ela parou de funcionar").empresa(empresaSamsungDTO).endereco(enderecoDTO).idCliente("id154345fgC").build();
		
		reclamacaoSamsungOneDTO = ReclamacaoDTO.builder().nome("Tv com imagem ruim").descricao("Imagem muito ruim").empresa(empresaSamsungDTO).endereco(enderecoDTO).idCliente("id12345ABC").build();
		
		reclamacaoSamsungOne = Reclamacao.builder().id("4534JSFSDI324").nome("Tv com imagem ruim").descricao("Imagem muito ruim").empresa(empresaSamsung).endereco(endereco).idCliente(clienteMatheus).build();

		reclamacaoSamsungMatheusDTO = ReclamacaoDTO.builder().nome("Tv com imagem ruim").descricao("Imagem muito ruim").empresa(empresaSamsungDTO).endereco(enderecoDTO).idCliente("id12345ABC").build();
		
		reclamacaoSamsungMatheus = Reclamacao.builder().nome("Tv com imagem ruim").descricao("Imagem muito ruim").empresa(empresaSamsung).endereco(endereco).idCliente(clienteMatheus).build();

	}

	@Test
	public void incluiReclamacaoTest() {
		
		Mockito.when(reclamacaoRepository.save(reclamacaoSamsungMatheus)).thenReturn(reclamacaoSamsungMatheus);

		ReclamacaoDTO response = reclamacaoService.incluir(reclamacaoSamsungMatheusDTO);

		assertThat(response).isNotNull();
		assertEquals(response.getId(), reclamacaoSamsungMatheusDTO.getId());

	}

	@Test
	public void buscarTodosTest() {
		
		List<ReclamacaoDTO> listReclamacoesDTO = Arrays.asList(reclamacaoSamsungOneDTO, reclamacaoSamsungTwoDTO);
		
		Type listType = new TypeToken<List<ClienteDTO>>() {}.getType();
		List<Reclamacao> listReclamacoes = modelMapper().map(listReclamacoesDTO, listType);

		Mockito.when(reclamacaoRepository.findAll()).thenReturn(listReclamacoes);

		List<ReclamacaoDTO> response = reclamacaoService.buscarTodos();

		assertThat(response).isNotNull();
		assertEquals(response.size(), 2);
	}

	@Test
	public void consultarReclamacaoTest() throws ReclamacaoNaoEncontradaException {

		Mockito.when(reclamacaoRepository.findById(reclamacaoSamsungOneDTO.getId())).thenReturn(reclamacaoSamsungOne);

		ReclamacaoDTO response = reclamacaoService.consultar(reclamacaoSamsungOneDTO.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getNome(), reclamacaoSamsungOneDTO.getNome());
	}
	
	@Test
	public void deletarReclamacaoeTest() throws ReclamacaoNaoEncontradaException {
		
		Mockito.when(reclamacaoRepository.findOne(reclamacaoSamsungOneDTO.getId())).thenReturn(reclamacaoSamsungOne);

		ResponseEntity<ReclamacaoDTO> response = reclamacaoService.deletar(reclamacaoSamsungOneDTO.getId());

		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	

	@Test
	public void atualizarReclamacaoTest() throws ReclamacaoNaoEncontradaException {
		reclamacaoSamsungOne.setNome("Meu celular não carrega");

		Mockito.when(reclamacaoRepository.findById(reclamacaoSamsungOneDTO.getId())).thenReturn(reclamacaoSamsungOne);
		
		ReclamacaoDTO response = reclamacaoService.atualizar(reclamacaoSamsungOneDTO.getId(), reclamacaoSamsungOneDTO);

		assertThat(response).isNotNull();
		assertEquals(response.getNome(), reclamacaoSamsungOneDTO.getNome());
	}
	
	@Test(expected = ReclamacaoNaoEncontradaException.class)
	public void testeValidacao() throws ReclamacaoNaoEncontradaException {
		reclamacaoService.validarReclamacao(null);
	}

}
