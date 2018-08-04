package ru.job4j.model.repository.queries;

/**
 * @author Yury Matskevich
 */
public interface SqlSpecification extends Specification {
	String toSqlQuery();
}
