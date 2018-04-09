package br.com.una.projeto.controller;

import java.util.List;

import br.com.una.projeto.entity.Bairro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.una.projeto.entity.Marca;
import br.com.una.projeto.repository.MarcaRepository;

@Controller
@RequestMapping("/marca")
public class MarcaController {
	@Autowired
	private MarcaRepository marcaRepository;

	@GetMapping("/listar")
	public List<Marca> list() {
		return marcaRepository.findAll();
	}

	@PostMapping("/editar/{marca}")
	public Marca editar(@PathVariable("marca") Marca marca) {
		return marcaRepository.save(marca);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		marcaRepository.delete(id);
	}
	@PostMapping("/salvar")
	public Marca salvar(@RequestBody Marca marca) {
		return marcaRepository.save(marca);
	}
}
