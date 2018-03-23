package com.kairos.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kairos.pontointeligente.api.entities.Pessoa;

@Transactional(readOnly = true)
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Pessoa findByCpf(String cpf);

	Pessoa findByEmail(String email);

	Pessoa findByCpfOrEmail(String cpf, String email);

}
