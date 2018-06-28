package inciManager.inciRepository;

import org.springframework.data.repository.CrudRepository;

import inciManager.entities.Incidencia;
import java.util.List;

public interface IncidenceRepository extends CrudRepository<Incidencia, Long> {

	List<Incidencia> findByIdentificadorAgente(String identificadoragente);

}
