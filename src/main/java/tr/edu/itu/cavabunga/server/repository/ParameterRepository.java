package tr.edu.itu.cavabunga.server.repository;

import tr.edu.itu.cavabunga.lib.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Optional<Parameter> findById(Long Id);
}
