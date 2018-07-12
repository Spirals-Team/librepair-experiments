package apna.Maholla.repository;

import apna.Maholla.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles findFirstById(Integer id);

    Roles findFirstByRoleName(String roleName);
}
