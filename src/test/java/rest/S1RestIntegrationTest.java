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
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.Book;
import test.utils.EmbeddedTomcat;

@RunWith(JUnitParamsRunner.class)
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
    @FileParameters("src/test/java/test/resources/S1-validinput.csv")
    public void testValidCityBooksFound(String city, String bookTitle, String authorFullName) {
        given()
                .queryParam("db", "stub")
                .queryParam("city", city)
                .contentType("application/json")
                .when()
                .get("/api/books/findByCity").then()
                .statusCode(200)
                .body("size()", is(not(0)))
                .body("bookTitle", hasItem(bookTitle));
        
    }
 
//    @Ignore
    @Test
    @FileParameters("src/test/java/test/resources/S1-notfound-cities.csv")
    public void testValidCityNoBookFound(String city) {
        given()
                .queryParam("db", "stub") // this has to be change to stub but the implementation always return books so it's gonna fail
                .queryParam("city", city)
                .contentType("application/json")
                .when()
                .get("/api/books/findByCity").then()
                .statusCode(400)
                .body("error.message", is("No Books Found"));

    }
        
    @Test
    @FileParameters("src/test/java/test/resources/S1-invalid-input-cities.csv")
    public void testInvalidCityNull(String input) {
        given()
                .queryParam("db", "stub")
                .queryParam("city", input)
                .contentType("application/json")
                .when()
                .get("/api/books/findByCity").then()
                .statusCode(400)
                .body("error.message", is("Invalid Input"));
    }
    

}
