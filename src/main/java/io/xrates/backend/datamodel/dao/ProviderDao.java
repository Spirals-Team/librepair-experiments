package io.xrates.backend.datamodel.dao;

import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.datamodel.beans.Provider;

public interface ProviderDao extends Dao<Provider>{
	
	public Provider findProvider(RateProviderDetails rateProvider);
	
}
