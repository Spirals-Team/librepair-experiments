package ru.job4j.model.pojo;

import java.util.List;
import java.util.Objects;

/**
 * @author Yury Matskevich
 */
public class User {
	private int id;
	private String login;
	private String password;
	private String name;
	private Address address;
	private Roles role;
	private List<MusicType> musicsType;

	public User(int id, String login, String password, String name, Address address, Roles role, List<MusicType> musicsType) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.address = address;
		this.role = role;
		this.musicsType = musicsType;
	}

	public User(String login, String password, String name, Address address, Roles role, List<MusicType> musicsType) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.address = address;
		this.role = role;
		this.musicsType = musicsType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public List<MusicType> getMusicsType() {
		return musicsType;
	}

	public void setMusicsType(List<MusicType> musicsType) {
		this.musicsType = musicsType;
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
		return Objects.equals(login, user.login)
				&& Objects.equals(password, user.password)
				&& Objects.equals(name, user.name)
				&& Objects.equals(address, user.address)
				&& role == user.role
				&& Objects.equals(musicsType, user.musicsType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(login, password, name, address, role, musicsType);
	}
}
