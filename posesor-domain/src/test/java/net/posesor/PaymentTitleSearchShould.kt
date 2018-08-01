package net.posesor

import net.posesor.query.PaymentTitleView
import net.posesor.query.PaymentTitlesQuery
import org.assertj.core.api.Assertions
import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.GenericQueryMessage
import org.axonframework.queryhandling.QueryBus
import org.axonframework.queryhandling.responsetypes.ResponseTypes
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = arrayOf(TestContext::class))
class PaymentTitleSearchShould {

    @Autowired
    var commandGateway: CommandGateway? = null

    @Autowired
    val queryBus: QueryBus? = null

    var principalName = "will be initialized before each test"

    @Before
    fun initialize() {
        principalName = "principal name ${UUID.randomUUID()}"
    }
    /**
     * https://github.com/Respekto/posesor-backend/issues/28
     */
    @Test
    fun includeChargeTitleAsPaymentTitle() {
        commandGateway!!.send(
                UnallocatedChargeDocument.CreateCommand(
                        principalName,
                        "docId",
                        "customer name",
                        "subject name",
                        LocalDate.of(2001,1,1),
                        "payment title",
                        BigDecimal.ONE),
                LoggingCallback.INSTANCE)

        val query = GenericQueryMessage<PaymentTitlesQuery, List<PaymentTitleView>>(
                PaymentTitlesQuery(principalName, ""),
                ResponseTypes.multipleInstancesOf(PaymentTitleView::class.java))
        val result = queryBus!!.query(query).get()

        Assertions.assertThat(result.payload).containsOnlyOnce(PaymentTitleView("payment title"))
    }

    @Test()
    fun removeNotUsedPaymentTitle() {

        // create and delete document should remove document's payment title as well
        commandGateway!!.send(
                UnallocatedChargeDocument.CreateCommand(
                        principalName,
                        "docId",
                        "customer name",
                        "subject name",
                        LocalDate.of(2001,1,1),
                        "payment title",
                        BigDecimal.ONE),
                LoggingCallback.INSTANCE)

        commandGateway!!.send(
                UnallocatedChargeDocumentDeleteCommand("docId", principalName),
                LoggingCallback.INSTANCE
        )


        val query = GenericQueryMessage<PaymentTitlesQuery, List<PaymentTitleView>>(
                PaymentTitlesQuery(principalName, ""),
                ResponseTypes.multipleInstancesOf(PaymentTitleView::class.java))
        val result = queryBus!!.query(query).get()

        Assertions.assertThat(result.payload).isEmpty()
    }

    @Test(timeout = 1000)
    fun keepPaymentTitleUsedMultipleTimes() {

        // create twice and delete document shouldn't remove document's payment title
        val commandTemplate = UnallocatedChargeDocument.CreateCommand(
                principalName,
                "docId",
                "customer name",
                "subject name",
                LocalDate.of(2001,1,1),
                "payment title",
                BigDecimal.ONE)
        commandGateway!!.send(
                commandTemplate,
                LoggingCallback.INSTANCE)
        commandGateway!!.send(
                commandTemplate,
                LoggingCallback.INSTANCE
        )


        val query = GenericQueryMessage<PaymentTitlesQuery, List<PaymentTitleView>>(
                PaymentTitlesQuery(principalName, ""),
                ResponseTypes.multipleInstancesOf(PaymentTitleView::class.java))
        val result = queryBus!!.query(query).get()

        Assertions.assertThat(result.payload).isNotEmpty()
    }

    @Test
    fun includeChangedTitle() {
        commandGateway!!.send(
                UnallocatedChargeDocument.CreateCommand(
                        principalName,
                        "docId",
                        "customer name",
                        "subject name",
                        LocalDate.of(2001,1,1),
                        "payment title old",
                        BigDecimal.ONE),
                LoggingCallback.INSTANCE)

        commandGateway!!.send(
                UnallocatedChargeDocument.UpdateCommand(
                        "docId",
                        "customer name",
                        "subject name",
                        LocalDate.of(2001,1,1),
                        "payment title new",
                        BigDecimal.ONE),
                LoggingCallback.INSTANCE)


        val query = GenericQueryMessage<PaymentTitlesQuery, List<PaymentTitleView>>(
                PaymentTitlesQuery(principalName,""),
                ResponseTypes.multipleInstancesOf(PaymentTitleView::class.java))
        val result = queryBus!!.query(query).get()

        Assertions.assertThat(result.payload).containsOnlyOnce(PaymentTitleView("payment title new"))
    }

    @Test
    fun filter() {
        commandGateway!!.send(
                UnallocatedChargeDocument.CreateCommand(
                        principalName,
                        "docId",
                        "customer name",
                        "subject name",
                        LocalDate.of(2001,1,1),
                        "payment title old",
                        BigDecimal.ONE),
                LoggingCallback.INSTANCE)


        val negativeQuery = GenericQueryMessage<PaymentTitlesQuery, List<PaymentTitleView>>(
                PaymentTitlesQuery(principalName, "new"),
                ResponseTypes.multipleInstancesOf(PaymentTitleView::class.java))

        Assertions.assertThat(queryBus!!.query(negativeQuery).get().payload).isEmpty()
    }
}

