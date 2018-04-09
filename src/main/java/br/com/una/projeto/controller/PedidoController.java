package br.com.una.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.una.projeto.entity.Pedido;
import br.com.una.projeto.repository.PedidoRepository;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping("/listar")
	public List<Pedido> list() {
		return pedidoRepository.findAll();
	}

	@PostMapping("/editar/{pedido}")
	public Pedido editar(@PathVariable("pedido") Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		pedidoRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Pedido salvar(@RequestBody Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
}
