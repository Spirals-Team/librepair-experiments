package br.com.syntax.controledeponto.dao;

import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.repositories.FuncionarioRepository;
import br.com.syntax.controledeponto.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioDao implements FuncionarioService {

  private final Logger log = LoggerFactory.getLogger(FuncionarioDao.class);

  @Autowired private FuncionarioRepository repository;

  @Override
  public Optional<Funcionario> buscaPorEmail(String email) {
    log.debug("Retornando o funcionário que possui o email: {}", email);
    Funcionario funcionario = this.repository.getByEmail(email);
    return Optional.ofNullable(funcionario);
  }

  @Override
  public Optional<Funcionario> buscaPorCpf(String cpf) {
    log.debug("Retornando o funcionário que possui o cpf: {}", cpf);
    Funcionario funcionario = this.repository.getByCpf(cpf);
    return Optional.ofNullable(funcionario);  }

  @Override
  public Optional<Funcionario> buscaPorId(Long id) {
    log.debug("Retornando o funcionário que possui o id: {}", id);
    return this.repository.findById(id);
  }

  @Override
  public Funcionario salva(Funcionario funcionario) {
    log.debug("Salvando o funcionário: {}", funcionario.getNome());
    return this.repository.save(funcionario);
  }
}
