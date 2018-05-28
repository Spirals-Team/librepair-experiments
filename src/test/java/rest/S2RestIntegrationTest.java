package rest;

import org.junit.BeforeClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.utils.EmbeddedTomcat;


@RunWith(JUnitParamsRunner.class)
public class S2RestIntegrationTest {

    private static final int SERVER_PORT = 9999;
    private static final String APP_CONTEXT = "/dbtest";
    private static EmbeddedTomcat tomcat;

    public S2RestIntegrationTest() {
    }

    @BeforeClass
    public static void setUpBeforeAll() throws ServletException, MalformedURLException, LifecycleException {
        tomcat = new EmbeddedTomcat();
        tomcat.start(SERVER_PORT, APP_CONTEXT);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = SERVER_PORT;
        RestAssured.basePath = APP_CONTEXT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterClass
    public static void after() throws ServletException, MalformedURLException, LifecycleException {
        tomcat.stop();
    }

    @Test
    @FileParameters("src/test/java/test/resources/S2-validinput.csv")
    public void testValidTitleCitiesFound(String bookTitle, String city, double lat, double lon) {
        given()
                .queryParam("db", "stub")
                .queryParam("title", bookTitle)
                .contentType("application/json")
                .when()
                .get("/api/cities/findByBookTitle").then()
                .statusCode(200)
                .body("size()", is(not(0)))
                .body("name", hasItem(city));
    }

    @Test
    @FileParameters("src/test/java/test/resources/S2-notfound-bookTitle.csv")
    public void testValidTitleNoCityFound(String city) {
        given()
                .queryParam("db", "stub")
                .queryParam("title", city)
                .contentType("application/json")
                .when()
                .get("/api/cities/findByBookTitle").then()
                .statusCode(400)
                .body("error.message", is("No Cities Found"));

    }

    @Test
    @FileParameters("src/test/java/test/resources/S2-invalid-input-bookTitle.csv")
    public void testInvalidTitle(String input) {
        given()
                .queryParam("db", "stub")
                .queryParam("title", "")
                .contentType("application/json")
                .when()
                .get("/api/cities/findByBookTitle").then()
                .statusCode(400)
                .body("error.message", is("Invalid Input"));
    }

}
