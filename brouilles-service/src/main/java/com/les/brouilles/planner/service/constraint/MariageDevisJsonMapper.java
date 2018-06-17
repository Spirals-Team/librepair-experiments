package com.les.brouilles.planner.service.constraint;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.devis.Devis.TypeDevis;
import com.les.brouilles.planner.service.devis.json.BaseJsonDevis;
import com.les.brouilles.planner.service.devis.json.JsonDevisMariage;

@Component
public class MariageDevisJsonMapper implements DevisJsonMapper {

	@Override
	public TypeDevis typeDevis() {
		return TypeDevis.MARIAGE;
	}

	@Override
	public Class<? extends BaseJsonDevis> mappedClass() {
		return JsonDevisMariage.class;
	}

	@Override
	public Set<String> proprietesValides() {
		return new HashSet<>();
	}

}
