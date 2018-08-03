package JoaoVFG.com.github.entity.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Cargo;
import JoaoVFG.com.github.repositories.CargoRepository;
import JoaoVFG.com.github.services.exception.DataIntegrityException;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	public Cargo findById(Integer id) {
		Optional<Cargo> cargo = Optional.ofNullable(cargoRepository.buscaPorId(id));
		return cargo.orElseThrow(() -> new ObjectNotFoundException(
				"Cargo não encontrado! Id: " + id + ". Tipo: " + Cargo.class.getName()));
	}
	
	public List<Cargo> findAll(){
		return cargoRepository.findAll();
	}
	
	public List<Cargo> findByDescricao(String descricao){
		Optional<List<Cargo>> cargos = Optional.ofNullable(cargoRepository.findBydescricaoContains(descricao));
		return cargos.orElseThrow(() -> new ObjectNotFoundException(
				"Cargo não encontrado! Descricao: " + descricao + ". Tipo: " + Cargo.class.getName()));
	}
	
	public Cargo create(String descricao) {
		if(cargoRepository.findBydescricao(descricao) == null) {
			Cargo cargo = new Cargo(null, descricao);
			cargo = cargoRepository.save(cargo); 
			return findById(cargo.getId());
		}else {
			throw new DataIntegrityException("NÃO É POSSIVEL CADASTRAR ESSE FUNCIONARIO");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			cargoRepository.delete(findById(id));
		} catch (DataIntegrityException e) {
			throw new DataIntegrityException("NAO E POSSIVEL EXCLUIR ESSE FUNCIONARIO.");
		}
	}
}
