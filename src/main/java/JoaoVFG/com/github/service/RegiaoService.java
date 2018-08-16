package JoaoVFG.com.github.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Regiao;
import JoaoVFG.com.github.repositories.RegiaoRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class RegiaoService {

	@Autowired
	RegiaoRepository regiaoRepository;

	@Autowired
	EmpresaService empresaService;

	public Regiao findById(Integer id) {
		Optional<Regiao> regiao = Optional.ofNullable(regiaoRepository.buscaPorId(id));
		return regiao.orElseThrow(() -> new ObjectNotFoundException(
				"Regiao não encontrado! Id: " + id + ". Tipo: " + Regiao.class.getName()));
	}

	public List<Regiao> findAll() {
		return regiaoRepository.findAll();
	}

	public List<Regiao> findByEmpresa(Integer idEmpresa) {
		Optional<List<Regiao>> regioes = Optional
				.ofNullable(regiaoRepository.findByempresa(empresaService.findById(idEmpresa)));
		return regioes.orElseThrow(
				() -> new ObjectNotFoundException("Não foram encontradas regiões para a empresa.  Id empresa: "
						+ idEmpresa + ". Tipo: " + Regiao.class.getName()));
	}

	public List<Regiao> findByEmpresaAndDescricao(Integer idEmpresa, String descricao) {
		Optional<List<Regiao>> regioes = Optional
				.ofNullable(regiaoRepository.findByEmpresaAndDescricao(empresaService.findById(idEmpresa), descricao));
		return regioes.orElseThrow(() -> new ObjectNotFoundException(
				"Não foram encontradas regiões as restrições de pesquisa.  Id empresa: " + idEmpresa + ". Descrição  :"
						+ descricao + ". Tipo: " + Regiao.class.getName()));
	}

	public Regiao createRegiao(Regiao regiao) {
		regiao.setId(null);
		regiao = regiaoRepository.save(regiao);
		return findById(regiao.getId());
	}

	public void deletaRegiao(Integer id) {
		findById(id);

		try {
			regiaoRepository.deleteById(id);
		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSA REGIAO.");
		}
	}
	
	public Regiao updateRegiao(Regiao updateRegiao){
		Regiao regiao = findById(updateRegiao.getId());
		regiao.setDescricao(updateRegiao.getDescricao());
		regiao.setCeps(updateRegiao.getCeps());
		return regiaoRepository.save(regiao);
	}

}
