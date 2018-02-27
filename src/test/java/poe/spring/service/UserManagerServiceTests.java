package poe.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import poe.spring.domain.User;
import poe.spring.exception.DuplicateLoginBusinessException;
import poe.spring.repository.UserRepository;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserManagerServiceTests {

	@Autowired
	UserManagerService userManagerService;

	@Autowired
	UserRepository userRepository;

	@Test
	public void checkUserCreation() throws DuplicateLoginBusinessException {

		// enregistre un nouvel utilisateur en BDD
		String login = "jean";
		User createdUser = userManagerService.signup(login, "secret");

		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getId()).isNotNull().isGreaterThan(0);

		// récupération de l'utilisateur en base de données
		long createdUserId = createdUser.getId();
		User user = userRepository.findOne(createdUserId);

		// vérifications
		assertThat(user).isNotNull();
		assertThat(user.getLogin()).isEqualTo(login);
	}

	@Test
	public void checkDuplicateUserCreation() throws DuplicateLoginBusinessException {

		// enregistre un nouvel utilisateur en BDD
		String login = UUID.randomUUID().toString();
		User createdUser = userManagerService.signup(login, "secret");

		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getId()).isNotNull().isGreaterThan(0);

		// récupération de l'utilisateur en base de données
		long createdUserId = createdUser.getId();
		User user = userRepository.findOne(createdUserId);

		// vérifications
		assertThat(user).isNotNull();
		assertThat(user.getLogin()).isEqualTo(login);

		// on vérifie qu'on n'a pas pu créer un user avec le même login
		User duplicateUser=null;
//		try {
//			duplicateUser = userManagerService.signup(login, "secret");
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		assertThat(duplicateUser).isNull();
		

		// on vérifie qu'on peut toujours créer d'autres users
//		assertThat(userManagerService.signup(login + "notTheSame", "secret")).isNotNull();

	}
}

// package poe.spring.service;
//
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
// import poe.spring.domain.User;
// import poe.spring.repository.UserRepository;
// import poe.spring.service.UserManagerService;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class UserManagerServiceTests {
//
// @Autowired
// UserManagerService userManagerService;
//
// @Autowired
// UserRepository userRepository;
//
// @Test
// public void checkUserCreation() {
//
// // ----------------------------------------------------------------
// // Start TI create User
// // ----------------------------------------------------------------
// // enregistre un nouvel utilisateur en BDD
// String login = "login";
// String password = "password";
// // login = "jean";
// // password = "secret";
//// User createdUser = userManagerService.signup("login", "password");
////
//// assertThat(createdUser).isNotNull();
//// assertThat(createdUser.getId()).isNotNull().isGreaterThan(0);
////
//// // récupération de l'utilisateur en base de données
//// Long createdUserId = createdUser.getId();
//// User user = userRepository.findOne(createdUserId);
////
//// // vérifications
//// assertThat(user).isNotNull();
//// assertThat(user.getLogin()).isEqualTo(login);
// // ----------------------------------------------------------------
// // End TI create User
// // ----------------------------------------------------------------
// // ----------------------------------------------------------------
// // Start TI create 10 Users
// // ----------------------------------------------------------------
// // End TI create 10 Users
// // ----------------------------------------------------------------
//
// }
//
// }
