package asw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.entities.CamposCriticos;
import asw.repository.CamposCriticosRepository;

@Service
public class CamposCriticosService {
	
	@Autowired
	private CamposCriticosRepository ccRepository;
	
	public void addCampoCritico(CamposCriticos cc) {
		ccRepository.save( cc );
	}
	
	public List<CamposCriticos> getAll(){
		List<CamposCriticos> campos = new ArrayList<>();
		ccRepository.findAll().forEach(campos::add);
		return campos;
	}
	
	public CamposCriticos findByClave(String clave){
		CamposCriticos campo = ccRepository.findByClave(clave);
		return campo;
	}

	
}
