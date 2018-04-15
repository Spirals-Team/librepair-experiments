package com.isa.instaticketapi.service.dto.places;

import java.time.Instant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.isa.instaticketapi.domain.Place;

public class HallDTO {

	private Long id;

	@NotBlank
	@Size(min = 1, max = 50)
	private String name;

	private int col;

	private int row;

	public HallDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HallDTO(Long id, String name, int col, int row) {
		super();
		this.id = id;
		this.name = name;
		this.col = col;
		this.row = row;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}
