package ru.job4j.crud.pojo;

import java.util.Objects;

/**
 * A pojo object which represents
 * a user with specified attributes
 * @author Yury Matskevich
 */
public class User {
	private static int count = 0; //an unique counter for users
	private int id;
	private String name;
	private String login;
	private String email;
	private long createDate;
	private String password;
	private Integer role;
	private Integer cityId;
	private Integer stateId;

	/**
	 * Creates a new user without an id.
	 * An id will be null
	 * @param name a user's name
	 * @param login a user's login
	 * @param email a user's email
	 * @param createDate a date when user was created
	 */
	public User(String name, String login, String email, long createDate, String password, Integer role, int cityId) {
		this.id = ++count;
		this.name = name;
		this.login = login;
		this.email = email;
		this.createDate = createDate;
		this.password = password;
		this.role = role;
		this.cityId = cityId;
	}

	/**
	 * Creates a new user without an id and a date of creating
	 * @param id a user's id
	 * @param name a user's name
	 * @param login a user's login
	 * @param email a user's email
	 */
	public User(int id, String name, String login, String email, String password, Integer role, int cityId) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
		this.cityId = cityId;
	}

	/**
	 * Creates a new user with all the comleted fields
	 * @param id a user's id
	 * @param name a user's name
	 * @param login a user's login
	 * @param email a user's email
	 * @param createDate a date when user was created
	 */
	public User(int id, String name, String login, String email, long createDate, String password, Integer role, int cityId) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.createDate = createDate;
		this.password = password;
		this.role = role;
		this.cityId = cityId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return createDate == user.createDate
				&& Objects.equals(name, user.name)
				&& Objects.equals(login, user.login)
				&& Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, login, email, createDate);
	}
}
