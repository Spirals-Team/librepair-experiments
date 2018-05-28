package rest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.utils.EmbeddedTomcat;

/**
 *
 * @author Cherry Rose Semeña
 */


@RunWith(JUnitParamsRunner.class)
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
    @FileParameters("src/test/java/test/resources/S3-validinput.csv")
    public void testValidAuthorBooksFound(String bookTitle, String authorFullName) {
        authorFullName = authorFullName.replace("_", ",");
        given()
                .queryParam("db", "stub")
                .queryParam("author", authorFullName)
                .contentType("application/json")
                .when()
                .get("/api/books/findByAuthor").then()
                .statusCode(200)
                .body("size()", is(not(0)))
                .body("bookTitle", hasItem(bookTitle));
    }
 
    @Test
    @FileParameters("src/test/java/test/resources/S3-notfound-authorFullName.csv")
    public void testValidAuthorBooksNotFound(String input) {
        given()
                .queryParam("db", "mongodb")
                .queryParam("author", input)
                .contentType("application/json")
                .when()
                .get("/api/books/findByAuthor").then()
                .statusCode(400)
                .body("error.message", is("No Books Found"));

    }

    
    @Test
    @FileParameters("src/test/java/test/resources/S3-invalid-input-authorFullName.csv")
    public void testInvalidAuthor(String input) {
        given()
                .queryParam("db", "mongodb")
                .queryParam("author", input)
                .contentType("application/json")
                .when()
                .get("/api/books/findByAuthor").then()
                .statusCode(400)
                .body("error.message", is("Invalid Input"));
    }
}
