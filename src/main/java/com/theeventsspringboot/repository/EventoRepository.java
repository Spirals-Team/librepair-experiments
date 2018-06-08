package com.theeventsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theeventsspringboot.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
