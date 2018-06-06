package io.xrates.backend.datamodel.dao;

import io.xrates.backend.datamodel.beans.Provider;
import io.xrates.backend.datamodel.beans.Service;

import java.util.List;

public interface ServiceDao extends Dao<Service> {
	
	public Service findService(String fromCurrency, String toCurrency, Provider provider);
	public List<Provider> findProviders(String fromCurrency, String toCurrency);
	public List<String> findToCurrencies(String fromCurrency);
}
