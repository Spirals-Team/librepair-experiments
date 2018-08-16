package JoaoVFG.com.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Estado;
import JoaoVFG.com.github.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired(required = true)
	private EstadoRepository estadoRepository;

	
	public Estado findById(Integer id) {
		Estado estado = estadoRepository.buscaPorId(id);

		return estado;
	}

	
	public List<Estado> findAllEstados() {
		return estadoRepository.findAll();
	}
	
	
	public Estado findBySigla(String sigla) {
		Estado estado = estadoRepository.findBysigla(sigla);
		
		return estado;
		
	}
	
	public Estado findByNome(String nome) {
		Estado estado = estadoRepository.findBynomeContains(nome);
		
		return estado;
	}
	
	
	
	public Estado createEstado(Estado estado) {
		estado.setId(null);
		estado = estadoRepository.save(estado);
		return findById(estado.getId());
	}
	
	
	public Estado updateEstado(Estado updateEstado) {
		Estado estado = findById(updateEstado.getId());
		
		estado.setNome(updateEstado.getNome());
		estado.setSigla(updateEstado.getSigla());
		
		return estadoRepository.save(estado);
	}
	
	
	public void deleteEstado(Integer id) {
		findById(id);
		
		try {
			estadoRepository.deleteById(id);
		}catch(DataIntegrityViolationException erro){
			throw new JoaoVFG.com.github.services.exception.DataIntegrityException
						("NAO E POSSIVEL EXCLUIR UM ESTADO QUE POSSUA CIDADES VINCULADAS");
			//futuro código para setar excluido = 1
		}
	}
	
	public Estado findBySiglaAux(String sigla) {
		try {
			return estadoRepository.findBySiglaAux(sigla);
		} catch (NullPointerException erro) {
			return null;
		}
	}
}
