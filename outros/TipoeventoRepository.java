package com.theeventsspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theeventsspringboot.model.Tipoevento;

public interface TipoeventoRepository extends JpaRepository<Tipoevento, Long> {

}
