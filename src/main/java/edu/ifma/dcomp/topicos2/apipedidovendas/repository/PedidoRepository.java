package edu.ifma.dcomp.topicos2.apipedidovendas.repository;

import edu.ifma.dcomp.topicos2.apipedidovendas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {


}
