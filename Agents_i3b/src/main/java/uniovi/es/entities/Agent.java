package uniovi.es.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "agentscollec")
public class Agent {

    @Id
    private String id;
    
	private String password;
    
    private String name;
    
    private String email; 
    
    private String location; 
    
    private String kind; 
    
    private String kindCode; 

    private Agent() {}

    public Agent(String id,String passw ,String name,String email, String location, String kind,String kindCode) {
    	this.id=id;
        this.password=passw;
		this.name = name;
        this.email = email;
        this.location=location;
        this.kind=kind;
        this.kindCode=kindCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public String getKindCode() {
        return kindCode;
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
		Agent other = (Agent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserInfo [ID=" + id + ", password=" + password + ", location=" + location + ", kind=" + kind
				+ ", kindCode=" + kindCode + ", name=" + name+ ", email=" + email + "]";
	}

}