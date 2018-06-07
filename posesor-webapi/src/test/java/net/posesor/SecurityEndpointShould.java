package net.posesor;

import lombok.val;
import net.posesor.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class SecurityEndpointShould {

    @LocalServerPort
    int port;

    @Test
    public void beRejectedAsInvalidUser() {
        given().port(port)
                .auth().basic("invalid user", "invalid password")
                .get("/api/security")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void beAcceptedAsDemoUser() {
        val userName = "demo " + UUID.randomUUID().toString();

        given().port(port)
                .auth().basic(userName, userName)
                .get("/api/security")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
