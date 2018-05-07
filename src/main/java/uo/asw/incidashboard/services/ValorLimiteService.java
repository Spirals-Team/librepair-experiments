package uo.asw.incidashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.ValorLimite;
import uo.asw.incidashboard.repositories.ValorLimiteRepository;

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

	public void update(String propiedad, String valorMaximo, String valorMinimo, String criticoMax, String criticoMin) {
		ValorLimite vL = valorLimiteRepository.findByPropiedad(propiedad);
		
		if(criticoMax.equals("true")) {
			vL.setMaxCritico(true);
		}else vL.setMaxCritico(false);
		
		if(criticoMin.equals("true")) {
			vL.setMinCritico(true);
		}else vL.setMinCritico(false);
		
		
		vL.setValorMax(Double.parseDouble(valorMaximo));
		vL.setValorMin(Double.parseDouble(valorMinimo));
		valorLimiteRepository.save(vL);
	}
	
	public ValorLimite findByPropiedad(String propiedad) {
		return valorLimiteRepository.findByPropiedad(propiedad);
	}
}
