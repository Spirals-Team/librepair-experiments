package br.edu.fapce.nexti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import br.edu.fapce.nexti.security.model.LoginUser;
import br.edu.fapce.nexti.security.model.LoginUserService;
import br.edu.fapce.nexti.security.model.UserRole;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	@Autowired
	LoginUserService loginUserService;

//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			createDefaultFinalUser();

		};
	}
	
	private void createDefaultFinalUser() {
		LoginUser loginUser = loginUserService.findByEmail("defaultfinaluser@gmail.com");
		if (loginUser == null) {
			LoginUser loginUser1 = LoginUser.builder().email("defaultfinaluser@gmail.com").password("123")
					.userRole(UserRole.ROLE_USUARIO).build();
			loginUserService.save(loginUser1);
		}
	}
	
}
