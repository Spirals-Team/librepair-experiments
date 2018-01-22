/**
 * 
 */
package py.org.fundacionparaguaya.pspserver.families.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author bsandoval
 *
 */
public class FamilyFilterDTO {

	private Long applicationId;
	private Long organizationId;
	private Long countryId;
	private Long cityId;
	private String name;
	private boolean isActive;
	
	public FamilyFilterDTO() {
	    super();
	}
	
	public FamilyFilterDTO(Long applicationId, Long organizationId) {
        super();
        this.applicationId = applicationId;
        this.organizationId = organizationId;
    }
	
	public FamilyFilterDTO(Long applicationId, Long organizationId, Long countryId, 
			Long cityId, String name, boolean isActive) {
        super();
        this.applicationId = applicationId;
        this.organizationId = organizationId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.name = name;
        this.isActive = isActive;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("applicationId", applicationId);
        builder.append("organizationId", organizationId);
        builder.append("countryId", countryId);
        builder.append("cityId", cityId);
        builder.append("name", name);
        builder.append("isActive", isActive);
        return builder.build();
    }
}
