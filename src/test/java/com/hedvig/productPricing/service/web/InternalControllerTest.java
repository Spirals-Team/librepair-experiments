package com.hedvig.productPricing.service.web;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedvig.productPricing.service.commands.ActivateInsuranceAtDate;
import com.hedvig.productPricing.service.query.ContractRepository;
import com.hedvig.productPricing.service.query.PerilRepository;
import com.hedvig.productPricing.service.query.PricingRepository;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.query.UserRepository;
import com.hedvig.productPricing.service.service.InsuranceBillingService;
import com.hedvig.productPricing.service.service.InsuranceService;
import com.hedvig.productPricing.service.service.InsuranceTransferService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = InternalController.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "hedvig.product-pricing.files.location = src/test/resources/")
public class InternalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommandGateway commandGateway;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ContractRepository contractRepository;

    @MockBean
    PerilRepository perilRepository;

    @MockBean
    PricingRepository pricingRepository;

    @MockBean
    InsuranceTransferService insuranceTransferService;

    @MockBean
    InsuranceBillingService insuranceBillingService;

    @MockBean
    AmazonS3Client s3Client;

    @MockBean
    InsuranceService insuranceService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void test() throws Exception {

        Mockito.when(commandGateway.sendAndWait(new ActivateInsuranceAtDate("1337", LocalDate.parse("2018-02-27")))).thenReturn(null);

        mockMvc.perform(
                    post("/_/insurance/1337/activateAtDate").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(new ActivateInsuranceAtDate("1337", LocalDate.parse("2018-02-27"))))).
                andExpect(status().is2xxSuccessful());

        Mockito.verify(commandGateway).sendAndWait(new ActivateInsuranceAtDate("1337", LocalDate.parse("2018-02-27")));
    }




}
