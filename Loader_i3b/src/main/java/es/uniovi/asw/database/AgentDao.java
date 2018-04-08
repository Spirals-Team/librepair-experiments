package es.uniovi.asw.database;

import java.util.List;

import es.uniovi.asw.parser.agents.AbstractAgent;

/**
 * Interface for the methods that the DAO must accomplish
 * 
 * @author Sergio Santano Álvarez - UO244858
 *
 */
public interface AgentDao {

	boolean insert(AbstractAgent c);

	AbstractAgent findById(String ID);

	void remove(String ID);

	List<AbstractAgent> findAll();

	void cleanDatabase();
	//prueba
}
