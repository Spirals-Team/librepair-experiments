package net.posesor;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.var;
import lombok.val;
import net.posesor.allocations.*;
import net.posesor.charges.ChargeDocumentDto;
import net.posesor.charges.ChargesEndpoint;
import net.posesor.charges.ChargesNotifier;
import net.posesor.expenses.ExpenseDocumentDto;
import net.posesor.expenses.ExpensesNotifier;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AllocateCommonExpenses {

    @LocalServerPort
    private int port = 0;

    /**
     * Scenario 1. Charges without costs need to be zeroed.
     * <p>
     * Implementation: we can't remove existing charges, so we would like to have
     * additional charges (hereafter correction) which zeroes the original one.
     */
    @Test
    public void allocateCostsScenario1() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.valueOf(10), LocalDate.of(2001, 1, 1))
                .when()
                .allocate(YearMonth.of(2001, 1), YearMonth.of(2001, 1), LocalDate.of(2001, 2, 3))
                .then()
                .ensureOptions(ExpectedAvailableAllocations.NONE)
                .ensureChargeDocuments(ExpectedChargeDocumentDefaulter.of()
                        .withAmount(BigDecimal.valueOf(-10))
                        .withPaymentDate(LocalDate.of(2001, 2, 3))
                );

    }

    /***
     * Scenario 2. Charges with total amount are equal to total amount of expenses.
     * <p>
     * Outcome:
     * * Correction documents are generated per every charge document
     * * Correction documents have amount '0'
     */
    @Test
    @SneakyThrows
    public void allocateCostsScenario2() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.valueOf(10), LocalDate.of(2001, 1, 1))
                .createExpense(BigDecimal.valueOf(10), LocalDate.of(2001, 1, 1))
                .when()
                .allocate(YearMonth.of(2001, 1), YearMonth.of(2001, 1), LocalDate.of(2001, 1, 1))
                .then()
                .ensureOptions(ExpectedAvailableAllocations.NONE)
                .ensureChargeDocuments(ExpectedChargeDocumentDefaulter.of()
                        .withAmount(BigDecimal.valueOf(0))
                        .withPaymentDate(LocalDate.of(2001, 1, 1))
                );
    }

    /**
     * Scenario
     * Recreate document should recreate settlement.
     * Purpose:
     * Bug reproduction
     * Given
     * 1) an unsettled chargeA
     * When
     * 2) remove the charge
     * 3) and recreate the charge
     * Then
     * 4) unsettled value should be same as defined in the ChargeA
     */
    @Test
    public void removeUnsettledValuesWhenOriginatingDocumentIsRemoved() {

        new AllocationDSL(port)
                .given()
                .createExpense(BigDecimal.TEN, LocalDate.of(2001, 1, 1))
                .createCharge(BigDecimal.TEN, LocalDate.of(2001, 2, 2))
                .createExpense(BigDecimal.TEN, LocalDate.of(2001, 3, 10))
                .then()
                .ensureOptions(ExpectedAvailableAllocations.of()
                                .withPeriodFrom(YearMonth.of(2001, 1))
                                .withPeriodTo(YearMonth.of(2001, 3)));
    }

    /**
     * Given
     * No previous activities
     * When
     * Define
     */
    @Test
    public void calculateMaxMinPeriod() {

    }

    /**
     * Scenario:
     * Change a charge twice.
     * Purpose:
     * Bug reproduction
     * Given
     * 1) an unsettled chargeA
     * When
     * 2) the chargeA content is changed
     * 3) and chargeA content is changed second time
     * Then
     * 4) unsettled value contains only the charge value from the latest change
     */
    @Test
    public void settlementsIsValidForChangeModifiedTwice() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.TEN, LocalDate.of(2001, 1, 1))
                .andUpdate()
                .withProducedValue(ExpectedChargeDocumentDefaulter.of()
                        .withCustomer("changed customer name")
                        .withSubject("changed subject name")
                        .withPaymentDate(LocalDate.of(2002, 2, 2))
                        .withPaymentTitle("changed payment title")
                        .withAmount(BigDecimal.valueOf(11))
                )
                .andUpdate()
                .withProducedValue(ExpectedChargeDocumentDefaulter.of()
                        .withCustomer("changed customer name *2")
                        .withSubject("changed subject name *2")
                        .withPaymentDate(LocalDate.of(2003, 3, 3))
                        .withPaymentTitle("changed payment title *2")
                        .withAmount(BigDecimal.valueOf(12))
                )
                .then()
                .ensureOptions(ExpectedAvailableAllocations.of()
                        .withSubject("changed subject name *2")
                        .withPayment("changed payment title *2")
                        .withPeriodFrom(YearMonth.of(2003, 3))
                        .withPeriodTo(YearMonth.of(2003, 3))
                )
                .ensureChargeDocuments(ExpectedChargeDocumentDefaulter.of()
                        .withSubject("changed subject name *2")
                        .withCustomer("changed customer name *2")
                        .withPaymentTitle("changed payment title *2")
                        .withAmount(BigDecimal.valueOf(12))
                        .withPaymentDate(LocalDate.of(2003, 3, 3))
                );

    }

    /**
     * Given
     * * No expectations
     * When
     * * 1 user creates a new Charge for a Subject
     * Then
     * * 2 The Subject has available allocations
     * * 3 and The Charge is included in The Settlements as unsettled part
     * When
     * * 4 user changes the Charge
     * Then
     * * 5 Unsettled Part reflects changes
     */
    @Test
    public void returnNewChargesAsUncleared() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.TEN, LocalDate.of(2000, 1, 1))
                .andUpdate()
                .withProducedValue(ExpectedChargeDocumentDefaulter.of()
                        .withSubject("changed")
                        .withPaymentTitle("changed")
                        .withAmount(BigDecimal.ONE)
                        .withPaymentDate(LocalDate.of(2001, 2, 2)))
                .then()
                .ensureChargeDocuments(ExpectedChargeDocumentDefaulter.of()
                        .withSubject("changed")
                        .withPaymentTitle("changed")
                        .withAmount(BigDecimal.ONE)
                        .withPaymentDate(LocalDate.of(2001, 2, 2)));
    }

    /**
     * Given
     * 1) an unsettled chargeA
     * Purpose:
     * Bug Reproduction
     * When
     * 2) the chargeA content is changed (excluding Subject)
     * 3) and the chargeA is removed
     * Then
     * 4) unsettled value disappeared
     */
    @Test
    public void removeUnsettledValueWhenOriginatingDocumentIsUpdatedAndRemoved() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.TEN, LocalDate.of(2000, 1, 1))
                .andUpdate()
                .withProducedValue(ExpectedChargeDocumentDefaulter.of()
                        .withSubject("changed")
                        .withPaymentTitle("changed")
                        .withAmount(BigDecimal.ONE)
                        .withPaymentDate(LocalDate.of(2001, 2, 2)))
                .andDelete()
                .then()
                .ensureOptions(ExpectedAvailableAllocations.NONE);
    }

    /**
     * Given
     * 1) an unsettled charge
     * When
     * 2) the charge's subject is changed
     * Then
     * 3) the new one subject has unsettled charges
     */
    @Test
    public void reflectDocumentSubjectChange() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.TEN, LocalDate.of(2001, 1, 1))
                .andUpdate()
                .withProducedValue(ExpectedChargeDocumentDefaulter.of()
                        .withSubject("Changed"))
                .then()
                .ensureOptions(ExpectedAvailableAllocations.of()
                        .withSubject("Changed")
                        .withPeriodFrom(YearMonth.of(2001, 1))
                        .withPeriodTo(YearMonth.of(2001, 1))
                );
    }

    /**
     * Given
     * 1) Charge
     * When
     * 2) Allocated
     * Then
     * 3) No more available allocations
     */
    @Test
    public void allocationZeroesAvailableAllocations() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.TEN, LocalDate.of(2001, 1, 1))
                .when()
                .allocate(YearMonth.of(2001, 1), YearMonth.of(2001, 1), LocalDate.of(2001, 1, 1))
                .then()
                .ensureOptions(ExpectedAvailableAllocations.NONE);
    }


    /**
     * Given
     * 1) No activities
     * When
     * 2) create documents with same subject and payemnt title and with different periods
     * Then
     * 3) Options contains one position with max and min periods
     */
    @Test
    public void optionNeedsAggregateSubjectAndPaymentTitle() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.TEN, LocalDate.of(2001, 1, 1))
                .createExpense(BigDecimal.TEN, LocalDate.of(2002, 2, 2))
                .then()
                .ensureOptions(ExpectedAvailableAllocations.of()
                        .withPeriodFrom(YearMonth.of(2001, 1))
                        .withPeriodTo(YearMonth.of(2002, 2)));
    }
    /**
     * Given
     * 1) No activities
     * When
     * 2) create an expense
     * 3) create a charge
     * then
     * 4) expense amount is available in unallocated documents
     * 5) charge amount is available in unallocated documents
     */
    @Test
    public void includeExpensesInUnallocatedOptions() {
        new AllocationDSL(port)
                .given()
                .createCharge(BigDecimal.ONE, LocalDate.of(2001, 1, 2))
                .createExpense(BigDecimal.TEN, LocalDate.of(2001, 1, 1))
                .ensureDocuments(
                        ExpectedUnallocatedDocuments.of()
                                .withCharge(BigDecimal.ONE)
                                .withPeriod(YearMonth.of(2001, 1)),
                        ExpectedUnallocatedDocuments.of()
                                .withExpense(BigDecimal.TEN)
                                .withPeriod(YearMonth.of(2001, 1)));
    }
}


