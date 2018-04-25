package ar.edu.undav.semillero.domain.repository;

import ar.edu.undav.semillero.domain.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long> {

    Optional<Node> findByName(String name);
}
