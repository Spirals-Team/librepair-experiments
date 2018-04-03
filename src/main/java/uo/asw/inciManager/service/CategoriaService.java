package uo.asw.inciManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Categoria;
import uo.asw.inciManager.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository c;

	public void addCategoria(Categoria c1) {
		c.save(c1);
	}
	
	public void deleteAll() {
		c.deleteAll();
		
	}
}
