package guru.bonacci.oogway.greeter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GreetingErrorHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView exception(Exception e) {
		e.printStackTrace();
		
		Greeting g = new Greeting();
		g.setKey("is invalid.");
		g.setAnswer("Poor darling..");
		
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("greeting", g);
		return mav;
	}
}
