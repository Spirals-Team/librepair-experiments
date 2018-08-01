package net.posesor.payments;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import lombok.val;
import net.posesor.Application;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class PaymentsEndpointShould {

    @LocalServerPort
    int port;

    @Before
    public void init()  {
        val userName = "demo " + UUID.randomUUID().toString();

        RestAssured.port = port;
        RestAssured.authentication = basic(userName, userName);
    }

    @Test
    public void returnNotFoundWhenLocationIsNonExisting() {
        given()
                .expect()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .get("/api/payments/{paymentId}", UUID.randomUUID());
    }

    @Test
    public void getAllEntries() {
        val entry = Given.createFullPaymentDto();

        given()
                .contentType(ContentType.JSON)
                .body(entry)
                .expect().statusCode(HttpStatus.CREATED.value())
                .post("/api/payments");

        val actual = given()
                .get("/api/payments")
                .andReturn()
                .as(PaymentDto[].class);

        // returned elements have already inserted paymentId,
        // so let manually adjust expected with actual to make sense checking collection equality
        entry.setPaymentId(actual[0].getPaymentId());

        Assertions.assertThat(actual).contains(entry);
    }

    @Test
    public void deletePayment() {
        val entry = Given.createFullPaymentDto();

        val location = given()
                .contentType(ContentType.JSON)
                .body(entry)
                .expect().statusCode(HttpStatus.CREATED.value())
                .post("/api/payments")
                .header("location");

        given()
                .expect().statusCode(HttpStatus.OK.value())
                .delete(location);


        val actual = given()
                .get("/api/payments")
                .andReturn()
                .as(PaymentDto[].class);

        Assertions.assertThat(actual).doesNotContain(entry);
    }
}