class AllocationDSL {
    private int port;
    private final String defaultUserName = "demo " + UUID.randomUUID().toString();
    private final String defaultSubjectName = "Subject name " + UUID.randomUUID();
    private final String defaultPaymentTitle = "Payment title " + UUID.randomUUID();
    private final String defaultCustomerName = "Customer name " + UUID.randomUUID();

    /**
     * Prepare fluent API to operate with service located at port
     *
     * @param port http port for posesor.
     */
    AllocationDSL(int port) {
        this.port = port;
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    /**
     * Navigates fluent API to 'given' section with methods to create assets required to alllocate expenses.
     *
     * @return next step of fluent API.
     */
    public GivenStep given() {
        return new GivenStep();
    }

    class GivenStep extends DocumentsEnsureStep<GivenStep> {
        /**
         * Creates a charge with default customer, subject and payment title and with provided amount and payment date.
         *
         * @param amount      total amount of the charge.
         * @param paymentDate when the charge need to be payed.
         * @return next step in fluent API
         */
        ChargeOperationStep createCharge(BigDecimal amount, LocalDate paymentDate) {
            val chargeObserver = new WebSocketClient()
                    .connect(port, defaultUserName, ChargesNotifier.QUEUE, ChargesNotifier.ChargeCreatedDto.class)
                    .blockingIterable().iterator();
            val settlementsNotifier = new WebSocketClient()
                    .connect(port, defaultUserName, AllocationsNotifier.QUEUE, AllocationsNotifier.SettlementsChangedDto.class)
                    .blockingIterable().iterator();

            // create an charge and waits for well-known effects:
            // creation notification and new settlement.

            val createRequest = new ChargeDocumentDto();
            createRequest.setCustomerName(defaultCustomerName);
            createRequest.setSubjectName(defaultSubjectName);
            createRequest.setPaymentTitle(defaultPaymentTitle);
            createRequest.setAmount(amount);
            createRequest.setPaymentDate(paymentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

            val location = RestAssured
                    .given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .body(createRequest)
                    .post("/api/charges")
                    .header("location");

            chargeObserver.next();
            settlementsNotifier.next();
            return new ChargeOperationStep(location, createRequest);
        }

        GivenStep createExpense(BigDecimal amount, LocalDate paymentDate) {
            val documentObserver = new WebSocketClient()
                    .connect(port, defaultUserName, ExpensesNotifier.QUEUE, ExpensesNotifier.DocumentsChangedDto.class)
                    .blockingIterable().iterator();

            val expense = new ExpenseDocumentDto();
            expense.setPaymentDate(paymentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            expense.setSubjectName(defaultSubjectName);
            expense.setPaymentTitle(defaultPaymentTitle);
            expense.setAmount(amount);

            RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .body(expense)
                    .post("/api/expenses");

            documentObserver.next();
            return this;
        }

        AllocateStep when() {
            return new AllocateStep();
        }

        AssertionStep then() {
            return new AssertionStep();
        }
    }

    @AllArgsConstructor
    class ChargeOperationStep extends GivenStep {

        private final String location;
        private ChargeDocumentDto document;

        GivenStep andDelete() {
            val settlementsNotifier = new WebSocketClient()
                    .connect(port, defaultUserName, AllocationsNotifier.QUEUE, AllocationsNotifier.SettlementsChangedDto.class)
                    .blockingIterable().iterator();

            RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .delete(location)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value());

            // we expect 2 changes:
            // * removing charge document - let's ignore the event because we are not going to check if the document is removed

            // * removing unsettled charge
            settlementsNotifier.next();
            return new GivenStep();
        }

        ChargeUpdateStep andUpdate() {
            return new ChargeUpdateStep(location, document);
        }
    }

    @AllArgsConstructor
    class ChargeUpdateStep extends GivenStep {

        private final String location;
        private final ChargeDocumentDto document;

        ChargeOperationStep withProducedValue(ExpectedChargeDocumentDefaulter defaulter) {
            val settlementsNotifier = new WebSocketClient()
                    .connect(port, defaultUserName, AllocationsNotifier.QUEUE, AllocationsNotifier.SettlementsChangedDto.class)
                    .blockingIterable().iterator();

            val draft = new ChargeDocumentDto(
                    null,
                    document.getCustomerName(),
                    document.getSubjectName(),
                    document.getPaymentDate(),
                    document.getPaymentTitle(),
                    document.getAmount()
            );
            defaulter.apply(draft);

            RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .body(draft)
                    .put(location)
                    .then()
                    .assertThat().statusCode(HttpStatus.OK.value());

            // we expect two changes: removing previous value from allocations and ad changed values
            settlementsNotifier.next();
            settlementsNotifier.next();

            return new ChargeOperationStep(location, document);
        }
    }

    class AllocateStep {
        /**
         * Starts 'Allocate Common Expenses'
         *
         * @param periodFrom    initial date of allocation.
         * @param periodTo      final date of allocation.
         * @param operationDate date of operation.
         * @return final step of allocation to check final values.
         */
        ThenStep allocate(YearMonth periodFrom, YearMonth periodTo, LocalDate operationDate) {
            val operationObserver = new WebSocketClient()
                    .connect(port, defaultUserName, AllocationsOperationNotifier.QUEUE, AllocationsOperationNotifier.FinishedDto.class)
                    .blockingIterable().iterator();

            RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .body(AllocateCostsDto.of(
                            defaultSubjectName,
                            defaultPaymentTitle,
                            periodFrom,
                            periodTo,
                            operationDate))
                    .post("/api/allocations/calculateCostsAllocation")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value());

            operationObserver.next();

            return new ThenStep();
        }
    }

    class ThenStep {
        AssertionStep then() {
            return new AssertionStep();
        }

    }

    class DocumentsEnsureStep<SELF_TYPE extends DocumentsEnsureStep<SELF_TYPE>> {
        SELF_TYPE ensureDocuments(ExpectedUnallocatedDocuments.Defaulter itemDefaulter, ExpectedUnallocatedDocuments.Defaulter... otherDefaulters) {

            val actual = RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .get("/api/allocations/documents?subjectName={1}&paymentTitle={2}&from={3}&to={4}", defaultSubjectName, defaultPaymentTitle, "1900-01", "2999-12")
                    .thenReturn()
                    .body()
                    .as(AllocationDocumentsDto.class);

            val actualItems = actual.getItems();

            val defaulters = new LinkedList<ExpectedUnallocatedDocuments.Defaulter>();
            defaulters.add(itemDefaulter);
            defaulters.addAll(Arrays.stream(otherDefaulters).collect(Collectors.toList()));

            val expectedItems = defaulters.stream().map(it -> {
                val template = new AllocationDocumentsDto.DocumentData();
                ExpectedUnallocatedDocuments.apply(template, it);
                return template;
            }).toArray(AllocationDocumentsDto.DocumentData[]::new);

            Assertions.assertThat(actualItems).isEqualTo(expectedItems);
            return (SELF_TYPE) this;
        }
    }

    class AssertionStep {

        AssertionStep ensureOptions(ExpectedAvailableAllocations.None none) {
            // obtain current list of available Allocations
            val actual = RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .get("/api/allocations/options")
                    .thenReturn()
                    .body()
                    .as(AvailableAllocationsDto.class);

            Assertions.assertThat(actual.getElements()).isEmpty();
            return new AssertionStep();
        }

        AssertionStep ensureOptions(ExpectedAvailableAllocations.Defaulter itemDefaulter, ExpectedAvailableAllocations.Defaulter... otherDefaulters) {
            // obtain current list of available Allocations
            val actual = RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .get("/api/allocations/options")
                    .thenReturn()
                    .body()
                    .as(AvailableAllocationsDto.class);


            val defaulters = new LinkedList<ExpectedAvailableAllocations.Defaulter>();
            defaulters.add(itemDefaulter);
            defaulters.addAll(Arrays.stream(otherDefaulters).collect(Collectors.toList()));

            val expectedItems = defaulters.stream().map(it -> {
                val template = new AvailableAllocationsDto.SubjectDto();
                template.setPaymentTitle(defaultPaymentTitle);
                template.setSubjectName(defaultSubjectName);
                ExpectedAvailableAllocations.Defaulter.apply(template, it);
                return template;
            }).toArray(AvailableAllocationsDto.SubjectDto[]::new);

            val expected = new AvailableAllocationsDto();
            expected.setElements(expectedItems);

            Assertions.assertThat(actual).isEqualTo(expected);

            return new AssertionStep();
        }

        @SneakyThrows
        void ensureChargeDocuments(ExpectedChargeDocumentDefaulter itemDefaulter, ExpectedChargeDocumentDefaulter... otherDefaulters) {

            val defaulters = new ArrayList<ExpectedChargeDocumentDefaulter>();
            defaulters.add(itemDefaulter);
            defaulters.addAll(Arrays.stream(otherDefaulters).collect(Collectors.toList()));

            val expected = new ArrayList<ChargeDocumentDto>();
            for (val defaulter : defaulters) {
                var item = new ChargeDocumentDto(null, defaultCustomerName, defaultSubjectName, null, defaultPaymentTitle, BigDecimal.ZERO);
                defaulter.apply(item);
                expected.add(item);
            }

            val queryToken = RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .contentType(ContentType.JSON)
                    .body(new ChargesEndpoint.QueryDto("any"))
                    .post("/api/charges/search/query")
                    .andReturn()
                    .as(String.class);

            val queryResult = RestAssured.given()
                    .auth().basic(defaultUserName, defaultUserName)
                    .get("api/charges/search/execute/{1}", queryToken)
                    .andReturn()
                    .as(ChargesEndpoint.QueryItem[].class);

            val actual = Arrays
                    .stream(queryResult)
                    .map(ChargesEndpoint.QueryItem::getItem)
                    .map(it -> new ChargeDocumentDto(
                            // returned items have already set chargeId so let's
                            // clean it up to achieve equality with original models.
                            null, // //it.getChargeId()
                            it.getCustomerName(),
                            it.getSubjectName(),
                            it.getPaymentDate(),
                            it.getPaymentTitle(),
                            it.getAmount().setScale(2, RoundingMode.HALF_UP)));

            // for assertion we need to convert expected items to an array.
            val expectedAsArray = expected.toArray(new ChargeDocumentDto[0]);
            Assertions.assertThat(actual.collect(Collectors.toList())).containsSubsequence(expectedAsArray);
        }

    }
}

/**
 * Allows to define in a declarative manner what Available Allocation we expect to obtain from server.
 */
class ExpectedAvailableAllocations {

