package groovy.m2dl.ivvq.sortircesoir.controllers;

import m2dl.ivvq.sortircesoir.controller.InscriptionController;
import m2dl.ivvq.sortircesoir.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import spock.lang.Specification;

class InscriptionControllerTest extends Specification {
    UserRepository userRepository;
    InscriptionController inscriptionController;

    String mail = "test@test.fr"
    String login = "test"
    String password = "azerty"


    def setup() {
        userRepository = Mock()
        inscriptionController = new InscriptionController(userRepository);
    }

    def "create an user works"() {
        when:
        ResponseEntity<Boolean> res = inscriptionController.signUp(mail, login,password, password)

        then:
        assert res.statusCode == HttpStatus.CREATED
        assert res.getBody() == true
    }

    def "trying to create an user with invalid passwordConfirmation fails"() {
        when:
        ResponseEntity<Boolean> res = inscriptionController.signUp(mail, login, password, "wrong")

        then:
        assert res.statusCode == HttpStatus.BAD_REQUEST
        assert res.getBody() == false
    }
}
