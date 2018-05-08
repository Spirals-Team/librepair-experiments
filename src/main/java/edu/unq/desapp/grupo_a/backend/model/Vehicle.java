package edu.unq.desapp.grupo_a.backend.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.unq.desapp.grupo_a.backend.model.exceptions.VehicleDataException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "vehicles")
public class Vehicle extends PersistenceEntity{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private VehicleData vehicleData;

	private Address withdrawAddress;

	private List<Address> returnAddresses;

	private Boolean availability;

	private Double rentPrice;

    private List<Photo> photos;

	private User user;

	public Vehicle (VehicleData vehicleData, Address withdrawAddress,
			List<Address> returnAddresses, Boolean availability, Double rentPrice,
			List<Photo> photos) throws VehicleDataException {
        try {
            VehicleData.check(vehicleData);
        } catch(VehicleDataException e) {
            throw e;
        }
		this.vehicleData = vehicleData;
		this.setWithdrawAddress(withdrawAddress);
		this.setReturnAddresses(returnAddresses);
		this.setAvailability(availability);
		this.setRentPrice(rentPrice);
        this.setPhotos(photos);
	}

	@OneToOne(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public VehicleData getVehicleData() {
		return this.vehicleData;
	}

	@OneToOne
	public Address getWithdrawAddress() {
		return withdrawAddress;
	}

	public void setWithdrawAddress(Address withdrawAddress) {
		this.withdrawAddress = withdrawAddress;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public List<Address> getReturnAddresses() {
		return returnAddresses;
	}

	public void setReturnAddresses(List<Address> returnAddresses) {
		this.returnAddresses = returnAddresses;
	}

	@Column(name = "availability")
	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	@Column(name = "rent_price")
	public Double getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Double rentPrice) {
		this.rentPrice = rentPrice;
	}

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

	@ManyToOne
	@JoinColumn
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
