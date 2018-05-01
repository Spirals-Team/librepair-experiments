package uo.asw.dbManagement;

import java.util.List;

import uo.asw.dbManagement.model.Agent;
import uo.asw.dbManagement.model.Category;
import uo.asw.dbManagement.model.Filter;
import uo.asw.dbManagement.model.Incidence;
import uo.asw.dbManagement.model.Operator;

public interface DBManagementFacade {
	
	public void updateIncidence(Incidence incidence);
	
	public List<Incidence> getOperatorIncidences(String identifier); //TODO - Cuando este implementado y funcione, hacer que devuelva Page<Incidence> para meter paginacion
	
	public List<Incidence> getIncidencesOfCategory(String category,String operator_identifier);
	
	public Incidence getIncidence(Long idIncidence);
	
	public Filter getFilter();

	public void updateFilter(Filter Filter);	
	
	//¿?Añadir a la documentación los de abajo¿?
	
	public Agent getAgent(String login, String password, String kind);
	
	public Operator getOperator(String identifier);//TODO - añadir a la documentacion
	
	public List<Category> findAllCategories();
	
	public Category findCategoryById(Long id);
		
}
