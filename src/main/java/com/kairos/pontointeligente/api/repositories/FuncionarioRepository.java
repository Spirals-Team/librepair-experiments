package com.kairos.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kairos.pontointeligente.api.entities.Funcionario;
import com.kairos.pontointeligente.api.entities.Pessoa;

@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Funcionario findByPessoa(Pessoa pessoa);

}
