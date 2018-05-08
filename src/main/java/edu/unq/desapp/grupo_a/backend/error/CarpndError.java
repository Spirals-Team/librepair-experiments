package edu.unq.desapp.grupo_a.backend.error;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Error representado con un codigo y una descripcion.
 * 
 * @author Charlie Cardozo
 *
 */

@XmlRootElement(name="error")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarpndError implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String code;

	@XmlAttribute
	private String description;

	@XmlAttribute
	private String field;

	public CarpndError() {
	}
	
	
	public CarpndError(String code, String description, String field) {
		super();
		this.code = code;
		this.description = description;
		this.field = field;
	}


	public CarpndError(String code,String description) {
		this.code = code;
		this.description = description;
	}
	
	public CarpndError(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	@Override
	public String toString() {
		return "Code " + code + " description "  + description;
	}
		

}
