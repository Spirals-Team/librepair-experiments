package uo.asw.dbManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Agent;
import uo.asw.dbManagement.model.Category;
import uo.asw.dbManagement.model.Filter;
import uo.asw.dbManagement.model.Incidence;
import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.repositories.AgentsRepository;
import uo.asw.dbManagement.repositories.CategoriesRepository;
import uo.asw.dbManagement.repositories.FilterRepository;
import uo.asw.dbManagement.repositories.IncidencesRepository;
import uo.asw.dbManagement.repositories.OperatorsRepository;

@Service
public class DBManagementFacadeImpl implements DBManagementFacade{

	@Autowired
	private OperatorsRepository operatorsRepository;
	
	@Autowired
	private FilterRepository filterRepository;
	
	@Autowired
	private IncidencesRepository incidencesRepository;
	
	@Autowired
	private AgentsRepository agentsRepository;
	
	@Autowired
	private CategoriesRepository categoriesRepository;
	
	@Override
	public void updateIncidence(Incidence incidence) {
		incidencesRepository.save(incidence);
	}
	
	@Override
	public List<Incidence> getOperatorIncidences(String idOperator) {
		List<Incidence> incidences=incidencesRepository.getOperatorIncidences(idOperator);
		if(incidences.size()>0) {
			incidences=incidencesRepository.orderIncidencesById(incidences);
		}
		return incidences;
	}
	
	@Override
	public List<Incidence> getIncidencesOfCategory(String category,String operator_identifier){

		List<Incidence> incidencesForCategory=new ArrayList<Incidence>();
		
		for (Incidence incidence : getOperatorIncidences(operator_identifier)) {
//			if(conatinsInArray(incidence.getTags(), category)) { //TODO - quitar
//				incidencesForCategory.add(incidence);
//			}
			if(incidence.getTags().contains(category)) {
				incidencesForCategory.add(incidence);
			}
		}
		return incidencesForCategory;
		
	}
	
	@Override
	public Incidence getIncidence(Long idIncidence) {
		return incidencesRepository.findOne(idIncidence);
	}
	
	/**
	 * Permite la solicitud del filtro guardado en la BD (sólo hay un filtro). 
	 * Si no hay ningún filtro en la BD, lo crea, lo guarda en BD y lo devuelve.
	 */
	@Override
	public Filter getFilter() {
		
		List<Filter> filters = new ArrayList<Filter>();
		filterRepository.findAll().forEach(filters::add);
		
		//Si la lista de filtros esta vacia, tenemos que crear un filtro
		if (filters.isEmpty()) {
			Filter filter = new Filter();
			filterRepository.save(filter); // lo guardamos
			
			// Lo volvemos a recuperar para que tenga el id actualizado
			filterRepository.findAll().forEach(filters::add);
		}
		
		return filters.get(0);		
	}
	
	@Override
	public void updateFilter(Filter filter) {		
		filterRepository.save(filter.setId(1));
	}

	@Override
	public Agent getAgent(String login, String password, String kind) {
		return agentsRepository.findByLoginPasswordAndKind(login, password, kind);
	}

	@Override
	public Operator getOperator(String identifier) {
		return operatorsRepository.findByIdentifier(identifier);
	}

	@Override
	public List<Category> findAllCategories() {
		
		List<Category> categories = new ArrayList<Category>();
		categoriesRepository.findAll().forEach(categories::add);
		
		return categories;
	}

	@Override
	public Category findCategoryById(Long id) {
		return categoriesRepository.findOne(id);
	}
	
	public boolean conatinsInArray(Set<String> array, String element) { // XXX ??????????
		
		for (String e : array) {
			
			if(e.equals(element)) {
				return true;
			}
		}
		return false;
	}
	

}
