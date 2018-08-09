package JoaoVFG.com.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Cep;
import JoaoVFG.com.github.entity.Cidade;
import JoaoVFG.com.github.repositories.CepRepository;
import JoaoVFG.com.github.repositories.CidadeRepository;
import JoaoVFG.com.github.service.consultaCep.CreateCep;
import JoaoVFG.com.github.services.exception.DataIntegrityException;

@Service
public class CepService {

	@Autowired
	CepRepository cepRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	CreateCep createCep;

	public Cep findById(Integer id) {
		Cep cep = cepRepository.buscaPorId(id);

		return cep;
	}

	public List<Cep> findAll() {
		return cepRepository.findAll();
	}

	public Cep findByCep(String cepBusca) {
		cepBusca = formataCep(cepBusca);
		
		Cep cep = cepRepository.findBycep(cepBusca);
		
		if (cep == null) {
			cep = createCep.generateCep(cepBusca.toString());
		}

		return cep;
	}

	public List<Cep> findByNomeRua(String nomeRua) {
		return cepRepository.findBynomeRua(nomeRua);
	}
	
	public List<Cep> findByCidade(String cidade){
		Cidade cidadeBusca = cidadeRepository.findBynome(cidade);
		return cepRepository.findByCidade(cidadeBusca);
	}

	public Cep createCep(Cep cep) {
		cep.setId(null);
		cep = cepRepository.save(cep);
		return findById(cep.getId());
	}

	public Cep updateCep(Cep updateCep) {
		Cep cep = findById(updateCep.getId());

		cep.setCep(updateCep.getCep());
		cep.setCidade(updateCep.getCidade());
		cep.setNomeRua(updateCep.getNomeRua());
		cep.setBairro(updateCep.getBairro());

		return cepRepository.save(cep);
	}

	public void deletarCep(Cep cep) {
		findById(cep.getId());

		try {
			cepRepository.deleteById(cep.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR UM CEP QUE POSSUA ENTREGAS VINCULADAS.");
			// futuro código para setar excluido = 1
		}
	}
	
	public String cepToStringEndereco(String cepBusca, String numLogradouro) {
		Cep cep = new Cep();
		StringBuilder builder = new StringBuilder();
		
		
		cep = findByCep(cepBusca);
		
		builder.append(cep.getNomeRua());
		builder.append(" ");
		builder.append(numLogradouro);
		builder.append(", ");
		builder.append(cep.getBairro());
		builder.append(", ");
		builder.append(cep.getCidade().getNome());
		builder.append(", ");
		builder.append(cep.getCidade().getEstado().getNome());
		builder.append(", ");
		builder.append(cep.getCep());
		
		return builder.toString();
		
	}
	
	public String formataCep(String cep) {
		return cep.replace("-", "");
	}
}
