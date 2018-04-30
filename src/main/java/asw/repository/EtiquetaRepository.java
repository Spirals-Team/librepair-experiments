package asw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import asw.entities.Etiqueta;

public interface EtiquetaRepository extends CrudRepository<Etiqueta, Long>{
	List<Etiqueta> findAll();
}
