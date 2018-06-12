package net.posesor.allocations

import com.jayway.restassured.RestAssured
import com.jayway.restassured.RestAssured.basic
import com.jayway.restassured.RestAssured.given
import com.jayway.restassured.http.ContentType
import com.jayway.restassured.parsing.Parser
import net.posesor.Application
import net.posesor.WebSocketClient
import net.posesor.charges.ChargeDocumentDto
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SettlementShould {

    @LocalServerPort
    private val port = 0

    private val userName = "demo " + UUID.randomUUID().toString()

    @Before
    fun init() {
        RestAssured.port = port
        RestAssured.authentication = basic(userName, userName)
        RestAssured.defaultParser = Parser.JSON
    }


    /**
     * Given
     * 1) an unsettled charge
     * When
     * 2) the charge's subject is changed
     * 3) the charge's subject is restored
     * Then
     * 4) the old one subject has unsettled charges
     */
    @Test
    fun reflectDocumentSubjectChangeAndRestore() {
        // 1)
        val charge = ChargeDocumentDto()
        charge.subjectName = "Subject Name " + UUID.randomUUID().toString()
        charge.paymentTitle = "Payment Title " + UUID.randomUUID().toString()
        charge.amount = BigDecimal.TEN
        charge.paymentDate = "2000-01-01"


        val chargesNotifier = WebSocketClient()
                .connect(port, userName, net.posesor.charges.ChargesNotifier.QUEUE, net.posesor.charges.ChargesNotifier.ChargeCreatedDto::class.java)
                .blockingIterable().iterator()

        val settlementsNotifier = WebSocketClient()
                .connect(port, userName, net.posesor.allocations.AllocationsNotifier.QUEUE, net.posesor.allocations.AllocationsNotifier.SettlementsChangedDto::class.java)
                .blockingIterable().iterator()

        val location = given()
                .contentType(ContentType.JSON)
                .body(charge)
                .post("/api/charges")
                .header("location")
        // waiting for the charge
        chargesNotifier.next()
        // created charge have to generate unsettled charge
        settlementsNotifier.next()

        val newSubjectName = "Changed ${charge.subjectName}"
        val oldSubjectName = charge.subjectName

        // 2)
        charge.subjectName = newSubjectName
        given()
                .contentType(ContentType.JSON)
                .body(charge)
                .put(location)
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())

        // we expect 2 changes:
        // * adding new unsettled charge
        // * removing prev unsettled charge
        settlementsNotifier.next()
        settlementsNotifier.next()

        // 2)
        charge.subjectName = oldSubjectName
        given()
                .contentType(ContentType.JSON)
                .body(charge)
                .put(location)
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())

        // we expect 2 changes:
        // * adding new unsettled charge
        // * removing prev unsettled charge
        settlementsNotifier.next()
        settlementsNotifier.next()

        run {
            // 3)
            val actual = given()
                    .contentType(ContentType.JSON)
                    .get("/api/allocations/options")
                    .thenReturn()
                    .body()
                    .`as`<net.posesor.allocations.AvailableAllocationsDto>(net.posesor.allocations.AvailableAllocationsDto::class.java)
                    as net.posesor.allocations.AvailableAllocationsDto;

            val subjects = Arrays.stream(actual.elements)
                    .map<String> { it -> it.getSubjectName() }

            Assertions.assertThat(subjects).containsOnly(oldSubjectName)
        }
    }
}