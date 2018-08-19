package JoaoVFG.com.github.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.dto.request.insert.InsertTelefoneDTO;
import JoaoVFG.com.github.entity.Telefone;
import JoaoVFG.com.github.repositories.TelefoneRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class TelefoneService {

	@Autowired
	PessoaService pessoaService;

	@Autowired
	TelefoneRepository telefoneRepository;

	public Telefone findById(Integer id) {
		Optional<Telefone> telefone = Optional.ofNullable(telefoneRepository.buscaPorId(id));
		return telefone.orElseThrow(() -> new ObjectNotFoundException(
				"Telefone não encontrado! Id: " + id + ". Tipo: " + Telefone.class.getName()));
	}

	public List<Telefone> findAll() {
		return telefoneRepository.findAll();
	}

	public List<Telefone> findByPessoa(Integer pessoaId) {
		Optional<List<Telefone>> telefones = Optional
				.ofNullable(telefoneRepository.findBypessoa(pessoaService.findById(pessoaId)));
		return telefones.orElseThrow(() -> new ObjectNotFoundException(
				"Telefone não encontrado! Id pessoa: " + pessoaId + ". Tipo: " + Telefone.class.getName()));
	}

	public List<Telefone> findByPessoaTipoNum(Integer pessoaId, String tipoNum) {
		Optional<List<Telefone>> telefones = Optional
				.ofNullable(telefoneRepository.findBypessoaAndtipoNumero(pessoaId, tipoNum));
		return telefones.orElseThrow(() -> new ObjectNotFoundException(
				"Telefones não encontrados! Id pessoa: " + pessoaId + ". Tipo: " + Telefone.class.getName()));
	}

	public Telefone create(Telefone telefone) {
		telefone.setId(null);
		telefone = telefoneRepository.save(telefone);
		return findById(telefone.getId());
	}

	public Telefone createFromDto(InsertTelefoneDTO dto) {
		Telefone tel = new Telefone(null, dto.getTipoNumero(), dto.getNumero(), 
				pessoaService.findById(dto.getIdPessoa()));
		tel = telefoneRepository.save(tel);
		return findById(tel.getId());
	}

	public Telefone updateTelefone(Telefone updateTelefone) {
		Telefone telefone = findById(updateTelefone.getId());

		telefone.setNumero(updateTelefone.getNumero());
		telefone.setTipoNumero(updateTelefone.getTipoNumero());

		return telefoneRepository.save(telefone);
	}

	public void deletaTelefone(Integer id) {
		findById(id);

		try {
			telefoneRepository.deleteById(id);
		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSE TELEFONE.");
		}
	}
}
