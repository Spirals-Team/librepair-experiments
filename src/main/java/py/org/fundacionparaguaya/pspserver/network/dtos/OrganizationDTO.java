package py.org.fundacionparaguaya.pspserver.network.dtos;

import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;

import java.io.Serializable;

public class OrganizationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private String name;

	private String code;

	private String description;

	private boolean isActive;

	private CountryDTO country;

	private String information;

	private ApplicationDTO application;
	
	private DashboardDTO dashboard;
	
	public OrganizationDTO() {}
	

	private OrganizationDTO(Long organizationId, String name, String code, String description, boolean isActive,
			CountryDTO country, String information, ApplicationDTO application, DashboardDTO dashboard) {
		this.id = organizationId;
		this.name = name;
		this.code = code;
		this.description = description;
		this.isActive = isActive;
		this.country = country;
		this.information = information;
		this.application = application;
		this.dashboard = dashboard;
	}

	public static class Builder {
		private Long id;
		private String name;
		private String code;
		private String description;
		private boolean isActive;
		private CountryDTO country;
		private String information;
		private ApplicationDTO application;
		private DashboardDTO dashboard;
		
		public Builder id(Long organizationId) {
			this.id = organizationId;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder code(String code) {
			this.code = code;
			return this;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder isActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public Builder country(CountryDTO country) {
			this.country = country;
			return this;
		}
		
		public Builder information(String information) {
			this.information = information;
			return this;
		}
		
		public Builder application(ApplicationDTO application) {
			this.application = application;
			return this;
		}
		
		public Builder dashboard(DashboardDTO dashboard) {
            this.dashboard = dashboard;
            return this;
        }
		
		public OrganizationDTO build() {
			return new OrganizationDTO(id, name, code, description, isActive, country, information, application, dashboard);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long organizationId) {
		this.id = organizationId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public CountryDTO getCountry() {
		return country;
	}


	public void setCountry(CountryDTO country) {
		this.country = country;
	}


	public String getInformation() {
		return information;
	}


	public void setInformation(String information) {
		this.information = information;
	}


	public ApplicationDTO getApplication() {
		return application;
	}


	public void setApplication(ApplicationDTO application) {
		this.application = application;
	}
	
	public DashboardDTO getDashboard() {
        return dashboard;
    }
	
    public void setDashboard(DashboardDTO dashboard) {
        this.dashboard = dashboard;
    }
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("name", name)
				.add("code", code)
				.add("description", description)
				.add("isActive", isActive)
				.add("country", country.toString())
				.add("information", information)
				.add("application", application.toString())
				.add("dashboard", dashboard.toString())
				.toString();
	}
	
}
