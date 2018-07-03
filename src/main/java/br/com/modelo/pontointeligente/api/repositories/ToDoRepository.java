package br.com.modelo.pontointeligente.api.repositories;

import br.com.modelo.pontointeligente.api.entities.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Long>{

}
