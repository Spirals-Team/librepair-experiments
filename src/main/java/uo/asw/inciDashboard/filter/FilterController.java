package uo.asw.inciDashboard.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uo.asw.dbManagement.DBManagementFacade;
import uo.asw.dbManagement.model.Filter;

@Controller
public class FilterController implements SetFilter {

	@Autowired
	private DBManagementFacade dbManagement;
	
	@Override
	@RequestMapping("/incidences/filter")
	public String setFilterGet(Model model) {
		model.addAttribute("filter", dbManagement.getFilter());
		return "incidences/filter";
	}

	@Override
	@RequestMapping(value ="/incidences/filter", method = RequestMethod.POST)
	public String setFilterPost(@RequestParam String filterResponse,
			@RequestParam(required=false)	String applyOn, 
			@RequestParam(required=false) String propertyType,
			@RequestParam(required=false) String filterOperation,
			@RequestParam(required=false) String tag,
			@RequestParam(required=false) String propertyName,
			@RequestParam(required=false) String propertyValue) {
		
		Filter filter = new Filter();
		
		filter.setFilterResponse(filterResponse).
			setApplyOn(applyOn).
			setPropertyType(propertyType).
			setFilterOperation(filterOperation);		
		
		filter.setTag(tag).
			setPropertyName(propertyName).
			setPropertyValue(propertyValue);
		
		dbManagement.updateFilter(filter);
		
		return "redirect:/incidences/filter";
	}

}
