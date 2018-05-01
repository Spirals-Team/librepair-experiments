package uo.asw.inciDashboard.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.DBManagementFacade;
import uo.asw.dbManagement.model.Incidence;
import uo.asw.dbManagement.model.Agent;
import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.model.Property;
import uo.asw.util.exception.BusinessException;
import uo.asw.util.exception.Check;

@Service
public class RIncidenceP {
	
	@Autowired
	private DBManagementFacade dbManagement;
	
	@Autowired
	private ReceiveIncidence receiveIncidence;
	
	/**
	 * Se encarga de parsear el String que le llega, convirtiéndolo a un objeto JSON, 
	 * y transformando dicho JSON en un objeto Incidence.
	 * 
	 * @param JSONString
	 * @return
	 * @throws BusinessException 
	 */
	public Incidence jsonStringToIncidence(String JSONString) throws BusinessException {
		JSONObject json = new JSONObject(JSONString);
		List<String> names = Arrays.asList(JSONObject.getNames(json));
				
		String identifier = getString(names, json, "identifier");
		String login = getString(names, json, "login");
		String password = getString(names, json, "password");
		String kind = getString(names, json, "kind");
		String name = getString(names, json, "name");
		String description = getString(names, json, "description");
		String location = getString(names, json, "location");
		
		String[] tagsArray = getStringArray(names, json, "tags");
		Set<String> tags = tagsArray != null ? 
				new HashSet<String>(Arrays.asList(tagsArray)) : 
					new HashSet<String>();
		
		Set<Property> properties =  getSetProperties(names, json) != null ? 
				 getSetProperties(names, json) : 
					new HashSet<Property>();
		
		String status = getString(names, json, "status");
		String operatorIdentifier = getString(names, json, "operatorIdentifier");
		String expiration = getString(names, json, "expiration");
		
		Agent agent = dbManagement.getAgent(login,password,kind);
		agent.setIncidences(new HashSet<Incidence>());
		
		Operator operator = dbManagement.getOperator(operatorIdentifier);
		
		Check.isNotNull(identifier, "Every incidence must have an identifier");
		Check.isNotNull(agent, "Every incidence must have an existing agent");
		
		Incidence incidence = new Incidence(identifier);
		incidence
				.setAgent(agent)
				.setOperator(operator)
				.setName(name)
				.setDescription(description)
				.setLocation(location)
				.setTags(tags)
				.setProperties(properties)
				.setStatus(status)
				.setExpiration(expiration);
				
		// Mandamos la inci!!!!x
		receiveIncidence.receiveIncidence(incidence);
		
		return incidence;
	}
	
	public String getString(List<String> names, JSONObject json, String key) {
		if(!names.contains(key))
			return null;
		return json.getString(key);
	}
	
	public String[] getStringArray(List<String> names, JSONObject json, String key) {
		if(!names.contains(key))
			return null;
		
		JSONArray jsonArray = json.getJSONArray(key);
		String[] strings = new String[jsonArray.length()];
		
		for (int i = 0; i < jsonArray.length(); i++)
			strings[i]= jsonArray.getString(i);
		
		return strings;
	}
	
	public Set<Property> getSetProperties(List<String> names, JSONObject json) {
		if(!names.contains("properties"))
			return null;
		
		JSONArray jsonArray = json.getJSONArray("properties");
		Set<Property> properties = new HashSet<>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonProperty = jsonArray.getJSONObject(i);
			
			String propertyName = JSONObject.getNames(jsonProperty)[0];
			String propertyValue = jsonProperty.getString(propertyName);
			
			Property property = new Property(propertyName, propertyValue);
			properties.add(property);
		}	
		
		return properties;
	}
	
}
