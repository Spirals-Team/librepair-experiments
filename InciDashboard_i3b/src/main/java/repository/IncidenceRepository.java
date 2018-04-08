package repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import model.Incidence;

public interface IncidenceRepository extends MongoRepository<Incidence, String>{
}
