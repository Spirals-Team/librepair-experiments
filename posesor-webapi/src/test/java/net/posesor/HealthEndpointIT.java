package net.posesor;

import lombok.val;
import net.posesor.Application;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class HealthEndpointIT {

    @LocalServerPort
    public int port;

    @Test
    public void reportGoodHealth() {
        val token = given()
                .port(port)
                .expect().statusCode(HttpStatus.OK.value())
                .get("/health/check")
                .body()
                .as(Boolean.class);

        Assertions.assertThat(token).isEqualTo(true);
    }
}