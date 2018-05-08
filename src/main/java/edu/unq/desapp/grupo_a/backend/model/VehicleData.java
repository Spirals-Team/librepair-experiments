package edu.unq.desapp.grupo_a.backend.model;

import java.util.List;

import edu.unq.desapp.grupo_a.backend.model.exceptions.VehicleDataException;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.sun.istack.NotNull;

public class VehicleData {
	
	private VehicleType vehicleType;
	
	private Integer passengerCapability;
	
	private City city;

	private String vehicleDescription;
	
	private Phone contactPhone;

	private List<Photo> photos;

	public VehicleData (VehicleType vehicleType, int passengerCapability, City city,
			String vehicleDescription, Phone contactPhone, List<Photo> photos) {

		this.vehicleType = vehicleType;
		this.passengerCapability = passengerCapability;
		this.city = city;
		this.vehicleDescription = vehicleDescription;
		this.contactPhone = contactPhone;
		this.photos = photos;
	}

	public static void check(VehicleData vehicleData) throws VehicleDataException {

		if (null == vehicleData) {
			throw new VehicleDataException();
		} else {
			try {
				VehicleType.check(vehicleData.getVehicleType());
				checkVehicleCapability(vehicleData.getPassengerCapability());
				City.check(vehicleData.getCity());
				checkVehicleDescription(vehicleData.getVehicleDescription());
				Phone.check(vehicleData.getContactPhone());
				Photo.check(vehicleData.getPhotos());
			} catch (VehicleDataException e) {
				throw e;
			}
		}
	}

	private static void checkVehicleCapability(int vehicleCapability) throws VehicleDataException {
		if (vehicleCapability <= 0) {
			throw new VehicleDataException();
		}
	}

	private static void checkVehicleDescription(String vehicleDescription) throws VehicleDataException {
		if (vehicleDescription == null || vehicleDescription.trim().isEmpty()) {
			throw new VehicleDataException();
		}
	}

	public Integer getPassengerCapability() {
		return passengerCapability;
	}

	public void setPassengerCapability(Integer passengerCapability) {
		this.passengerCapability = passengerCapability;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getVehicleDescription() {
		return vehicleDescription;
	}

	public void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}

	public Phone getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(Phone contactPhone) {
		this.contactPhone = contactPhone;
	}

    @OneToMany(targetEntity=Photo.class, mappedBy="vehicleData", fetch=FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Photo> getPhotos() {
        return photos;
    }

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

    @NotNull
    @Column(name = "vehicle_type", length = 10,nullable=false)
    @Enumerated(EnumType.STRING)
	public VehicleType getVehicleType() {
		return this.vehicleType;
	}
}
