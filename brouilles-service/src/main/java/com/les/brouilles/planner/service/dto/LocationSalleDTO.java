package com.les.brouilles.planner.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LocationSalleDTO extends CommonEvenementDTO {

	public LocationSalleDTO() {

	}

	private String typeTables;

}