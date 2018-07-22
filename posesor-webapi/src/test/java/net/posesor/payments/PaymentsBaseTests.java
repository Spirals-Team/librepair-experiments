//package net.posesor.payments;
//
//import lombok.val;
//import org.jetbrains.annotations.NotNull;
//import org.junit.runner.RunWith;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//public abstract class PaymentsBaseTests {
//
//    @NotNull
//    static HttpEntity<PaymentDto> prepareEntityRequest() {
//        val expected = Given.createFullPaymentDto();
//
//        //given:
//        val headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        return new HttpEntity<>(expected, headers);
//    }
//}
