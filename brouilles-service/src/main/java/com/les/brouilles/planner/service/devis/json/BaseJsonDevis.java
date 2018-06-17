package com.les.brouilles.planner.service.devis.json;

import java.util.Map;

import com.les.brouilles.planner.service.devis.json.propriete.ProprietePrix;

public abstract class BaseJsonDevis {

	public abstract Map<String, ProprietePrix> getProprietes();
}
