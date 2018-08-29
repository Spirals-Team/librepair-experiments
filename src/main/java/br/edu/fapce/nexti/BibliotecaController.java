package br.edu.fapce.nexti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.fapce.nexti.model.Biblioteca;
import br.edu.fapce.nexti.repository.BibliotecaRepository;

@Controller
public class BibliotecaController {

	@Autowired
	private BibliotecaRepository repository;
	
	@RequestMapping("/open-library")
	public String biblioteca(Model model) {
		Iterable<Biblioteca> bibliotecas = repository.findAll();

		model.addAttribute("bibliotecas", bibliotecas);

		return "open-library";
	}
	
}
