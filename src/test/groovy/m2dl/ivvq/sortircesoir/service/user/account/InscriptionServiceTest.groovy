import m2dl.ivvq.sortircesoir.controller.InscriptionController
import m2dl.ivvq.sortircesoir.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class InscriptionServiceTest extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private UserRepository userRepository

    @Autowired
    private InscriptionController inscriptionController

    def baseURL = "http://localhost:8080"
    def signUpUrl = baseURL + "/signup"

    def login = "test"
    def email = "test@test.fr"
    def password = "azerty"

    def "un visiteur s'inscrit sur notre site"() {
        when:
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("mail", email)
        map.add("login", login)
        map.add("password", password)
        map.add("passwordConfirmation", password)
        def res = restTemplate.postForObject(signUpUrl, map, String.class)

        then:
        userRepository.findAll().size() 1
    }
}

//package groovy.m2dl.ivvq.sortircesoir.service.user.account
//
//import m2dl.ivvq.sortircesoir.SortirCeSoirApplication
//import m2dl.ivvq.sortircesoir.controller.InscriptionController
//import m2dl.ivvq.sortircesoir.domain.User
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.SpringApplication
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.HttpEntity
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.context.junit4.SpringRunner
//import org.springframework.test.context.web.WebAppConfiguration
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.web.client.RestTemplate
//import spock.lang.Specification
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//
//
////@WebMvcTest
////@RunWith(SpringRunner.class)
////@SpringBootTest
////@ContextConfiguration //NOTE: No loader defined
////@WebMvcTest(InscriptionController)
//
////@RunWith(SpringRunner.class)
////@SpringBootTest(classes = SortirCeSoirApplication.class)
//@AutoConfigureMockMvc
//class InscriptionServiceTest extends Specification {
//    RestTemplate restTemplate;
//    HttpEntity<User> request;
//
//    String baseURL = "http://localhost:8080";
//    String signUpUrl = baseURL + "/signup";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    def setup() {
//        SpringApplication.run(SortirCeSoirApplication.class);
//        restTemplate = new RestTemplate();
//        //def mockMvc = standaloneSetup(underTest).build()
//        // mockMvc = standaloneSetup(new InscriptionController()).build()
//    }
//
//    @Test
//    def "successfull sign up"() {
//        given:
//        def login = "test"
//        def email = "test@test.fr"
//        def password = "azerty"
//        def passwordConfirmation = "azerty"
//
////        Map<String, String> input = new HashMap<>();
////        input.put("login", "test");
////        input.put("email", "test@test.fr");
////        input.put("password", "azerty");
////        input.put("passwordConfirmation", "azerty");
//
//        when:
//        def response = mockMvc.perform(post(signUpUrl)
//                .param("login", login)
//                .param("email", email)
//                .param("password", password)
//                .param("passwordConfirmation", passwordConfirmation))
//                .andReturn()
//                .response;
//        //String result = restTemplate.postForObject(signUpUrl, input, String.class);
//
//        then:
//        assertEquals("Approved", response);
//    }
//}
