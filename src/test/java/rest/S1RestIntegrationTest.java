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
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import rest.Book;
import test.utils.EmbeddedTomcat;

public class S1RestIntegrationTest {

    private static final int SERVER_PORT = 9999;
    private static final String APP_CONTEXT = "/dbtest"; 
    private static EmbeddedTomcat tomcat;

    public S1RestIntegrationTest() {
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

//    @Ignore
    @Test
    public void testValidCityBooksFound() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("city", "Madrid")
                .contentType("application/json")
                .when()
                .get("/api/book/city").then()
                .statusCode(200)
                .body("size()", is(not(0)));

    }
 
//    @Ignore
    @Test
    public void testValidCityNoBookFound() {
        given()
                .queryParam("db", "mongodb") // this has to be change to stub but the implementation always return books so it's gonna fail
                .queryParam("city", "Lyngby")
                .contentType("application/json")
                .when()
                .get("/api/book/city").then()
                .statusCode(400)
                .body("error.message", is("No Book Found"));

    }

    
//    @Ignore
    @Test
    public void testInvalidCityNull() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("city", "")
                .contentType("application/json")
                .when()
                .get("/api/book/city").then()
                .statusCode(400)
                .body("error.message", is("Invalid Input"));
    }
    

}
