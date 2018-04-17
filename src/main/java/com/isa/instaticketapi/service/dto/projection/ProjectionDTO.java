
package com.isa.instaticketapi.service.dto.projection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProjectionDTO {

	private Long id;

	@NotNull
	@Size(min = 5, max = 5)
	private String startTime;

	@NotNull
	@Size(min = 5, max = 5)
	private String endTime;

	private String eventName;

	private String hallName;

	private String date;

	private List<SeatDTO> seatDTO;

	private int regularPrice;

	private int vipPrice;

	private int balconyPrice;

	private int salePercentage;

	public ProjectionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectionDTO(Long id, String startTime, String endTime, String eventName, String hallName, String date) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventName = eventName;
		this.hallName = hallName;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public List<SeatDTO> getSeatDTO() {
		return seatDTO;
	}

	public void setSeatDTO(List<SeatDTO> seatDTO) {
		this.seatDTO = seatDTO;
	}

	public int getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(int regularPrice) {
		this.regularPrice = regularPrice;
	}

	public int getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(int vipPrice) {
		this.vipPrice = vipPrice;
	}

	public int getBalconyPrice() {
		return balconyPrice;
	}

	public void setBalconyPrice(int balconyPrice) {
		this.balconyPrice = balconyPrice;
	}

	public int getSalePercentage() {
		return salePercentage;
	}

	public void setSalePercentage(int salePercentage) {
		this.salePercentage = salePercentage;
	}
}
