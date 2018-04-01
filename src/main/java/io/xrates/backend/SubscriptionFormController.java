package io.xrates.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.xrates.frontend.templates.SubscriptionForm;


@Controller
public class SubscriptionFormController {
	Map<String,String> clientImpMap = new HashMap<String,String> ();
	ArrayList<String> curr = new ArrayList<>();
	
	@RequestMapping(value="/subscribe", method=RequestMethod.GET)
	public String greetingForm(Model model){
		model.addAttribute("greetingObj", new SubscriptionForm());
		
		return "subscribe";
	}
	
	@RequestMapping(value="/subscribe", method=RequestMethod.POST)
	public String greetingSubmit(@ModelAttribute SubscriptionForm greeting, Model model){
		model.addAttribute("greeting",greeting);
		return "result";
	}
	
	@RequestMapping(value="/getToCurrencyList", 
			params="fromCurrency", method=RequestMethod.POST)
	@ResponseBody
	public String[] getToCurrencyList(@RequestParam(value = "fromCurrency") String fromCurrency){
		String[] currencyList = SubscriptionForm.getRelevantCurrencyList(fromCurrency);
		return currencyList;
	}
	
	@RequestMapping(value="/getServices",
			params={"fromCurrency", "toCurrency"}, method=RequestMethod.POST)
	@ResponseBody 
	public String[] getServices(@RequestParam(value = "fromCurrency") String fromCurrency, @RequestParam(value = "toCurrency") String toCurrency){
		String[] serviceList = SubscriptionForm.getRelevantServices(fromCurrency, toCurrency);
		return serviceList;
	}
 	
//	@ModelAttribute("currency")
//	public ArrayList<String> populateCurrency(){
//		return (List<Currency>) Currency.getAvailableCurrencies();
//		
//		curr.add("SGD");
//		curr.add("INR");
//		System.out.println("Printing list");
//		for(String each:curr) System.out.println(each);
//		return curr;
//	}
	
}
