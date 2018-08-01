package net.posesor.api

import net.posesor.*
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth

@RunWith(JUnit4::class)
class SubjectShould {
    private var fixture: FixtureConfiguration<Subject>? = null

    @Before
    fun setUp() {
        fixture = AggregateTestFixture(Subject::class.java)
    }

    @Test
    fun reserveExpenseDocumentsWhenTheyAreInRangeOfPeriod() {
        fixture!!
                .given(
                        SubjectCreatedEvent("principal", "subjectId", "name"),
                        UnallocatedExpenseAddedEvent("subjectId", "principal", "doc1", LocalDate.of(2001, 1, 1), "payment title", BigDecimal(1)),
                        UnallocatedExpenseAddedEvent("subjectId", "principal", "doc2", LocalDate.of(2001, 2, 1), "payment title", BigDecimal(1)),
                        UnallocatedExpenseAddedEvent("subjectId", "principal", "doc3", LocalDate.of(2001, 3, 1), "payment title", BigDecimal(1)))
                .`when`(SubjectReserveDocumentsCommand("subjectId", "ignored", "payment title", YearMonth.of(2001, 2), YearMonth.of(2001, 2)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(SubjectDocumentsReservedEvent("ignored", "subjectId", arrayOf(SubjectDocumentsReservedEvent.ExpenseDocumentId("doc2")), arrayOf()))
    }

    @Test
    fun reserveChargeDocumentsWhenTheyAreInRangeOfPeriod() {
        fixture!!
                .given(
                        SubjectCreatedEvent("principal", "subjectId", "name"),
                        SubjectChargeAddedEvent("doc1", "account1", LocalDate.of(2001, 1, 1), "payment title", BigDecimal(1)),
                        SubjectChargeAddedEvent("doc2", "account1", LocalDate.of(2001, 2, 1), "payment title", BigDecimal(1)),
                        SubjectChargeAddedEvent("doc3", "account1", LocalDate.of(2001, 3, 1), "payment title", BigDecimal(1)))
                .`when`(SubjectReserveDocumentsCommand("subjectId", "ignored", "payment title", YearMonth.of(2001, 2), YearMonth.of(2001, 2)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(SubjectDocumentsReservedEvent("ignored", "subjectId", arrayOf(), arrayOf(SubjectDocumentsReservedEvent.ChargeDocumentId("doc2"))))
    }

    @Test
    fun removeAllocatedDocuments() {
        fixture!!
                .given(
                        SubjectCreatedEvent("principal", "subjectId", "name"),
                        SubjectChargeAddedEvent("docA", "account1", LocalDate.of(2001, 1, 1), "payment title", BigDecimal(1)),
                        SubjectDocumentsReservedEvent("correlationId", "subjectId", arrayOf(), arrayOf(SubjectDocumentsReservedEvent.ChargeDocumentId("docA")))
                )
                .`when`(SubjectAllocateReservedDocumentsCommand("subjectId", "correlationId"))
                .expectEvents(UnallocatedChargeRemovedEvent("principal", "subjectId", LocalDate.of(2001, 1, 1), "payment title", BigDecimal(1)))
    }
}