    static final None NONE = new None();

    static Defaulter of() {
        return new Defaulter();
    }

    private ExpectedAvailableAllocations() {
    }

    static class Defaulter {
        private final List<Consumer<AvailableAllocationsDto.SubjectDto>> operations = new ArrayList<>();

        private Defaulter() {
        }

        Defaulter withPeriodFrom(YearMonth from) {
            operations.add(it -> it.setPeriodFrom(new AvailableAllocationsDto.PeriodDto(from.getYear(), from.getMonthValue())));
            return this;
        }

        Defaulter withPeriodTo(YearMonth to) {
            operations.add(it -> it.setPeriodTo(new AvailableAllocationsDto.PeriodDto(to.getYear(), to.getMonthValue())));
            return this;
        }

        Defaulter withSubject(String name) {
            operations.add(it -> it.setSubjectName(name));
            return this;
        }

        Defaulter withPayment(String title) {
            operations.add(it -> it.setPaymentTitle(title));
            return this;
        }

        static void apply(AvailableAllocationsDto.SubjectDto template, Defaulter defaulter) {
            for (val operation : defaulter.operations) {
                operation.accept(template);
            }
        }
    }

    static class None {
        private None() {
        }
    }

}

class ExpectedChargeDocumentDefaulter {

    private final List<Consumer<ChargeDocumentDto>> operations = new ArrayList<>();

