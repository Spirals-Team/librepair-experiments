package pl.edu.prz.kia.universityproject.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "uzytkownik")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_uzytkownika")
	private int id;

	@Column(name = "email")
	@Email(message = "*Podaj poprawny adres email")
	@NotEmpty(message = "*Podaj email")
	private String email;

	@Column(name = "haslo")
	@Length(min = 5, message = "*Haslo musi posiadac conajmniej 5 znakow")
	@NotEmpty(message = "*Podaj haslo")
	@Transient
	private String password;

	@Column(name = "imie")
	@NotEmpty(message = "*Podaj imie")
	private String name;

	@Column(name = "nazwisko")
	@NotEmpty(message = "*Podaj nazwisko")
	private String lastName;


	@Column(name = "active")
	private int active;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "uzytkownik_rola", joinColumns = @JoinColumn(name = "id_uzytkownika"), inverseJoinColumns = @JoinColumn(name = "id_roli"))
	private Set<Role> roles;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "uzytkownik_kierunek", joinColumns = @JoinColumn(name = "id_uzytkownika"), inverseJoinColumns = @JoinColumn(name = "id_kierunku"))
	private Set<Specialization> spectializations;


	@OneToMany(mappedBy = "user")
	private List<UserAnswer> UserAnswers;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Specialization> getSpectializations() {
		return spectializations;
	}

	public void setSpectializations(Set<Specialization> spectializations) {
		this.spectializations = spectializations;
	}

	public List<UserAnswer> getUserAnswers() {
		return UserAnswers;
	}

	public void setUserAnswers(List<UserAnswer> userAnswers) {
		UserAnswers = userAnswers;
	}
}
