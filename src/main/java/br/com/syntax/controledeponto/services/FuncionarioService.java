package br.com.syntax.controledeponto.services;

import br.com.syntax.controledeponto.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

  /**
   * Retorna o funcionário que possui um email igual ao fornecido
   * @param email
   * @return Optional<Funcionario>
   */
  Optional<Funcionario> buscaPorEmail(String email);


  /**
   * Retorna o funcionário que possui um cpf igual ao fornecido
   * @param cpf
   * @return Optional<Funcionario>
   */
  Optional<Funcionario> buscaPorCpf(String cpf);

  /**
   * Retorna o funcionário que possui o ID fornecido
   * @param id
   * @return Optional<Funcionario>
   */
  Optional<Funcionario> buscaPorId(Long id);

  /**
   * Salva o funcionário fornecido no banco de dados e retorna o que foi salvo
   * @param funcionario
   * @return Funcionario
   */
  Funcionario salva(Funcionario funcionario);

}
