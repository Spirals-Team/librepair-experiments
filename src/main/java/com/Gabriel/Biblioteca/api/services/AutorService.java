package com.Gabriel.Biblioteca.api.services;

import java.util.Optional;

import com.Gabriel.Biblioteca.api.entities.Autor;

public interface AutorService {

	public Optional<Autor> buscaAutorPorCodigo(String codigo);

	public Autor persistir(Autor autor);

}
