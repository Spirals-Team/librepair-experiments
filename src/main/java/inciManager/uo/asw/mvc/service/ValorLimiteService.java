package inciManager.uo.asw.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import inciManager.uo.asw.dbManagement.model.ValorLimite;
import inciManager.uo.asw.mvc.repository.ValorLimiteRepository;

@Service
public class ValorLimiteService {
	@Autowired
	private ValorLimiteRepository valorLimiteRepository;

	public void addValorLimite(ValorLimite v1) {
		valorLimiteRepository.save(v1);
	}

	public Page<ValorLimite> findAll(Pageable pageable) {
		return valorLimiteRepository.findAll(pageable);
	}

	public void deleteAll() {
		valorLimiteRepository.deleteAll();
	}
	public List<ValorLimite> findAll(){
		return valorLimiteRepository.findAll();
	}
	
	public ValorLimite findByPropiedad(String propiedad) {
		return valorLimiteRepository.findByPropiedad(propiedad);
	}
}
