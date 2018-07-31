package tech.spring.structure.auth.model.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import tech.spring.structure.auth.model.User;
import tech.spring.structure.model.repo.StructureRepo;

@Repository
public interface UserRepo extends StructureRepo<User> {

    public Optional<User> findByUsername(String username);

    public boolean existsByUsername(String username);

}
