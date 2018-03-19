package com.jairosousa.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jairosousa.cursomc.domain.Categoria;
import com.jairosousa.cursomc.dto.CategoriaDTO;
import com.jairosousa.cursomc.repository.CategoriaRepository;
import com.jairosousa.cursomc.services.exceptios.DataIntegrityException;
import com.jairosousa.cursomc.services.exceptios.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Categoria obj = categoriaRepository.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Categoria.class.getName());
		}
		return obj;
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return categoriaRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			categoriaRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que tem produtos");
		}
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
