package edu.ifma.dcomp.topicos2.apipedidovendas.repository;

import edu.ifma.dcomp.topicos2.apipedidovendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByNome(String nome);

}
