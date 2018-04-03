package uo.asw.inciManager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import uo.asw.dbManagement.model.Propiedad;
@EnableMongoRepositories
public interface PropiedadRepository extends MongoRepository<Propiedad, ObjectId>{

}
