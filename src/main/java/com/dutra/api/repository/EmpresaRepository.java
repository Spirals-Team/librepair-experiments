package com.dutra.api.repository;

import com.dutra.api.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findByCnpj(String cnpj);

    Empresa findTopById(Long id);
}
