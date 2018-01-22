package py.org.fundacionparaguaya.pspserver.network.dtos;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;

public class UserApplicationDTO {

	private Long id;

	private UserDTO user;

	private ApplicationDTO application;

	private OrganizationDTO organization;

	public UserApplicationDTO() {}
	

	private UserApplicationDTO(Long id, UserDTO user, ApplicationDTO application, OrganizationDTO organization) {
		this.id = id;
		this.user = user;
		this.application = application;
		this.organization = organization;
	}

	public static class Builder {
		private Long userApplicationId;
		private UserDTO user;
		private ApplicationDTO application;
		private OrganizationDTO organization;
		
		public Builder userApplicationId(Long userApplicationId) {
			this.userApplicationId = userApplicationId;
			return this;
		}
		
		public Builder user(UserDTO user) {
			this.user = user;
			return this;
		}
		
		public Builder application(ApplicationDTO application) {
			this.application = application;
			return this;
		}
		
		public Builder organization(OrganizationDTO organization) {
			this.organization = organization;
			return this;
		}
		public UserApplicationDTO build() {
			return new UserApplicationDTO(userApplicationId, user, application, organization);
		}
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ApplicationDTO getApplication() {
		return application;
	}

	public void setApplication(ApplicationDTO application) {
		this.application = application;
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("user", user)
				.add("application", application)
				.add("organization", organization)
				.toString();
	}
	
}
