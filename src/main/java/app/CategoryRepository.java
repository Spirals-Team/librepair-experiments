package app;

import app.Program;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

@RepositoryRestResource(collectionResourceRel="categories", path ="categories")
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll();
    List<Category> findByName(String name);
}