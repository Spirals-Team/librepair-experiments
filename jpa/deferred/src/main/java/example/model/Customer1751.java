package example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer1751 {

	@Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
	private String firstName;
	private String lastName;

	protected Customer1751() {}

	public Customer1751(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Customer1751[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}

}
