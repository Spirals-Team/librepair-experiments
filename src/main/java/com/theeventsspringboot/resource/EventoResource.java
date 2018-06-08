package com.theeventsspringboot.resource;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.theeventsspringboot.model.Evento;
import com.theeventsspringboot.repository.EventoRepository;

@RestController
@RequestMapping("/eventos")
@CrossOrigin("${origem-permitida}")
public class EventoResource {

	@Autowired
	private EventoRepository eventoRepository;

	@PostMapping
	public Evento adicionar(@Valid @RequestBody Evento evento) {
		return eventoRepository.save(evento);
	}

	@GetMapping()
	public List<Evento> listar() {
		return eventoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Evento> findById(@PathVariable Long id) {
		Evento evento = eventoRepository.getOne(id);

		if (evento == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(evento);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Evento> atualizar(@PathVariable Long id, @Valid @RequestBody Evento evento) {
		Evento existente = eventoRepository.findById(id).get();

		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(evento, existente, "id");

		existente = eventoRepository.save(existente);

		return ResponseEntity.ok(existente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		Evento evento = eventoRepository.findById(id).get();

		if (evento == null) {
			return ResponseEntity.notFound().build();
		}

		eventoRepository.delete(evento);

		return ResponseEntity.noContent().build();
	}

}
