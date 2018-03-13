package org.w4tracking;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w4tracking.representations.idm.CompanyAttributes;
import org.w4tracking.representations.idm.ItemRepresentation;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@DefaultDeployment
public class CompanyResourceTest {

    @Test
    @RunAsClient
    public void createCompanyTest() throws MalformedURLException {
        String apiUrl = "http://localhost:8080/api/companies";
        CompanyResource companyService = RestClientBuilder.newBuilder()
                .baseUrl(new URL(apiUrl))
                .build(CompanyResource.class);

        ItemRepresentation<CompanyAttributes> company = new ItemRepresentation.Builder<CompanyAttributes>()
                .withType("company")
                .withAttributes(
                        new CompanyAttributes.Builder()
                                .withName("Wolsnut4")
                                .build()
                )
                .build();

        Response response = companyService.createCompany(company);

        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED);
    }

}