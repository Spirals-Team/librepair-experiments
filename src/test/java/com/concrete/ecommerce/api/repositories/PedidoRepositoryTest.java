package com.concrete.ecommerce.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.enums.PerfilEnum;
import com.concrete.ecommerce.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PedidoRepositoryTest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private Long usuarioId;

	@Before
	public void setUp() throws Exception {
		Usuario usuario = this.usuarioRepository.save(obterDadosUsuario());
		this.usuarioId = usuario.getId();
		this.pedidoRepository.save(obterDadosPedidos(usuario));
		this.pedidoRepository.save(obterDadosPedidos(usuario));
	}

	@After
	public void tearDown() throws Exception {
		this.usuarioRepository.deleteAll();
	}

	@Test
	public void testBuscarPedidosPorUsuarioId() {
		List<Pedido> pedidos = this.pedidoRepository.findByUsuarioId(usuarioId);
		assertEquals(2, pedidos.size());
	}
	
	@Test
	public void testBuscarPedidosPorUsuarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Pedido> pedidos = this.pedidoRepository.findByUsuarioId(usuarioId, page);
		assertEquals(2, pedidos.getTotalElements());
	}
	
	private Pedido obterDadosPedidos(Usuario usuario) {
		Pedido pedido = new Pedido();
		pedido.setDescricao("Novo Pedido");
		pedido.setEnderecoEntrega("Avenida Nações Unidas, 11541");
//		List<Produto> produtos = new ArrayList<>();
//		Produto produto = new Produto();
//		produto.setValor(15.80);
//		produtos.add(produto);
//		pedido.setProdutos(produtos);
		pedido.setUsuario(usuario);
		return pedido;
	}

	private Usuario obterDadosUsuario() throws NoSuchAlgorithmException {
		Usuario funcionario = new Usuario();
		funcionario.setNome("Fulano de Tal");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setEmail("email@email.com");
		return funcionario;
	}


}
