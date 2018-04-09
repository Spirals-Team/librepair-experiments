package br.com.una.projeto.controller;

import java.util.List;

import br.com.una.projeto.entity.Bairro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.una.projeto.entity.Estoque;
import br.com.una.projeto.repository.EstoqueRepository;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

	@Autowired
	private EstoqueRepository estoqueRepository;

	@GetMapping("/listar")
	public List<Estoque> list() {
		return estoqueRepository.findAll();
	}

	@PostMapping("/editar/{estoque}")
	public Estoque editar(@PathVariable("estoque") Estoque estoque) {
		return estoqueRepository.save(estoque);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		estoqueRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Estoque salvar(@RequestBody Estoque estoque) {
		return estoqueRepository.save(estoque);
	}
}
