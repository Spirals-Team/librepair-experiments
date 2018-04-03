package uo.asw.inciManager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import uo.asw.dbManagement.model.Categoria;
@EnableMongoRepositories
public interface CategoriaRepository extends MongoRepository<Categoria, ObjectId>{

}
