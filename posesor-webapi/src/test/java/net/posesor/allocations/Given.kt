package net.posesor.allocations

import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private fun String.Companion.random(): String {
    return UUID.randomUUID().toString()
}

public fun fullValidChargeDto() : net.posesor.charges.ChargeDocumentDto {
    return net.posesor.charges.ChargeDocumentDto().apply {
        customerName = "Customer Name ${UUID.randomUUID()}"
        subjectName = "Subject Name ${UUID.randomUUID()}"
        paymentTitle = "Payment Title ${UUID.randomUUID()}"
        amount = BigDecimal.valueOf(10)
        paymentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}

