package br.com.una.projeto.controller;

import java.util.List;

import br.com.una.projeto.entity.Bairro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.una.projeto.entity.Carrinho;
import br.com.una.projeto.repository.CarrinhoRepository;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {
	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@GetMapping("/listar")
	public List<Carrinho> list() {
		return carrinhoRepository.findAll();
	}

	@PostMapping("/editar/{carrinho}")
	public Carrinho editar(@PathVariable("carrinho") Carrinho carrinho) {
		return carrinhoRepository.save(carrinho);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		carrinhoRepository.delete(id);
	}
	@PostMapping("/salvar")
	public Carrinho salvar(@RequestBody Carrinho carrinho) {
		return carrinhoRepository.save(carrinho);
	}
	
}