    private ExpectedChargeDocumentDefaulter() {
    }

    static ExpectedChargeDocumentDefaulter of() {
        return new ExpectedChargeDocumentDefaulter();
    }

    void apply(ChargeDocumentDto template) {
        for (val operation : operations) {
            operation.accept(template);
        }
    }

    ExpectedChargeDocumentDefaulter withSubject(String name) {
        operations.add(it -> it.setSubjectName(name));
        return this;
    }

    ExpectedChargeDocumentDefaulter withCustomer(String name) {
        operations.add(it -> it.setCustomerName(name));
        return this;
    }

    ExpectedChargeDocumentDefaulter withPaymentTitle(String title) {
        operations.add(it -> it.setPaymentTitle(title));
        return this;
    }

    ExpectedChargeDocumentDefaulter withAmount(BigDecimal amount) {
        operations.add(it -> it.setAmount(amount.setScale(2, RoundingMode.HALF_UP)));
        return this;
    }

    ExpectedChargeDocumentDefaulter withPaymentDate(LocalDate paymentDate) {
        operations.add(it -> it.setPaymentDate(paymentDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        return this;
    }
}

final class ExpectedUnallocatedDocuments {

    static Defaulter of() {
        return new Defaulter();
    }

    final static class Defaulter {
        private final List<Consumer<AllocationDocumentsDto.DocumentData>> operations = new ArrayList<>();

        Defaulter withPeriod(YearMonth period) {
            operations.add(it -> it.setPeriod(AllocationDocumentsDto.YearMonth.of(period.getYear(), period.getMonthValue())));
            return this;
        }

        Defaulter withCharge(BigDecimal amount) {
            operations.add(it -> it.setChargesAmount(amount));
            operations.add(it -> it.setExpensesAmount(BigDecimal.ZERO));
            return this;
        }

        Defaulter withExpense(BigDecimal amount) {
            operations.add(it -> it.setExpensesAmount(amount));
            operations.add(it -> it.setChargesAmount(BigDecimal.ZERO));
            return this;
        }
    }

    static void apply(AllocationDocumentsDto.DocumentData template, ExpectedUnallocatedDocuments.Defaulter defaulter) {
        for (val operation : defaulter.operations) {
            operation.accept(template);
        }
    }

}