package com.concrete.ecommerce.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.concrete.ecommerce.api.dtos.ProdutoDto;
import com.concrete.ecommerce.api.entities.Produto;
import com.concrete.ecommerce.api.services.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoControllerTest {



	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProdutoService produtoService;
	
	private static final String URL_BASE = "/api/produtos/";
	private static final Long ID_PRODUTO = 1L;
	private static final String DESCRICAO = "fake product";
	private static final double VALOR_PRODUTO = 5.60;
	
	
	@Test
	@WithMockUser
	public void testCadastrarProduto() throws Exception {
		Produto produto = obterDadosProduto();
		BDDMockito.given(this.produtoService.persistir(Mockito.any(Produto.class))).willReturn(produto);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_PRODUTO))
				.andExpect(jsonPath("$.data.descricao").value(DESCRICAO))
				.andExpect(jsonPath("$.data.valor").value(VALOR_PRODUTO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testRemoverProduto() throws Exception {
		BDDMockito.given(this.produtoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Produto()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_PRODUTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testRemoverLancamentoAcessoNegado() throws Exception {
		BDDMockito.given(this.produtoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Produto()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_PRODUTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		ProdutoDto produtoDto = new ProdutoDto();
		produtoDto.setId(null);
		produtoDto.setDescricao(DESCRICAO);
		produtoDto.setValor(VALOR_PRODUTO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(produtoDto);
	}

	private Produto obterDadosProduto() {
		Produto produto = new Produto();
		produto.setId(ID_PRODUTO);
		produto.setDescricao(DESCRICAO);
		produto.setValor(VALOR_PRODUTO);
		return produto;
	}	

}
