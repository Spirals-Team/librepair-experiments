package backend.repository;

import backend.entity.AppUser;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
public interface UserRepository extends MongoRepository<AppUser,String> {
    AppUser findByUsername(@Param("username") String username);
    void deleteByUsername(@Param("username") String username);
}