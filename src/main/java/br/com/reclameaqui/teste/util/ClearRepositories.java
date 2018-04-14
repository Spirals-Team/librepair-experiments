package br.com.reclameaqui.teste.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.reclameaqui.teste.repository.ClienteRepository;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;


@Component
public class ClearRepositories {

	@Autowired
	private ReclamacaoRepository reclamacaoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	public void clear() {
		reclamacaoRepository.deleteAll();
		clienteRepository.deleteAll();
	}

}
