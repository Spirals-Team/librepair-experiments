package br.com.syntax.controledeponto.repositories;

import br.com.syntax.controledeponto.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

  Funcionario getByCpf(String cpf);
  Funcionario getByEmail(String email);

}
