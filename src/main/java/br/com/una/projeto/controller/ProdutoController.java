package br.com.una.projeto.controller;

import java.util.List;

import br.com.una.projeto.entity.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.una.projeto.entity.Produto;
import br.com.una.projeto.repository.ProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/listar")
	public List<Produto> list() {
		return produtoRepository.findAll();
	}

	@PostMapping("/editar/{produto}")
	public Produto editar(@PathVariable("produto") Produto produto) {
		return produtoRepository.save(produto);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		produtoRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Produto salvar(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
}
