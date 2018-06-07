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
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public final class PaymentUpdateShould {

    @LocalServerPort
    private int port;

    // updated Dto
    private PaymentDto expected;
    // returned Dto from server
    private PaymentDto actual;

    @Before
    public void init() {
        val userName = "demo " + UUID.randomUUID().toString();
        RestAssured.port = port;
        RestAssured.authentication = basic(userName, userName);

        // initially create a random DTO
        val location = given()
                .contentType(ContentType.JSON)
                .body(expected = Given.createFullPaymentDto())
                .expect().statusCode(HttpStatus.CREATED.value())
                .post("/api/payments")
                .andReturn()
                .header("location");

        // replace ith withe next random Dto
        given()
                .contentType(ContentType.JSON)
                .body(expected = Given.createFullPaymentDto())
                .put(location);

        actual = given()
                .get(location)
                .body()
                .as(PaymentDto.class);

        // the only accepted difference is that returned model has already paymentId
        // let do correction so that whole objects need to be equal.
        expected.setPaymentId(actual.getPaymentId());
    }

    @Test
    public void updatedDtoReplacedOldOne() {
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}