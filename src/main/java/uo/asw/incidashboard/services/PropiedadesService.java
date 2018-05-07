package uo.asw.incidashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Propiedad;
import uo.asw.incidashboard.repositories.PropiedadRepository;

@Service
public class PropiedadesService {

	@Autowired
	private PropiedadRepository p;

	public void addPropiedad(Propiedad p1) {
		p.save(p1);
	}
	
	public Page<Propiedad> findAll(Pageable pageable){
		return p.findAll(pageable);
	}
	
	public void deleteAll() {
		p.deleteAll();
	}
}