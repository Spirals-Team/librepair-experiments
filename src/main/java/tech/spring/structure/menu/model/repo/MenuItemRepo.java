package tech.spring.structure.menu.model.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import tech.spring.structure.menu.model.MenuItem;
import tech.spring.structure.model.repo.StructureRepo;

@Repository
@RepositoryRestResource(exported = false)
public interface MenuItemRepo extends StructureRepo<MenuItem> {

}
