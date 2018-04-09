package br.com.una.projeto.controller;

import br.com.una.projeto.entity.Bairro;
import br.com.una.projeto.repository.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bairro")
public class BairroController {
	
	@Autowired
	private BairroRepository bairroRepository;

	@GetMapping("/listar")
	public List<Bairro> list() {
		return bairroRepository.findAll();
	}

	@PostMapping("/editar/{bairro}")
	public Bairro editar(@PathVariable("bairro") Bairro bairro) {
		return bairroRepository.save(bairro);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		bairroRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Bairro salvar(@RequestBody Bairro bairro) {
		return bairroRepository.save(bairro);
	}

}
