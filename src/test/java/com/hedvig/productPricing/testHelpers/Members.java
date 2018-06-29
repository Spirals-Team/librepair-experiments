package com.hedvig.productPricing.testHelpers;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product;

import java.time.LocalDate;
import java.util.ArrayList;

public class Members {


    public static MemberFake TOLVAN = new MemberFake("1",
            "191212121212",
            "Tolvan",
            "Tolvanson",
            LocalDate.parse("1912-12-12"),
            new Address("Långgatan", "Storstan", "12345"),
            115.0f,
            Product.ProductTypes.RENT,
            null,
            3,
            false,
            new ArrayList<>());

    public static MemberFake MRTVA = new MemberFake("2",
            "199906222394",
            "Herbert",
            "Larsson",
            LocalDate.parse("1912-12-12"),
            new Address("L:a gränden", "Byn", "54321"),
            230.0f,
            Product.ProductTypes.RENT,
            "TRYGGHANSA",
            4,
            true,
            new ArrayList<>()
            );

}
