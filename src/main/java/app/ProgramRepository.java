package app;

import app.Program;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

@RepositoryRestResource(collectionResourceRel="programs", path ="programs")
public interface ProgramRepository extends CrudRepository<Program, Long> {

    List<Program> findAll();
    List<Program> findByName(String name);
}