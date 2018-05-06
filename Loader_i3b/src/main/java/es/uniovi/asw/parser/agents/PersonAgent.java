package es.uniovi.asw.parser.agents;

/**
 * 
 * @author Jorge  Class used to represent PersonAgents and parse their data.
 *
 */
public class PersonAgent extends AbstractAgent {

	public PersonAgent(String name, String location, String email, String identifier, String kind) {
		super(name, location, email, identifier, kind);
		setKind("Person");

	}

	public PersonAgent(Object[] data) {
		super((String)data[0],(String)data[1],(String)data[2],(String) data[3], String.valueOf(data[4]));	
		setKind("Person");

	}

	public String toString() {
		return "Person Agent [Name=" + this.name
				+ ", location=" + this.location
				+ ", email=" + this.email
				+ ", identifier=" + this.id
				+"]";
	}
}
