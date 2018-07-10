package br.com.syntax.controledeponto.dao;

import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.repositories.EmpresaRepository;
import br.com.syntax.controledeponto.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EmpresaDao implements EmpresaService {

  private final Logger log = LoggerFactory.getLogger(EmpresaDao.class);

  @Autowired private EmpresaRepository repository;


  @Override
  public Optional<Empresa> buscaPorCnpj(String cnpj) {
    log.debug("Buscando empresa pelo cnpj: {} ", cnpj);
    return  Optional.ofNullable( this.repository.findByCnpj(cnpj) );
  }

  @Override
  public Optional<Empresa>  salva(Empresa empresa) {
    log.debug("Salvando empresa: {}", empresa.getRazaoSocial());
    return Optional.ofNullable( this.repository.save(empresa) );
  }
}
