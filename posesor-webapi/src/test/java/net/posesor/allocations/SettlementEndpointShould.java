package net.posesor.allocations;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import lombok.val;
import net.posesor.Application;
import net.posesor.WebSocketClient;
import net.posesor.charges.ChargeDocumentDto;
import net.posesor.charges.ChargesNotifier;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class SettlementEndpointShould {

    @LocalServerPort
    private int port = 0;

    private String userName = "demo " + UUID.randomUUID().toString();

    @Before
    public void init() {
        RestAssured.port = port;
        RestAssured.authentication = basic(userName, userName);
        RestAssured.defaultParser = Parser.JSON;
    }

    /**
     * Given
     * 1) an unsettled chargeA
     * When
     * 2) the chargeA is removed
     * Then
     * 3) unsettled value disappeared
     */
    @Test
    public void removeUnsettledValueWhenOriginatingDocumentIsRemoved() {
        // 1)
        val charge = new ChargeDocumentDto();
        charge.setSubjectName("Subject Name " + UUID.randomUUID().toString());
        charge.setPaymentTitle("Payment Title " + UUID.randomUUID().toString());
        charge.setAmount(BigDecimal.TEN);
        charge.setPaymentDate("2000-01-01");


        val chargesNotifier = new WebSocketClient()
                .connect(port, userName, ChargesNotifier.QUEUE, ChargesNotifier.ChargeCreatedDto.class)
                .blockingIterable().iterator();

        val settlementsNotifier = new WebSocketClient()
                .connect(port, userName, AllocationsNotifier.QUEUE, AllocationsNotifier.SettlementsChangedDto.class)
                .blockingIterable().iterator();

        val location = given()
                .contentType(ContentType.JSON)
                .body(charge)
                .post("/api/charges")
                .header("location");
        // waiting for the charge
        chargesNotifier.next();
        // created charge have to generate unsettled charge
        settlementsNotifier.next();

        // 2)
        given()
                .delete(location)
                .then()
                .assertThat().statusCode(HttpStatus.OK.value());

        // we expect 2 changes:
        // * removing charge document - let's ignore the event because we are not going to check if the document is removed

        // * removing unsettled charge
        settlementsNotifier.next();

        {
            // 3)
            val actual = given()
                    .contentType(ContentType.JSON)
                    .get("/api/allocations/options")
                    .thenReturn()
                    .body()
                    .as(AvailableAllocationsDto.class);

            Assertions.assertThat(actual.getElements()).isEmpty();
        }
    }

}
