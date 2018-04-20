package com.utn.tacs.grupo2.snake.repository;

import com.utn.tacs.grupo2.snake.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

}
