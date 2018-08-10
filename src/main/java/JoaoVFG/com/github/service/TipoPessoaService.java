package JoaoVFG.com.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.TipoPessoa;
import JoaoVFG.com.github.repositories.TipoPessoaRepository;

@Service
public class TipoPessoaService {

	@Autowired
	TipoPessoaRepository tipoPessoaRepository;
	
	
	public TipoPessoa findById(Integer id) {
		return tipoPessoaRepository.buscaPorId(id);
	}
	
	
	public TipoPessoa findBydescricao(String descricao) {
		return tipoPessoaRepository.findBydescricao(descricao);
	}
	
	public List<TipoPessoa> findAll(){
		return tipoPessoaRepository.findAll();
	}
	
}
