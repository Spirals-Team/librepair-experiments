package com.theeventsspringboot.resource;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theeventsspringboot.model.Tipoevento;
import com.theeventsspringboot.repository.TipoeventoRepository;

@Entity
@RequestMapping("/tipoeventos")
@CrossOrigin("${origem-permitida}")
public class TipoeventoResource {

	@Autowired
	private TipoeventoRepository tipoeventoRepository;

	@PostMapping
	public Tipoevento adicionar(@Valid @RequestBody Tipoevento tipoevento) {
		return tipoeventoRepository.save(tipoevento);
	}

	@GetMapping()
	public List<Tipoevento> listar() {
		return tipoeventoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tipoevento> buscar(@PathVariable Long id) {
		Tipoevento tipoevento = tipoeventoRepository.findById(id).get();

		if (tipoevento == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(tipoevento);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tipoevento> atualizar(@PathVariable Long id, @Valid @RequestBody Tipoevento tipoevento) {
		Tipoevento existente = tipoeventoRepository.findById(id).get();

		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(tipoevento, existente, "id");

		existente = tipoeventoRepository.save(existente);

		return ResponseEntity.ok(existente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		Tipoevento tipoevento = tipoeventoRepository.findById(id).get();

		if (tipoevento == null) {
			return ResponseEntity.notFound().build();
		}

		tipoeventoRepository.delete(tipoevento);

		return ResponseEntity.noContent().build();
	}
}
