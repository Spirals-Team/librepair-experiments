package org.w4tracking;

import org.w4tracking.models.transaction.W4TrackingTransaction;
import org.w4tracking.models.transaction.W4Transactional;
import org.w4tracking.representations.idm.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RequestScoped
//@W4Transactional
public class DefaultCompanyResource implements CompanyResource {

//    @Inject
//    private W4TrackingTransaction transaction;

    @Override
    public Response createCompany(ItemRepresentation<CompanyAttributes> rep) {
        DataRepresentation<CompanyAttributes> data = rep.getData();
        Map<String, Object> meta = rep.getMeta();
        Map<String, String> links = rep.getLinks();

        ItemRepresentation<CompanyAttributes> company = new ItemRepresentation.Builder<CompanyAttributes>()
                .withId(UUID.randomUUID().toString())
                .withType("company")
                .withLinks(
                        new LinksRepresentation.Builder()
                                .withMeta("withMeta")
                                .withRelated("withRelated")
                                .withSelf("withSelf")
                                .build()
                )
                .withAttributes(
                        new CompanyAttributes.Builder()
                        .withName("Wolsnut4")
                        .build()
                )
                .build();

        return Response.ok(company).build();
    }

    @Override
    public void updateCompany(ItemRepresentation<CompanyAttributes> rep) {

    }

    @Override
    public ItemRepresentation<CompanyAttributes> getCompanies() {
        ItemRepresentation<CompanyAttributes> company = new ItemRepresentation.Builder<CompanyAttributes>()
                .withId(UUID.randomUUID().toString())
                .withType("company")
                .withLinks(
                        new LinksRepresentation.Builder()
                                .withMeta("withMeta")
                                .withRelated("withRelated")
                                .withSelf("withSelf")
                                .build()
                )
                .withAttributes(new CompanyAttributes())
                .build();
        return company;
    }

    @Override
    public CollectionRepresentation<CompanyAttributes> getCompany(String companyId) {
        return null;
    }
}
