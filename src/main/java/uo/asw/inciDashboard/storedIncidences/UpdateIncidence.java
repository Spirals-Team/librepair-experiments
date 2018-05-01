package uo.asw.inciDashboard.storedIncidences;

import java.security.Principal;

import org.springframework.ui.Model;

public interface UpdateIncidence {

	public String updateIncidenceGet(Model model, Long idIncidence);
	
	public String updateIncidencePost(Model model, Principal principal, 
			Long idIncidence, String statusIncidence, String operatorComments);
	
}
