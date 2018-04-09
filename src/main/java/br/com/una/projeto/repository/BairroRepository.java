package br.com.una.projeto.repository;

import br.com.una.projeto.entity.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BairroRepository extends JpaRepository<Bairro, Integer> {
}
