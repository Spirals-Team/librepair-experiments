package br.net.itech.controllers.crud;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.net.itech.response.Response;

public interface Create<T> {
	@PostMapping
	public ResponseEntity<Response<T>> create(@Valid @RequestBody T objectDto, BindingResult bindingResult)
			throws NoSuchAlgorithmException;
}