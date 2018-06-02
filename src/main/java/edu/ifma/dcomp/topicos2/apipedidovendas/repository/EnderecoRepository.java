package edu.ifma.dcomp.topicos2.apipedidovendas.repository;

import edu.ifma.dcomp.topicos2.apipedidovendas.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
