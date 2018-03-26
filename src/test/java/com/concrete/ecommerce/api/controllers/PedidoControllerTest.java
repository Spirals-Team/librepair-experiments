package com.concrete.ecommerce.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.concrete.ecommerce.api.entities.Produto;
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

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.services.PedidoService;
import com.concrete.ecommerce.api.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {


	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PedidoService pedidoService;
	
	@MockBean
	private UsuarioService usuarioService;
	
	private static final String URL_BASE = "/api/pedidos/";
	private static final Long ID_USUARIO = 1L;
	private static final Long ID_PEDIDO = 1L;
	private static final String ENDERECO_PEDIDO = "Avenida Nações Unidas, 11541";
	private static final String DESCRICAO_PEDIDO = "Pedido de Mesas";
	
	
	@Test
	@WithMockUser
	public void testCadastrarPedido() throws Exception {
		Pedido pedido = obterDadosPedido();
		BDDMockito.given(this.usuarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Usuario()));
		BDDMockito.given(this.pedidoService.persistir(Mockito.any(PedidoDto.class), Mockito.any(Pedido.class))).willReturn(pedido);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_PEDIDO))
				.andExpect(jsonPath("$.data.descricao").value(DESCRICAO_PEDIDO))
				.andExpect(jsonPath("$.data.enderecoEntrega").value(ENDERECO_PEDIDO))
				.andExpect(jsonPath("$.data.usuarioId").value(ID_USUARIO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarPedidoUsuarioIdInvalido() throws Exception {
		BDDMockito.given(this.usuarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Usuário não encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testRemoverPedido() throws Exception {
		BDDMockito.given(this.pedidoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Pedido()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_PEDIDO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testRemoverPedidoAcessoNegado() throws Exception {
		BDDMockito.given(this.pedidoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Pedido()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_PEDIDO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setId(null);
		pedidoDto.setDescricao(DESCRICAO_PEDIDO);
		pedidoDto.setEnderecoEntrega(ENDERECO_PEDIDO);
		pedidoDto.setUsuarioId(ID_USUARIO);
		List<Produto> produtos = new ArrayList<>();
		Produto produto = new Produto();
		produto.setDescricao("Produto Teste");
		produto.setValor(2.57);
		produtos.add(produto);
		pedidoDto.setProdutos(produtos);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(pedidoDto);
	}

	private Pedido obterDadosPedido() {
		Pedido pedido = new Pedido();
		pedido.setId(ID_PEDIDO);
		pedido.setDescricao(DESCRICAO_PEDIDO);
		pedido.setEnderecoEntrega(ENDERECO_PEDIDO);
		pedido.setUsuario(new Usuario());
		pedido.getUsuario().setId(ID_USUARIO);
		return pedido;
	}	

}
