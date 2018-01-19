package backend.repository;

import backend.entity.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<AppUser,String> {

}
