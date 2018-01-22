package py.org.fundacionparaguaya.pspserver.network.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;



public interface ApplicationService {

	ApplicationDTO updateApplication(Long applicationId, ApplicationDTO application);

	ApplicationDTO addApplication(ApplicationDTO application);
	
	ApplicationDTO getApplicationById(Long applicationId);
	
	List<ApplicationDTO> getAllApplications();

	List<ApplicationDTO> getAllHubs();

	List<ApplicationDTO> getAllPartners();

	void deleteApplication(Long applicationId);

    ApplicationDTO getApplicationDashboard(Long applicationId, UserDetailsDTO details);
	
}
