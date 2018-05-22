package rest;

import org.junit.BeforeClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import test.utils.EmbeddedTomcat;

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
    public void testValidTitleCitiesFound() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("title", "The Three Musketeers")
                .contentType("application/json")
                .when()
                .get("/api/city").then()
                .statusCode(200)
                .body("size()", is(not(0)));
    }
 
    @Test
    public void testValidTitleNoCityFound() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("title", "The Book of Copenhagen")
                .contentType("application/json")
                .when()
                .get("/api/city").then()
                .statusCode(400)
                .body("error.message", is("No Cities Found"));

    }

    
    @Test
    public void testInvalidTitle() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("title", "")
                .contentType("application/json")
                .when()
                .get("/api/city").then()
                .statusCode(400)
                .body("error.message", is("Invalid Input"));
    }
    

}
