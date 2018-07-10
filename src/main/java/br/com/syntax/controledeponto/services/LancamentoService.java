package br.com.syntax.controledeponto.services;

import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface LancamentoService {

  /**
   * Buscando os lançamentos de um funcionário
   * @param funcionario
   * @return List<Lancamento>
   */
  List<Lancamento> getByFuncionario(Funcionario funcionario);

  /**
   * Buscando os lançamentos de um funcionário com um limite feito por paginação
   * @param funcionario
   * @param page
   * @return List<Lancamento>
   */
  Page<Lancamento> getByFuncionario(Funcionario funcionario, PageRequest page);

  /**
   * Buscando um lançamento específico pelo seu id
   * @param id
   * @return List<Lancamento>
   */
  Optional<Lancamento> getById(Long id);

  /**
   * Grava um lançamento no banco de dados
   * @param lancamento
   * @return Lancamento
   */
  Lancamento grava(Lancamento lancamento);

  /**
   * Remove um lançamento no banco de dados
   * @param lancamento
   * @return void
   */
  void remove(Lancamento lancamento);


}
