package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="operators")
public class Operator {
	@Id
	String operatorId;
	String password;
	
	public Operator(String operatorId, String password) {
		super();
		this.operatorId = operatorId;
		this.password = password;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
