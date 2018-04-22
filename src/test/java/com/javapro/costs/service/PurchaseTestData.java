package com.javapro.costs.service;

import com.javapro.costs.model.Purchase;
import com.javapro.costs.service.matcher.ModelMatcher;

import java.time.LocalDate;
import java.util.Comparator;

public class PurchaseTestData {
    public static final ModelMatcher<Purchase> MATCHER =
            new ModelMatcher<>(Comparator
                    .comparing(Purchase::getCreatedDate)
                    .thenComparing(Purchase::getCategory)
                    .thenComparing(Purchase::getMoney)
                    .thenComparing(Purchase::getComment));


    public static final double MONEY1 = 0;
    public static final LocalDate CREATED_DATE1 = LocalDate.of(2018, 1, 1);
    public static final String CATEGORY1 = "meal";
    public static final String COMMENT1 = "milk and bread";


    public static final double MONEY2 = 70.5;
    public static final LocalDate CREATED_DATE2 = LocalDate.of(2018, 1, 15);
    public static final String CATEGORY2 = "internet";
    public static final String COMMENT2 = " ";

    public static final double MONEY3 = 7666.55;
    public static final LocalDate CREATED_DATE3 = LocalDate.of(2018, 2, 28);
    public static final String CATEGORY3 = "electronics";
    public static final String COMMENT3 = "laptop";


    public static final Purchase EXPECTED_PURCHASE1 = new Purchase(MONEY1, CREATED_DATE1, CATEGORY1, COMMENT1);
    public static final Purchase EXPECTED_PURCHASE2 = new Purchase(MONEY2, CREATED_DATE2, CATEGORY2, COMMENT2);
    public static final Purchase EXPECTED_PURCHASE3 = new Purchase(MONEY3, CREATED_DATE3, CATEGORY3, COMMENT3);
}
