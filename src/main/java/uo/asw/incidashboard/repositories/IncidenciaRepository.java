package uo.asw.incidashboard.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import uo.asw.dbManagement.model.Incidencia;
import uo.asw.dbManagement.model.Usuario;

@EnableMongoRepositories
public interface IncidenciaRepository extends MongoRepository<Incidencia, ObjectId> {

	Page<Incidencia> findByIdAgente(String idAgente,Pageable pageable);

	Page<Incidencia> findByOperario(Usuario operario, Pageable pageable);

	Incidencia findByNombreIncidencia(String nombreIncidencia);

	List<Incidencia> findByOperario(Usuario operario);
}
