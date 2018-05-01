package uo.asw.inciDashboard.storedIncidences;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.dbManagement.DBManagementFacade;
import uo.asw.dbManagement.model.Category;
import uo.asw.dbManagement.model.Incidence;

@Controller
public class StoredIncidencesController implements ShowOperatorIncidences, ShowIncidencesOfCategory, UpdateIncidence {

	@Autowired
	private DBManagementFacade dBManagement;

	@Override
	@RequestMapping("/incidences/operator")
	public String showOperatorIncidences(Model model,Principal principal) {
				
			String identifier=principal.getName();
			List<Incidence> operatorIncidences=dBManagement.getOperatorIncidences(identifier);
			
			model.addAttribute("operatorIncidences", operatorIncidences);
			model.addAttribute("updateHabilitado", false);
			model.addAttribute("listStatus", new ArrayList<String>());
			model.addAttribute("selectIncidence", new Incidence(-1,""));
			
		 	return "incidences/operator";
	}
	
	@RequestMapping("/incidences/categories/select_show")
	public String showIncidencesOfCategorySelect(Model model) {
		List<Category> categorys=dBManagement.findAllCategories();
		model.addAttribute("categorys", categorys);
		model.addAttribute("selectCategory", new Category(""));
		return "incidences/categories/select_show";
	}
	
	@Override
	@RequestMapping("/incidences/categories/show/{category_id}")
	public String showIncidencesOfCategoryGet(Model model, @PathVariable Long category_id) {

		Category c=dBManagement.findCategoryById(category_id);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String operator_identifier = auth.getName();
		
		List<Incidence> incidencesOfCategory=dBManagement.getIncidencesOfCategory(c.getName(),operator_identifier);
		
		model.addAttribute("selectCategory", c);
		model.addAttribute("incidencesOfCategory", incidencesOfCategory);
		
		return "incidences/categories/select_show :: tableIncidences";
	}

	@Override
	@RequestMapping("/incidences/update/changeStatus/{idIncidence}")
	public String updateIncidenceGet(Model model, @PathVariable Long idIncidence) {
		Incidence incidence=dBManagement.getIncidence(idIncidence);
		
		List<String> estados=new ArrayList<String>();
		estados.add("Abierta");
		estados.add("En_proceso");
		estados.add("Cerrada");
		estados.add("Anulada");
		
		model.addAttribute("updateHabilitado", true);
		model.addAttribute("listStatus", estados);
		model.addAttribute("selectIncidence", incidence);
		 
		return "incidences/operator :: tableUpdate";
	}
	
	@Override
	@RequestMapping("/incidences/update/saveStatus/{idIncidence}")
	public String updateIncidencePost(Model model, Principal principal, @PathVariable Long idIncidence, 
			@RequestParam String statusIncidence, @RequestParam(required=false, value="") String operatorComments) {
		
		//Actualizamos la incidencia con el nuevo estado y el nuevo comentario
		Incidence incidence = dBManagement.getIncidence(idIncidence);
		dBManagement.updateIncidence(incidence.setStatus(statusIncidence).setOperatorComments(operatorComments));
		
		String identifier=principal.getName();
		List<Incidence> operatorIncidences=dBManagement.getOperatorIncidences(identifier);
		
		model.addAttribute("operatorIncidences", operatorIncidences);
		model.addAttribute("updateHabilitado", false);
		model.addAttribute("listStatus", new ArrayList<String>());
		model.addAttribute("selectIncidence", new Incidence(-1,""));
		
		return "incidences/operator :: viewIncidences";
	}

}
