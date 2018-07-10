package br.com.syntax.controledeponto.dao;

import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.entities.Lancamento;
import br.com.syntax.controledeponto.repositories.LancamentoRespository;
import br.com.syntax.controledeponto.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoDao implements LancamentoService {

  private final Logger log = LoggerFactory.getLogger(LancamentoDao.class);

  @Autowired private LancamentoRespository respository;

  @Override
  public List<Lancamento> getByFuncionario(Funcionario funcionario) {
    log.debug("Buscando os lançamentos de ponto do funcionário: {}", funcionario.getNome());
    return this.respository.findByFuncionarioId(funcionario.getId());
  }

  @Override
  public Page<Lancamento> getByFuncionario(Funcionario funcionario, PageRequest page) {
    log.debug("Buscando os {} primeiros lançamentos de ponto do funcionário: {}", page.getPageNumber(), funcionario.getNome());
    return this.respository.findByFuncionarioId(funcionario.getId(), page);
  }

  @Override
  public Optional<Lancamento> getById(Long id) {
    log.debug("Buscando o lançamento de código: {}", id);
    return this.respository.findById(id);
  }

  @Override
  public Lancamento grava(Lancamento lancamento) {
    return this.respository.save(lancamento);
  }

  @Override
  public void remove(Lancamento lancamento) {
    this.respository.delete(lancamento);
  }
}
