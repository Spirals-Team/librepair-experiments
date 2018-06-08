package com.theeventsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theeventsspringboot.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
