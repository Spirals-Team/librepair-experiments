package uo.asw.dbManagement.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Filter {

	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * La respuesta que va a tener el filtro con cada incidencia:
	 * -ACCEPT_ALL --> Acepta todas las incidencias (no filtra nada)
	 * -ACCEPT --> Si se cumple la operación, deja pasar la incidencia, si no no
	 * -MARK_AS_DANGEROUS --> Si se cumple la operación, se marca la incidencia como peligrosa
	 */
	private String filterResponse = "acceptAll"; // por defecto acepta todas
	
	/**
	 * Decide si la operacion se va a aplicar sobre tags o properties de la incidencia
	 */
	private String applyOn = "tag"; // por defecto se aplica sobre tags
	
	/**
	 * En caso de que se aplique sobre propiedades de las incidencias,
	 * indica el tipo Java que va a tener el String contenido en el "value" de la propiedad
	 */
	private String propertyType = "double"; //only if apply on == PROPERTY, por defecto double
	
	/**
	 * Tipo de operación a realizar: GREATER, LESS, EQUALS, NOT_EQUALS, CONTAINS, NOT_CONTAINS
	 * Si la operación se cumple y FilterResponse==ACCEPT, se deja pasar la incidencia
	 * Si la operación se cumple y FilterResponse==ACCEPT, se marca como peligrosa la incidencia
	 */
	private String filterOperation = "contains";// por defecto si se cambiase FilterResponse a ACCEPT,
	// solo se aceptarian las incidencias que contengan el tag indicado
	
	/**
	 * Guarda el valor del tag con el que se va a comparar
	 */
	private String tag;
	
	/**
	 * Guarda el nombre de la propiedad con la que se va a comparar
	 */
	private String propertyName;
	
	/**
	 * Guarda el valor de la propiedad con la que se va a comparar
	 */
	private String propertyValue;
	
	
	public Filter() {}

	public Incidence applyFilter(Incidence incidence) {
		if("accept".equals(filterResponse))
			return acceptResponse(incidence);
		
		else if("markAsDangerous".equals(filterResponse))
			return markAsDangerousResponse(incidence);
		
		else // ACCEPT_ALL
			return incidence;
	}
	
	/**
	 * Si cumple la operacion, se marca como peligrosa y se retorna,
	 * si no, se retorna sin mas
	 */
	private Incidence markAsDangerousResponse(Incidence incidence) {
		if(satisfiesOperation(incidence)) {
			incidence.setDangerous(true);
			return incidence;
		}
		else return incidence;
	}

	/**
	 * Si cumple la operacion, se retorna la incidencia
	 * si no, se retorna null
	 */
	private Incidence acceptResponse(Incidence incidence) {
		if(satisfiesOperation(incidence)) {
			return incidence;
		}
		else return null;
	}

	private boolean satisfiesOperation(Incidence incidence) {
		if("tag".equals(applyOn))
			return satisfiesTagOperation(incidence);
		else // Property
			return satisfiesPropertyOperation(incidence);
	}

	private boolean satisfiesTagOperation(Incidence incidence) {
		if("contains".equals(filterOperation))
			return incidence.getTags().contains(tag);
		
		else // NOT_CONTAINS 
			return !incidence.getTags().contains(tag); 
	}

	private boolean satisfiesPropertyOperation(Incidence incidence) {
		String incidencePropertyValue = getPropertyValueByName(incidence, propertyName);
		if(incidencePropertyValue == null) return false;
		
		if("greater".equals(filterOperation))
			return satisfiesPropertyGreaterOperation(incidence);

		else if("less".equals(filterOperation))
			return satisfiesPropertyLessOperation(incidence);
		
		else if("equals".equals(filterOperation))
			return satisfiesPropertyEqualsOperation(incidence);
		
		else //NOT_EQUALS
			return !satisfiesPropertyEqualsOperation(incidence);
	}
	
	private boolean satisfiesPropertyGreaterOperation(Incidence incidence) {
		String incidencePropertyValue = getPropertyValueByName(incidence, propertyName);
		
		if(Double.parseDouble(incidencePropertyValue) > Double.parseDouble(propertyValue))
			return true;
		else return false;
	}
	
	private boolean satisfiesPropertyLessOperation(Incidence incidence) {
		String incidencePropertyValue = getPropertyValueByName(incidence, propertyName);
		
		if(Double.parseDouble(incidencePropertyValue) < Double.parseDouble(propertyValue))
			return true;
		else return false;
	}
	
	private boolean satisfiesPropertyEqualsOperation(Incidence incidence) {
		if("string".equals(propertyType))
			return satisfiesPropertyEqualsStringOperation(incidence);
		
		else // BOOLEAN
			return satisfiesPropertyEqualsBooleanOperation(incidence);
	}
	
	private boolean satisfiesPropertyEqualsStringOperation(Incidence incidence) {
		String incidencePropertyValue = getPropertyValueByName(incidence, propertyName);
		
		if(incidencePropertyValue.equalsIgnoreCase(propertyValue))
			return true;
		else return false;
	}

	private boolean satisfiesPropertyEqualsBooleanOperation(Incidence incidence) {
		String incidencePropertyValue = getPropertyValueByName(incidence, propertyName);
		
		if(Boolean.parseBoolean(incidencePropertyValue) == Boolean.parseBoolean(propertyValue))
			return true;
		else return false;
	}
	
	private String getPropertyValueByName(Incidence incidence, String propertyName) {
		Set<Property> properties = incidence.getProperties();
		for (Property property : properties) {
			if(property.getName().equals(propertyName))
				return property.getValue();
		}
		return null;
	}

	public long getId() {
		return id;
	}
	
	public Filter setId(long id) {
		this.id = id;
		return this;
	}

	public String getFilterResponse() {
		return filterResponse;
	}

	public Filter setFilterResponse(String filterResponse) {
		this.filterResponse = filterResponse;
		return this;
	}

	public String getApplyOn() {
		return applyOn;
	}

	public Filter setApplyOn(String applyOn) {
		this.applyOn = applyOn;
		return this;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public Filter setPropertyType(String propertyType) {
		this.propertyType = propertyType;
		return this;
	}

	public String getFilterOperation() {
		return filterOperation;
	}

	public Filter setFilterOperation(String filterOperation) {
		this.filterOperation = filterOperation;
		return this;
	}

	public String getTag() {
		return tag;
	}


	public Filter setTag(String tag) {
		this.tag = tag;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public Filter setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}


	public String getPropertyValue() {
		return propertyValue;
	}


	public Filter setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
		return this;
	}
	
}
