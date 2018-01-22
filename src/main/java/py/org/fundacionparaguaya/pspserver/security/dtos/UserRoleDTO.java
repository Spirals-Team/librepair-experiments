package py.org.fundacionparaguaya.pspserver.security.dtos;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.security.constants.Role;

public class UserRoleDTO {

	private Long userRoleId;

	private UserDTO user;

	private Role role;
	
	public UserRoleDTO(){}
	
	private UserRoleDTO(Long userRoleId, UserDTO user, Role role) {
		this.userRoleId = userRoleId;
		this.user = user;
		this.role = role;
	}
	
	public static class Builder {
		private Long userRoleId;
		private UserDTO user;
		private Role role;
		
		public Builder userRoleId(Long userRoleId) {
			this.userRoleId = userRoleId;
			return this;
		}
		
		public Builder user(UserDTO user) {
			this.user = user;
			return this;
		}
		
		public Builder role(Role role) {
			this.role = role;
			return this;
		}
		
		public UserRoleDTO build() {
			return new UserRoleDTO(userRoleId, user, role);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("userRoleId", userRoleId)
				.add("user", user)
				.add("role", role)
				.toString();
	}

}
