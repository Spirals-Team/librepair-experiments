package br.net.itech.controllers.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.net.itech.response.Response;

public interface Read<T> {
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<T>> read(@PathVariable("id") String id);
}