package br.com.syntax.controledeponto.repositories;

import br.com.syntax.controledeponto.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

  @Transactional(readOnly = true) // Por se tratar de uma consulta não é necessário abrir uma transação que bloqueia o DB
  Empresa findByCnpj(String cnpj);
}
