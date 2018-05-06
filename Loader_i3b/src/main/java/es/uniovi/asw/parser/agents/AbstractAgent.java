package es.uniovi.asw.parser.agents;

/**
 * 
 * @author Jorge. Class used to represent abstract Agents and parse their data.
 *
 */
public abstract class AbstractAgent {
	protected String name;
	protected String location;
	protected String email;	
	protected String id;
	protected String kindCode;
	protected String kind;
	protected String password;
	
	public AbstractAgent(String name, String location, String email, String identifier, String kind) {
		super();
		this.name = name;
		this.location = location;
		this.email = email;
		this.id = identifier;
		this.kindCode = String.valueOf(kind);
	}
	
	
	public String getKindCode() {
		return kindCode;
	}


	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String identifier) {
		this.id = identifier;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setPassword(String string) {
		password = string;
	}
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAgent other = (AbstractAgent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
