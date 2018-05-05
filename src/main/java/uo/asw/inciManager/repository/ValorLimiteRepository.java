package uo.asw.inciManager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import uo.asw.dbManagement.model.Propiedad;
import uo.asw.dbManagement.model.ValorLimite;

@EnableMongoRepositories
public interface ValorLimiteRepository extends MongoRepository<ValorLimite, ObjectId> {

	ValorLimite findByPropiedad(String propiedad);

}
