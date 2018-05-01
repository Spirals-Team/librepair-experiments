package uo.asw.dbManagement.repositories;

import org.springframework.data.repository.CrudRepository;

import uo.asw.dbManagement.model.Operator;

public interface OperatorsRepository extends CrudRepository<Operator, Long>{
	Operator findByIdentifier(String identifier);
}
