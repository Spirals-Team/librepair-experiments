package uo.asw.inciDashboard.filter;

import org.springframework.ui.Model;

public interface SetFilter {
	
	public String setFilterGet(Model model);
	
	public String setFilterPost(String filterResponse, //TODO . quizas hay que poner los @
			String applyOn, 
			String propertyType,
			String filterOperation,
			String tag,
			String propertyName,
			String propertyValue);
	
}
