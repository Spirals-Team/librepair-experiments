package br.edu.fapce.nexti.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.edu.fapce.nexti.model.Biblioteca;
import br.edu.fapce.nexti.model.PalavrasBiblioteca;

public interface PalavrasBibliotecaRepository extends CrudRepository<PalavrasBiblioteca, Long>{
	
	List<PalavrasBiblioteca> findByBiblioteca (Biblioteca biblioteca);
	
}
