package JoaoVFG.com.github.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Funcionario;
import JoaoVFG.com.github.entity.dto.FuncionarioDTO;
import JoaoVFG.com.github.repositories.CargoRepository;
import JoaoVFG.com.github.repositories.FuncionarioRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private CargoRepository cargoRepository;

	public Funcionario findById(Integer id) {
		Optional<Funcionario> funcionario = Optional.ofNullable(funcionarioRepository.buscaPorId(id));
		return funcionario.orElseThrow(() -> new ObjectNotFoundException(
				"Funcionario não encontrado! Id: " + id + ". Tipo: " + Funcionario.class.getName()));
	}

	public List<Funcionario> findAll() {
		return funcionarioRepository.findAll();
	}

	public Funcionario findByPessoa(Integer idPessoa) {
		Optional<Funcionario> funcionario = Optional
				.ofNullable(funcionarioRepository.findBypessoa(pessoaService.findById(idPessoa)));
		return funcionario.orElseThrow(() -> new ObjectNotFoundException(
				"Funcionario não encontrado! Id da Pessoa: " + idPessoa + ". Tipo: " + Funcionario.class.getName()));
	}

	public List<Funcionario> findByEmpresa(Integer idEmpresa) {
		Optional<List<Funcionario>> funcionarios = Optional
				.ofNullable(funcionarioRepository.findByempresa(empresaService.findById(idEmpresa)));
		return funcionarios
				.orElseThrow(() -> new ObjectNotFoundException("Funcionarios não encontrados! Id da Empresa: "
						+ idEmpresa + ". Tipo: " + Funcionario.class.getName()));
	}

	public List<Funcionario> findByEmpresaCargo(Integer idEmpresa, Integer idCargo) {
		Optional<List<Funcionario>> funcionarios = Optional.ofNullable(funcionarioRepository
				.buscaPorEmpresaECargo(empresaService.findById(idEmpresa), cargoRepository.buscaPorId(idCargo)));
		return funcionarios
				.orElseThrow(() -> new ObjectNotFoundException("Funcionarios não encontrados! Id da Empresa: "
						+ idEmpresa + ". Id Cargo: " + idCargo + ". Tipo: " + Funcionario.class.getName()));
	}

	public Funcionario insertFuncionario(FuncionarioDTO dto) {
		Funcionario funcionario = funcionarioFromDto(dto);
		if (funcionarioRepository.buscaPorEmpresaECargo(funcionario.getEmpresa(), funcionario.getCargo()) == null) {
			funcionario.setId(null);
			funcionario = funcionarioRepository.save(funcionario);
			return findById(funcionario.getId());
		} else {
			throw new DataIntegrityException("NÃO É POSSIVEL CADASTRAR ESSE FUNCIONARIO");
		}
	}
	
	public Funcionario updateFuncionario(Funcionario updateFuncionario) {
		Funcionario funcionario = findById(updateFuncionario.getId());
		
		funcionario.setPessoa(pessoaService.findById(updateFuncionario.getId()));
		funcionario.setEmpresa(empresaService.findById(updateFuncionario.getEmpresa().getId()));
		funcionario.setCargo(cargoRepository.buscaPorId(updateFuncionario.getCargo().getId()));
		
		return funcionarioRepository.save(funcionario);
	}

	public void deletaFuncionario(Integer id) {
		findById(id);
		try {
			funcionarioRepository.deleteById(id);
		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSE FUNCIONARIO.");
		}
	}

	public Funcionario funcionarioFromDto(FuncionarioDTO dto) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCargo(cargoRepository.buscaPorId(dto.getIdCargo()));
		funcionario.setEmpresa(empresaService.findById(dto.getIdEmpresa()));
		funcionario.setPessoa(pessoaService.findById(dto.getIdPessoa()));

		return funcionario;
	}
}
