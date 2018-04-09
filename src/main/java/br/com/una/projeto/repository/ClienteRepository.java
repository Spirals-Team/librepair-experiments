package br.com.una.projeto.repository;

import br.com.una.projeto.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
}
