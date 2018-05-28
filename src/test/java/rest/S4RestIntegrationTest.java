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
public class S4RestIntegrationTest {

    private static final int SERVER_PORT = 9999;
    private static final String APP_CONTEXT = "/dbtest"; 
    private static EmbeddedTomcat tomcat;

    public S4RestIntegrationTest() {
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
    @FileParameters("src/test/java/test/resources/S4-validinput.csv")
    public void testValidGeoLocationBooksFound(double lat, double lon, String bookTitle, String authorFullName) {
        given()
                .queryParam("db", "stub")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .contentType("application/json")
                .when()
                .get("/api/books/findByGeolocation").then()
                .statusCode(200)
                .body("size()", is(not(0)))
                .body("bookTitle", hasItem(bookTitle));
    }
 
//    @Ignore
    @Test
    @FileParameters("src/test/java/test/resources/S4-notfound-geolocation.csv")
    public void testValidGeoLocationNoBookFound(double lat, double lon) {
        given()
                .queryParam("db", "stub") // this has to be change to stub but the implementation always return books so it's gonna fail
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .contentType("application/json")
                .when()
                .get("/api/books/findByGeolocation").then()
                .statusCode(400)
                .body("error.message", is("No Books Found"));

    }
        
    @Test
    @FileParameters("src/test/java/test/resources/S4-invalid-input-geolocation.csv")
    public void testInvalidCityNull(String lat, String lon) {
        given()
                .queryParam("db", "stub")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .contentType("application/json")
                .when()
                .get("/api/books/findByGeolocation").then()
                .statusCode(400)
                .body("error.message", is("The requested resource was not found on our server")); //But it should be invalid 
    }
    

}
