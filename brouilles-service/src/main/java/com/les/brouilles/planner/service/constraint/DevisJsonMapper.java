package com.les.brouilles.planner.service.constraint;

import java.util.Set;

import com.les.brouilles.planner.persistence.model.devis.Devis.TypeDevis;
import com.les.brouilles.planner.service.devis.json.BaseJsonDevis;

public interface DevisJsonMapper {

	TypeDevis typeDevis();

	Class<? extends BaseJsonDevis> mappedClass();

	Set<String> proprietesValides();
}
