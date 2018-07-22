package net.posesor.charges;

import com.jayway.restassured.http.ContentType;
import lombok.SneakyThrows;
import lombok.val;
import net.posesor.Application;
import net.posesor.WebSocketClient;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public final class ChargesEndpointShould {

    @LocalServerPort
    private int port;

    private String userName = "demo " + UUID.randomUUID().toString();

    /**
     * Endpoint should return template of REST document.
     * <p>
     * Currently the document is empty, so only return code means then functionality is working.
     */
    @Test
    public void getTemplate() {
        given().port(port).authentication().basic(userName, userName)
                .get("/api/charges/template")
                .then().statusCode(200);
    }

    @Test
    public void post() {
        val expected = Given.prepareFullChargeDocumentDto();

        val eventWaiter = new WebSocketClient()
                .connect(port, userName, ChargesNotifier.QUEUE, ChargesNotifier.ChargeCreatedDto.class)
                .blockingIterable().iterator();

        val location = given().port(port).authentication().basic(userName, userName)
                .contentType(ContentType.JSON)
                .body(expected)
                .post("/api/charges")
                .header("location");

        eventWaiter.next();

        val actual = given().port(port).authentication().basic(userName, userName)
                .get(location)
                .body()
                .as(ChargeDocumentDto.class);

        Assertions.assertThat(actual.getChargeId()).isNotEmpty();
        actual.setChargeId(null);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void put() {
        val expected = Given.prepareFullChargeDocumentDto();

        val eventWaiter = new WebSocketClient()
                .connect(port, userName, ChargesNotifier.QUEUE, ChargesNotifier.ChargeCreatedDto.class)
                .blockingIterable().iterator();

        val location = given().port(port).authentication().basic(userName, userName)
                .contentType(ContentType.JSON)
                .body(expected)
                .post("/api/charges")
                .header("location");

        eventWaiter.next();

        val draft = given().port(port).authentication().basic(userName, userName)
                .get(location)
                .body()
                .as(ChargeDocumentDto.class);
        draft.setCustomerName("Changed Customer Name");
        draft.setSubjectName("Changed Subject Name");

        given().port(port).authentication().basic(userName, userName)
                .contentType(ContentType.JSON)
                .body(draft)
                .put(location);

        eventWaiter.next();

        val actual = given().port(port).authentication().basic(userName, userName)
                .get(location).body().as(ChargeDocumentDto.class);

        Assertions.assertThat(actual).isEqualTo(draft);
    }

    @SneakyThrows
    @Test
    public void delete() {
        val expected = Given.prepareFullChargeDocumentDto();

        val eventWaiter = new WebSocketClient()
                .connect(port, userName, ChargesNotifier.QUEUE, ChargesNotifier.ChargeCreatedDto.class)
                .blockingIterable().iterator();

        val location = given().port(port).authentication().basic(userName, userName)
                .contentType(ContentType.JSON)
                .body(expected)
                .post("/api/charges")
                .header("location");

        eventWaiter.next();

        given().port(port).authentication().basic(userName, userName)
                .expect().statusCode(HttpStatus.OK.value())
                .delete(location);

        eventWaiter.next();

        given().port(port).authentication().basic(userName, userName)
                .expect().statusCode(HttpStatus.NOT_FOUND.value())
                .when().get(location);
    }

    @Test
    public void getById() {
        val expected = Given.prepareFullChargeDocumentDto();

        val eventWaiter = new WebSocketClient()
                .connect(port, userName, ChargesNotifier.QUEUE, ChargesNotifier.ChargeCreatedDto.class)
                .blockingIterable().iterator();

        val location = given().port(port).authentication().basic(userName, userName)
                .contentType(ContentType.JSON)
                .body(expected)
                .expect().statusCode(HttpStatus.CREATED.value())
                .when()
                .post("/api/charges")
                .header("location");

        eventWaiter.next();

        val returnedChargeById = given().port(port).authentication().basic(userName, userName)
                .get(location)
                .body()
                .as(ChargeDocumentDto.class);

        Assertions.assertThat(returnedChargeById.getChargeId()).isNotEmpty();

        returnedChargeById.setChargeId(null);
        Assertions.assertThat(returnedChargeById.equals(expected)).isTrue();
    }
}
