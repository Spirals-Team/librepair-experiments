package repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import model.Incidence;

public interface IncidenceRepository extends MongoRepository<Incidence, String>{
}
