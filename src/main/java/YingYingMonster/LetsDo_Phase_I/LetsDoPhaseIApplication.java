package YingYingMonster.LetsDo_Phase_I;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
//@EnableAspectJAutoProxy//在springboot这里可有可无
@Controller
public class LetsDoPhaseIApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsDoPhaseIApplication.class, args);
	}
	
	@GetMapping("/home")
	public String visitHome(){
		return "home";
	}
}
