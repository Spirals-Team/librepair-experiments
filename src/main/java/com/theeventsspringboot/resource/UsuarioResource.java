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

import com.theeventsspringboot.model.Usuario;
import com.theeventsspringboot.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("${origem-permitida}")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public Usuario adicionar(@Valid @RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@GetMapping()
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();

		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		Usuario existente = usuarioRepository.findById(id).get();

		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(usuario, existente, "id");

		existente = usuarioRepository.save(existente);

		return ResponseEntity.ok(existente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();

		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		usuarioRepository.delete(usuario);

		return ResponseEntity.noContent().build();
	}

}
