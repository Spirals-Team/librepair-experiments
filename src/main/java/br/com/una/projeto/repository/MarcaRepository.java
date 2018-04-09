package br.com.una.projeto.repository;

import br.com.una.projeto.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}
