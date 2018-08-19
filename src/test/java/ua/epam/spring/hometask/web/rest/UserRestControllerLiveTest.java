package ua.epam.spring.hometask.web.rest;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.UserTestData.MATCHER;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class UserRestControllerLiveTest extends AbstractRestLiveTest {

    private RestTemplate restTemplate;

    private static long createdId;

    @Before
    public void init() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());
    }

    @Test
    public void test1_create() {
        String url = "http://localhost:8080/movie/api/user";

        User createdUser = new User();
        createdUser.setFirstName("Vladimir2");
        createdUser.setLastName("Vys2");
        createdUser.setEmail("unlim2@mail.com");
        createdUser.setBirthday(LocalDate.of(1970, 5, 20));
        createdUser.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<>(createdUser, headers);

        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, entity, User.class);
//        ResponseEntity<User> response = restTemplate.postForEntity(url, entity, User.class);
        User actual = response.getBody();
        createdId = actual.getId();

        MATCHER.assertEquals(createdUser, actual);

    }

    @Test
    public void test2_getById() {
        String url = "http://localhost:8080/movie/api/user/{id}";

        User expectedUser = new User();
        expectedUser.setFirstName("Vladimir0");
        expectedUser.setLastName("Vys0");
        expectedUser.setEmail("unlim0@mail.com");

        User actual = restTemplate.getForObject(url, User.class, 0);

        MATCHER.assertEquals(expectedUser, actual);
    }

    @Test
    public void test3_update() {
        String url = "http://localhost:8080/movie/api/user/{id}";

        User updatedUser = new User();
        updatedUser.setFirstName("Vladimir11");
        updatedUser.setLastName("Vys11");
        updatedUser.setEmail("unlim11@mail.com");

//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<User> entity = new HttpEntity<>(updatedUser, headers);
        HttpEntity<User> entity = new HttpEntity<>(updatedUser);

        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, entity, User.class, 1);

        User actual = response.getBody();

        MATCHER.assertEquals(updatedUser, actual);

    }

    @Test
    public void test4_delete() {
        String url = "http://localhost:8080/movie/api/user/{id}";
        restTemplate.delete(url, createdId);
    }

    @Test
    public void test5_getAll() {
        String url = "http://localhost:8080/movie/api/user";

        User user0 = new User();
        user0.setFirstName("Vladimir0");
        user0.setLastName("Vys0");
        user0.setEmail("unlim0@mail.com");

        User user1 = new User();
        user1.setFirstName("Vladimir11");
        user1.setLastName("Vys11");
        user1.setEmail("unlim11@mail.com");

        ResponseEntity<List<User>> usersResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        List<User> users = usersResponse.getBody();

        MATCHER.assertCollectionEquals(
                Arrays.asList(user0, user1),
                users
                        .stream()
                        .sorted(Comparator.comparing(User::getFirstName).thenComparing(User::getLastName).thenComparing(User::getEmail))
                        .collect(Collectors.toList()));
    }
}