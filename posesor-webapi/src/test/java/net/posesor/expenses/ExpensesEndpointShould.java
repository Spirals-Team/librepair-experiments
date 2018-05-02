package net.posesor.expenses;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import lombok.SneakyThrows;
import lombok.val;
import net.posesor.Application;
import net.posesor.WebSocketClient;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class ExpensesEndpointShould {

    @LocalServerPort
    int port;

    private ExpenseDocumentDto expected;
    private String location;

    private Iterator<ExpensesNotifier.DocumentsChangedDto> eventWaiter;

    private String userName = "demo " + UUID.randomUUID().toString();

    @Before
    @SneakyThrows
    public void init() {
        RestAssured.port = port;
        RestAssured.authentication = basic(userName, userName);

        eventWaiter = new WebSocketClient()
                .connect(port, userName, ExpensesNotifier.QUEUE, ExpensesNotifier.DocumentsChangedDto.class)
                .blockingIterable().iterator();

        val template = given()
                .get("/api/expenses/template")
                .andReturn().as(ExpenseDocumentDto.class);
        expected = Given.prepareFullExpenseDtoModel();

        location = given()
                .contentType("application/json")
                .body(expected)
                .post("/api/expenses")
                .getHeader("location");

        Assertions.assertThat(location).isNotNull();

        // wait for notification about posted entity
        eventWaiter.next();
    }

    @Test
    @SneakyThrows
    public void post() {
        val actual = given().get(location).andReturn().as(ExpenseDocumentDto.class);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void put() {
        val draft = given().get(location).andReturn().as(ExpenseDocumentDto.class);
        val random = UUID.randomUUID().toString();
        draft.setSubjectName("Subject " + random);
        draft.setCustomerName("Customer " + random);
        draft.setAmount(expected.getAmount().add(BigDecimal.ONE));
        draft.setDescription("Description " + random);

        given()
                .contentType(ContentType.JSON)
                .body(draft)
                .expect().statusCode(HttpStatus.NO_CONTENT.value())
                .when()
                .put(location);

        eventWaiter.next();

        val actual = given().get(location).as(ExpenseDocumentDto.class);

        Assertions.assertThat(actual).isEqualTo(draft);
    }

    @Test
    @SneakyThrows
    public void delete() {
        val inserted = given().get(location).andReturn().as(ExpenseDocumentDto.class);

        given()
                .expect().statusCode(HttpStatus.NO_CONTENT.value())
                .when().delete(location);

        eventWaiter.next();

        given()
                .expect().statusCode(HttpStatus.NOT_FOUND.value())
                .when().get(location);
    }

    @Test
    public void getById(){
        val getByExpenseId = given()
                .get(location).andReturn().as(ExpenseDocumentDto.class);

        Assertions.assertThat(getByExpenseId).isEqualTo(expected);
    }

    @Test
    public void getAll() {
        val actual = given()
                .post("/api/expenses/query")
                .andReturn().as(QueryItemDto[].class);
        val items = Arrays.stream(actual).map(QueryItemDto::getItem);
        val locations = Arrays.stream(actual).map(it -> it.getLocation().toString());

        Assertions.assertThat(items).containsOnly(expected);
        Assertions.assertThat(locations).containsOnly(location);
    }
}
