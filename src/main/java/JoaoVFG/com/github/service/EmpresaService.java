package JoaoVFG.com.github.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.dto.EmpresaDTO;
import JoaoVFG.com.github.repositories.EmpresaRepository;
import JoaoVFG.com.github.repositories.TipoEmpresaRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class EmpresaService {

	@Autowired
	TipoEmpresaRepository tipoEmpresaRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	PessoaService pessoaService;

	public Empresa findById(Integer id) {
		Optional<Empresa> empresa = Optional.ofNullable(empresaRepository.buscaPorId(id));
		return empresa.orElseThrow(() -> new ObjectNotFoundException(
				"Empresa não encontrado! Id: " + id + ". Tipo: " + Pessoa.class.getName()));
	}

	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}

	public Empresa findByIdPessoa(Integer id) {
		Pessoa pessoa = pessoaService.findById(id);
		Optional<Empresa> empresa = Optional.ofNullable(empresaRepository.findBypessoa(pessoa));
		return empresa
				.orElseThrow(() -> new ObjectNotFoundException("Não houverem resultados para a restrição de pesquisa."
						+ "Metodo: Busca Por Id. Id:" + id));
	}

	public List<Empresa> findByTipoEmpresa(Integer id) {
		Optional<List<Empresa>> empresas = Optional
				.ofNullable(empresaRepository.findBytipoEmpresa(tipoEmpresaRepository.buscaPorId(id)));
		return empresas
				.orElseThrow(() -> new ObjectNotFoundException("Não houverem resultados para a restrição de pesquisa"
						+ "Metodo: Busca por tipo empresa. Tipo Empresa" + id));
	}
	
	public List<Empresa> findTransportadoras(){
		Optional<List<Empresa>> empresas = Optional
				.ofNullable(empresaRepository.findByTransportadora(1));
		return empresas
				.orElseThrow(() -> new ObjectNotFoundException("Não houverem resultados para a restrição de pesquisa"));
	}
	
	public List<Empresa> findByMatriz(Integer id){
		Optional<List<Empresa>> empresas = Optional
				.ofNullable(empresaRepository.findByempresaMatrizId(id));
		return empresas
				.orElseThrow(() -> new ObjectNotFoundException("Não houverem resultados para a restrição de pesquisa"
						+ "Metodo: Busca Por Id Matriz: Id Matriz: " + id));
	}
	
	public Empresa createEmpresa(EmpresaDTO dto) {
		Empresa empresa = empresaFromEmpresaDTO(dto);
		if(empresaRepository.findBypessoa(pessoaService.findById(empresa.getPessoa().getId())) == null) {
			empresa.setId(null);
			empresa = empresaRepository.save(empresa);
		}else {
			throw new DataIntegrityException("NÃO É POSSIVEL CADASTRAR ESSE EMPRESA, POIS ELA JA ESTA CADASTRADA");
		}
		
		
		return findById(empresa.getId());
	}
	
	public Empresa updateEmpresa(Integer id, EmpresaDTO empresaUpdate) {
		Empresa empresa = findById(id);
		if(!(empresa == null)) {
			empresa.setPessoa(pessoaService.findById(empresaUpdate.getPessoa()));
			empresa.setTipoEmpresa(tipoEmpresaRepository.buscaPorId(empresaUpdate.getTipoEmpresa()));
			empresa.setTransportadora(empresaUpdate.getTransportadora());
			empresa.setEmpresaMatrizId(empresaUpdate.getEmpresaMatriz());
			
			return empresaRepository.save(empresa);
		}else {
			throw new ObjectNotFoundException("Não houverem resultados para a restrição de pesquisa");
		}
	}
	
	public void deletarEmpresa(Empresa empresa) {
		findById(empresa.getId());

		try {
			empresaRepository.deleteById(empresa.getId());
		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSA PESSOA.");
		}
	}
	
	public Empresa empresaFromEmpresaDTO(EmpresaDTO dto) {
		Empresa empresa = new Empresa();
		empresa.setId(null);
		empresa.setPessoa(pessoaService.findById(dto.getPessoa()));
		empresa.setTipoEmpresa(tipoEmpresaRepository.buscaPorId(dto.getTipoEmpresa()));
		empresa.setTransportadora(dto.getTransportadora());
		empresa.setEmpresaMatrizId(dto.getEmpresaMatriz());
		
		return empresa;
		
	}
}
