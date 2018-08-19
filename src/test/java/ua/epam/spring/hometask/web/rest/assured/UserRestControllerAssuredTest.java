package ua.epam.spring.hometask.web.rest.assured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class UserRestControllerAssuredTest {

    @Before
    public void setUp() {
        RestAssured.port = 8080;    //8080 by default
    }

    @Test
    public void create() throws Exception {
        String bodyStringValue =
                given()
                        .contentType(ContentType.JSON)
                        .body(ResourceUtil.loadFrom("/user/create_user.json")).
                        when()
                        .post("/movie/api/user").
                        then()
                        .statusCode(201)
                        .assertThat()
                        .body("id", greaterThan(-1))
                        .extract().body().asString();

        assertTrue(bodyStringValue.contains("Natalia"));
    }

    @Test
    public void getByIdStatusCodeAndResponseTest() {
        String bodyStringValue =
                when()
                        .get("/movie/api/user/0").
                        then()
                        .statusCode(200)
                        .extract().body().asString();
        assertTrue(bodyStringValue.contains("firstName"));
        assertTrue(bodyStringValue.contains("lastName"));
        assertTrue(bodyStringValue.contains("email"));
        assertTrue(bodyStringValue.contains("birthday"));
        assertTrue(bodyStringValue.contains("tickets"));
        assertTrue(bodyStringValue.contains("luckyEvents"));
        assertTrue(bodyStringValue.contains("password"));
        assertTrue(bodyStringValue.contains("roles"));
    }

    @Test
    public void update() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(ResourceUtil.loadFrom("/user/updated_user.json")).
                when()
                .put("/movie/api/user/0").
                then()
                .assertThat()
                .body("id", equalTo(0)).and()
                .body("firstName", equalTo("Vladimir0_changed")).and()
                .body("lastName", equalTo("Vys0_changed")).and()
                .body("email", equalTo("unlim0@mail.com_changed")).and()
                .body("birthday", hasItems(1990, 10, 10)).and()
                .body("password", notNullValue()).and()
                .body("roles", hasItems("REGISTERED_USER", "BOOKING_MANAGER"));
    }

    @Test
    public void delete() {

    }

    @Test
    public void getAll() {
        when().
                get("/movie/api/user").
                then().
                statusCode(200).
                body("id", hasItems(0, 1));
    }

    @Test
    public void uploadUsers() {
    }
}