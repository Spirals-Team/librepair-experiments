package com.les.brouilles.planner.service.devis.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.les.brouilles.planner.service.devis.json.propriete.ProprietePrix;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class JsonDevisLiberte extends BaseJsonDevis {

	@JsonProperty("proprietesLiberte")
	private final Map<String, ProprietePrix> proprietes = new HashMap<String, ProprietePrix>();
}
