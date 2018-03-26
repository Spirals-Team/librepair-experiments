package com.concrete.ecommerce.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.repositories.PedidoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PedidoServiceTest {

	@MockBean
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito
				.given(this.pedidoRepository.findByUsuarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Pedido>(new ArrayList<Pedido>()));
		BDDMockito.given(this.pedidoRepository.findOne(Mockito.anyLong())).willReturn(new Pedido());
		BDDMockito.given(this.pedidoRepository.save(Mockito.any(Pedido.class))).willReturn(new Pedido());
	}

	@Test
	public void testBuscarPedidoPorUsuarioId() {
		Page<Pedido> pedido = this.pedidoService.buscarPorUsuarioId(1L, new PageRequest(0, 10));
		assertNotNull(pedido);
	}

	@Test
	public void testBuscarLancamentoPorId() {
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(1L);
		assertTrue(pedido.isPresent());
	}

	@Test
	public void testPersistirLancamento() {
		Pedido pedido = this.pedidoService.persistir(new PedidoDto(), new Pedido());
		assertNotNull(pedido);
	}

}
