package JoaoVFG.com.github.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.dto.request.insert.InsertEnderecoDTO;
import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.repositories.EnderecoRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	PessoaService pessoaService;

	@Autowired
	CepService cepService;

	public Endereco findById(Integer id) {
		Optional<Endereco> endereco = Optional.ofNullable(enderecoRepository.buscaPorId(id));
		return endereco.orElseThrow(() -> new ObjectNotFoundException(
				"Endereço não encontrado! Id: " + id + ". Tipo: " + Pessoa.class.getName()));
	}

	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}

	public Endereco findByPessoa(Integer id) {
		Optional<Endereco> endereco = Optional.ofNullable(enderecoRepository.findBypessoa(pessoaService.findById(id)));
		return endereco.orElseThrow(() -> new ObjectNotFoundException(
				"Essa pessoa não possui endereco Cadastrado. Id Pessoa: " + id + ". Tipo: " + Pessoa.class.getName()));
	}

	public List<Endereco> findByCep(String cep) {
		Optional<List<Endereco>> enderecos = Optional.ofNullable(enderecoRepository.findBycep(cepService.findByCep(cep)));
		return enderecos.orElseThrow(() -> new ObjectNotFoundException(
				"Não existem Enderecos cadastrados para este cep" + cep + ". Tipo: " + Pessoa.class.getName()));
	}

	public Endereco create(Endereco endereco) {
		endereco.setId(null);
		endereco = enderecoRepository.save(endereco);
		return findById(endereco.getId());
	}

	public Endereco updateEndereco(Integer id, InsertEnderecoDTO enderecoUpdate) {
		
		Endereco endereco = findById(id);

		endereco.setCep(cepService.findByCep(enderecoUpdate.getCep()));
		endereco.setComplemento(enderecoUpdate.getComplemento());
		endereco.setNumeroLogradouro(enderecoUpdate.getNumeroLogradouro());

		return enderecoRepository.save(endereco);

	}

	public void deletarEndereco(Endereco endereco) {
		findById(endereco.getId());

		try {
			enderecoRepository.deleteById(endereco.getId());

		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSE ENDERECO.");
		}
	}

	public Endereco createFromDTO(InsertEnderecoDTO insertEnderecoDTO) {
		Endereco endereco = new Endereco(null, pessoaService.findById(insertEnderecoDTO.getIdPessoa()),
				cepService.findByCep(insertEnderecoDTO.getCep()), insertEnderecoDTO.getNumeroLogradouro(),
				insertEnderecoDTO.getComplemento());
		endereco = enderecoRepository.save(endereco);
		return findById(endereco.getId());
	}
}
