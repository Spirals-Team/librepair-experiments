package inciManager.uo.asw.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inciManager.uo.asw.dbManagement.model.Propiedad;
import inciManager.uo.asw.mvc.repository.PropiedadRepository;

@Service
public class PropiedadesService {

	@Autowired
	private PropiedadRepository p;

	public void addPropiedad(Propiedad p1) {
		p.save(p1);
	}
	
	public void deleteAll() {
		p.deleteAll();
		
	}
}
