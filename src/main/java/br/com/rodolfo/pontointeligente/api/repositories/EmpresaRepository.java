package br.com.rodolfo.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.pontointeligente.api.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    //Por se tratar de um método apenas de consulta (faz um select no banco) ele não precisa de transação,
    //não precisa bloquear o banco de dados
    @Transactional(readOnly = true)
    Empresa findByCnpj(String cnpj);

}