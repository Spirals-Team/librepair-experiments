package br.com.una.projeto.controller;

import br.com.una.projeto.entity.Cidade;
import br.com.una.projeto.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@GetMapping("/listar")
	public List<Cidade> list() {
		return cidadeRepository.findAll();
	}

	@PostMapping("/editar/{cidade}")
	public Cidade editar(@PathVariable("cidade") Cidade cidade) {
		return cidadeRepository.save(cidade);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		cidadeRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Cidade salvar(@RequestBody Cidade cidade) {
		return cidadeRepository.save(cidade);
	}
}
