package br.edu.fapce.nexti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.fapce.nexti.model.Biblioteca;
import br.edu.fapce.nexti.model.PalavrasBiblioteca;
import br.edu.fapce.nexti.repository.PalavrasBibliotecaRepository;

@Controller
public class PalavraController {

	@Autowired
	private PalavrasBibliotecaRepository repository;
	
	@RequestMapping("/biblioteca/{nome}")
	public String listaPalavras(Model model, Biblioteca biblioteca) {

		Iterable<PalavrasBiblioteca> palavras = repository.findByBiblioteca(biblioteca);

		model.addAttribute("palavras", palavras);

		return "open-library";
	}
	
}
