package io.xrates.backend.datamodel.beans;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Provider")
public class Provider {
	@Id
	@Column(name = "provider_id")
	private long providerId;
	
	@Column(name = "provider_name", nullable = false)
	private String providerName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
	private Set<Service> service;

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Set<Service> getService() {
		return service;
	}
	
}
