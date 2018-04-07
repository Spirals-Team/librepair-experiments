package builders;

import model.User;

public class UserBuilder {
	private String CUIL= "aCUIL";
	private String name= "aName";
	private String surname= "aSurname";
	private String address= "anAdress";
	private String email= "aEmail@carpnd.com";
	private double credit=0;
	
	public static UserBuilder anUser(){
		return new UserBuilder();
	}
	
	public User build(){
		User user= new User(CUIL,name,surname,address,email);
		user.getAccount().addCredit(credit);
		return user;
	}

	public User buildUserDisabled(){
		User user= new User(CUIL,name,surname,address,email);
		user.getAccount().addCredit(credit);
		user.processScore(1);
		return user;
	}
	
	public UserBuilder withNameAndSurname(String name, String surname){
		this.name=name;
		this.surname=surname;
		return this;
	}
	
	public UserBuilder withEmail(String email){
		this.email=email;
		return this;
	}
	
	public UserBuilder withCredit(double credit){
		this.credit=credit;
		return this;
	}

	public UserBuilder withAddress(String address){
		this.address=address;
		return this;
	}
}
