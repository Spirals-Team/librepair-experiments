package io.xrates.backend.datamodel.dao;


import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import io.xrates.backend.constants.RateProviderDetails;
import io.xrates.backend.datamodel.beans.Provider;

@Repository
@Transactional
public class ProviderDaoImpl extends AbstractDao<Provider> implements ProviderDao {

	@Override
	public Provider findProvider(RateProviderDetails rateProvider) {
		Provider provider = get((Long) rateProvider.getValue());
		
		if (provider == null) {
			provider = new Provider();
			provider.setProviderId(rateProvider.getValue());
			provider.setProviderName(rateProvider.getProviderName());
			
			create(provider);
		}
		return provider;
	}
}
