package app;

import app.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="roles", path ="roles")
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}