package org.w4tracking;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w4tracking.models.ModelType;
import org.w4tracking.representations.idm.CompanyAttributesRepresentation;
import org.w4tracking.representations.idm.DataRepresentation;
import org.w4tracking.representations.idm.ItemRepresentation;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@DefaultDeployment
public class CompanyResourceTest {

    @Test
    @RunAsClient
    public void createCompanyTest() throws MalformedURLException {
        String apiUrl = "http://localhost:8080/api/companies";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(apiUrl);

        ItemRepresentation<CompanyAttributesRepresentation> company = new ItemRepresentation.Builder<CompanyAttributesRepresentation>()
                .withData(
                        new DataRepresentation.Builder<CompanyAttributesRepresentation>()
                                .withType(ModelType.COMPANY.getAlias())
                                .withAttributes(
                                        new CompanyAttributesRepresentation.Builder()
                                                .withName("Wolsnut4")
                                                .build()
                                )
                                .build()
                ).build();

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(company));

        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

}