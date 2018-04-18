package br.com.una.projeto.controller;

import br.com.una.projeto.entity.Estado;
import br.com.una.projeto.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/estado")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping("/listar")
	public List<Estado> list() {
		return estadoRepository.findAll();
	}

	@PostMapping("/editar/{estado}")
	public Estado editar(@PathVariable("estado") Estado estado) {
		return estadoRepository.save(estado);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		estadoRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Estado salvar(@RequestBody Estado estado) {
		return estadoRepository.save(estado);
	}
}
