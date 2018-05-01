package uo.asw.dbManagement.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.repositories.OperatorsRepository;

@Service
public class OperatorsService {
	
	@Autowired
	private OperatorsRepository operatorsRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@PostConstruct
	public void init() {
	}
	
	public List<Operator> getOperators() {
		List<Operator> operators = new ArrayList<Operator>();
		operatorsRepository.findAll().forEach(operators::add);
		return operators;
	}
	
	public Operator getOperator(Long id) {
		return operatorsRepository.findOne(id);
	}
	
	public Operator findByIdentifier(String identifier) {
		return operatorsRepository.findByIdentifier(identifier);
	}
	
	public void addOperator(Operator operator) {
		operator.setPassword(bCryptPasswordEncoder.encode(operator.getPassword()));
		operatorsRepository.save(operator);
	}
	
	public Operator getUserByIdentifier(String identifier) {
		return operatorsRepository.findByIdentifier(identifier);
	}
	
	public void deleteUser(Long id) {
		operatorsRepository.delete(id);
	}
	
	public boolean passwordsIguales(String password,String passwordEncriptada) {
		
		return bCryptPasswordEncoder.matches(password, passwordEncriptada);
	}
	
	/*public List<String> findAllCategorys(){
		
		List<String> categorys=new ArrayList<String>();
		
		for (Incidence incidence : incidencesRepository.findAll()) {
			String[] categorysForOperator=incidencesRepository.findCategorysForIncidence(incidence);
			for (String category : categorysForOperator) {
				if(!categorys.contains(category)) {
					categorys.add(category);
				}
			}
		}
		return categorys;
		
	}*/
	
	/*public List<Incidence> getIncidencesOfCategory(String category){
		
		List<Incidence> incidencesForCategory=new ArrayList<Incidence>();
		
		for (Incidence incidence : incidencesRepository.findAll()) {
			if(conatinsInArray(incidence.getTags(), category)) {
				incidencesForCategory.add(incidence);
			}
		}
		return incidencesForCategory;
		
	}
	
	public boolean conatinsInArray(String[] array, String element) {
		
		for (String e : array) {
			
			if(e.equals(element)) {
				return true;
			}
		}
		return true;
	}*/
	
	/*public Page<Operator> searchUsersByidentifierAndName(Pageable pageable, String searchText){
		
		Page<Operator> operators = new PageImpl<Operator>(new LinkedList<Operator>());
		searchText = "%"+searchText+"%";
	
		operators = operatorsRepository.searchByIdentifierAndName(pageable, searchText);

		return operators;
	}*/

}
