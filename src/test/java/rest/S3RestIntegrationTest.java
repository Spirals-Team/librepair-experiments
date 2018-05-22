package rest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import test.utils.EmbeddedTomcat;

/**
 *
 * @author Cherry Rose Semeña
 */
public class S3RestIntegrationTest {
    private static final int SERVER_PORT = 9999;
    private static final String APP_CONTEXT = "/dbtest"; 
    private static EmbeddedTomcat tomcat;

    public S3RestIntegrationTest() {
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
    public void testValidAuthorBooksFound() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("author", "Alexandre Dumas")
                .contentType("application/json")
                .when()
                .get("/api/book/author").then()
                .statusCode(200)
                .body("size()", is(not(0)));
    }
 
    @Test
    public void testValidAuthorBooksNotFound() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("author", "Hans Christian Andersen")
                .contentType("application/json")
                .when()
                .get("/api/book/author").then()
                .statusCode(400)
                .body("error.message", is("No Book Found"));

    }

    
    @Test
    public void testInvalidAuthor() {
        given()
                .queryParam("db", "mongodb")
                .queryParam("author", "")
                .contentType("application/json")
                .when()
                .get("/api/book/author").then()
                .statusCode(400)
                .body("error.message", is("Invalid Input"));
    }
}
