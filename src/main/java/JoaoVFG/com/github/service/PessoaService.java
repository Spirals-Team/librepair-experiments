package JoaoVFG.com.github.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.dto.PessoaFisicaDTO;
import JoaoVFG.com.github.entity.dto.PessoaJuridicaDTO;
import JoaoVFG.com.github.repositories.PessoaRepository;
import JoaoVFG.com.github.repositories.TipoPessoaRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	TipoPessoaRepository tipoPessoaRepository;

	public Pessoa findById(Integer id) {
		Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.buscaPorId(id));

		return pessoa.orElseThrow(() -> new ObjectNotFoundException(
				"Pessoa não encontrado! Id: " + id + ". Tipo: " + Pessoa.class.getName()));
	}

	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	public List<Pessoa> findByTipo(Integer tipo) {
		Optional<List<Pessoa>> pessoas = Optional.ofNullable(pessoaRepository.findBytipo(tipoPessoaRepository.findByid(tipo)));
		return pessoas.orElseThrow(() -> new ObjectNotFoundException(
				"Não existem pessoas para o tipo procurado! Id: " + tipo + ". Tipo: " + Pessoa.class.getName()));
	}

	public Pessoa findByCpf(String cpf) {
		Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.findBycpf(cpf));
		return pessoa.orElseThrow(() -> new ObjectNotFoundException(
				"Não existem pessoas para o tipo procurado! CPF: " + cpf + ". Tipo: " + Pessoa.class.getName()));
	}

	public Pessoa findByCnpj(String cnpj) {
		Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.findBycnpj(cnpj));
		return pessoa.orElseThrow(() -> new ObjectNotFoundException(
				"Não existem pessoas para o tipo procurado! CNPJ: " + cnpj + ". Tipo: " + Pessoa.class.getName()));
	}

	public List<Pessoa> findByrazaoSocial(String razaoSocial) {
		Optional<List<Pessoa>> pessoas = Optional.ofNullable(pessoaRepository.findByrazaoSocialContains(razaoSocial));
		return pessoas
				.orElseThrow(() -> new ObjectNotFoundException("Não existem pessoas para o tipo procurado! Razão: "
						+ razaoSocial + ". Tipo: " + Pessoa.class.getName()));
	}

	public Pessoa create(Pessoa pessoa) {
		pessoa.setId(null);
		pessoa = pessoaRepository.save(pessoa);
		return findById(pessoa.getId());
	}
	
	public Pessoa createPF(PessoaFisicaDTO dto) {
		Pessoa pessoa = PFFromDto(dto);
		if(pessoaRepository.findBycpf(pessoa.getCpf())==null) {
			pessoa.setId(null);
			pessoa = pessoaRepository.save(pessoa);
			return findById(pessoa.getId());
		}else {
			throw new DataIntegrityException("NÃO É POSSIVEL CADASTRAR ESSE CLIENTE");
		}
	}
	
	public Pessoa createPJ(PessoaJuridicaDTO dto) {
		Pessoa pessoa = PJFromDTO(dto);
		if(pessoaRepository.findBycnpj(pessoa.getCnpj())==null) {
			pessoa.setId(null);
			pessoa = pessoaRepository.save(pessoa);
			return findById(pessoa.getId());
		}else {
			throw new DataIntegrityException("NÃO É POSSIVEL CADASTRAR ESSE CLIENTE");
		}
	}

	public Pessoa updatePessoa(Pessoa updatePessoa) {
		Pessoa pessoa = findById(updatePessoa.getId());

		pessoa.setCnpj(updatePessoa.getCnpj());
		pessoa.setCpf(updatePessoa.getCpf());
		pessoa.setDataNascimento(updatePessoa.getDataNascimento());
		pessoa.setNome(updatePessoa.getNome());
		pessoa.setRazaoSocial(updatePessoa.getRazaoSocial());
		pessoa.setSexo(updatePessoa.getSexo());

		return pessoaRepository.save(pessoa);
	}

	public void deletarPessoa(Pessoa pessoa) {
		findById(pessoa.getId());

		try {
			pessoaRepository.deleteById(pessoa.getId());
		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSA PESSOA.");
		}
	}
	
	
	public Pessoa PJFromDTO(PessoaJuridicaDTO dto) {
		Pessoa pessoa = new Pessoa();
		pessoa.setCnpj(dto.getCnpj());
		pessoa.setRazaoSocial(dto.getRazaoSocial());
		pessoa.setTipo(tipoPessoaRepository.findByid(dto.getTipo()));
		return pessoa;
	}
	
	public Pessoa PFFromDto(PessoaFisicaDTO dto) {
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(dto.getCpf());
		pessoa.setNome(dto.getNome());
		pessoa.setDataNascimento(dto.getDataNascimento());
		pessoa.setSexo(dto.getSexo());
		pessoa.setTipo(tipoPessoaRepository.findByid(dto.getTipo()));
		return pessoa;
	}

}
