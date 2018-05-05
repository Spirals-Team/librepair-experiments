package uo.asw.inciManager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import uo.asw.dbManagement.model.Incidencia;
@EnableMongoRepositories
public interface IncidenciaRepository extends MongoRepository<Incidencia, ObjectId>{

	Page<Incidencia> findByIdAgente(String idAgente, Pageable pageable);
}
