package edu.unq.desapp.grupo_a.backend.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.unq.desapp.grupo_a.backend.model.exceptions.UserInitException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.sun.istack.NotNull;

@Entity
@Table(name = "users")
public class User extends PersistenceEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private int idUser;

	private String cuil;
	
	private String name;
	
	private Address address;

	private String email;
	
	private Integer reputation;
	
	private Collection<Vehicle> vehicles;
	
	private CreditAccount creditAccount;
	
	public User(String cuil, String name, Address address, String email) throws UserInitException{
		try {
			check(cuil, name, address, email);
		} catch (UserInitException e) {
			throw e;
		}
		this.cuil = cuil;
		this.name = name;
		this.address = address;
		this.email = email;
		this.reputation = 0;
		this.vehicles = new ArrayList<>();
		this.creditAccount = new CreditAccount();
	}

	@Column(name = "cuil", nullable = false)
	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	@Column(name = "name", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
	@JoinColumn(name = "address_id",nullable=false)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(name = "user_email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "user_reputation", nullable = false)
	public Integer getReputation() {
		return reputation;
	}

	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public CreditAccount getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(CreditAccount creditAccount) {
		this.creditAccount = creditAccount;
	}

	@OneToMany(mappedBy="user", orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
    }

    public void addCredit(Double amount) {
        this.creditAccount.addAmount(amount);
    }

    public void loseCredit(Double amount) {
        this.creditAccount.loseAmount(amount);
    }

    private static void check(String cuil, String name, Address address,
                              String email) throws UserInitException {
        if (name == null || name.trim().isEmpty() ||
                cuil == null || cuil.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            throw new UserInitException();
        } else {
            Address.check(address);
        }
    }
}
