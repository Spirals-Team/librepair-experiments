package br.com.una.projeto.controller;

import br.com.una.projeto.entity.Endereco;
import br.com.una.projeto.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@GetMapping("/listar")
	public List<Endereco> list() {
		return enderecoRepository.findAll();
	}

	@PostMapping("/editar/{endereco}")
	public Endereco editar(@PathVariable("endereco") Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		enderecoRepository.delete(id);
	}

	@PostMapping("/salvar")
	public Endereco salvar(@RequestBody Endereco endereco) {
		return enderecoRepository.save(endereco);
	}
}
