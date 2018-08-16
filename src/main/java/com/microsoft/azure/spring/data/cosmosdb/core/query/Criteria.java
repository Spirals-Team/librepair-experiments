/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.core.query;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Criteria {

    private String subject;
    private List<Object> subjectValues;
    private final CriteriaType type;
    private final List<Criteria> subCriteria;

    private Criteria(CriteriaType type) {
        this.type = type;
        this.subCriteria = new ArrayList<>();
    }

    public static boolean isBinaryOperation(CriteriaType type) {
        switch (type) {
            case AND:
            case OR:
                return true;
            default:
                return false;
        }
    }

    public static boolean isUnaryOperation(CriteriaType type) {
        switch (type) {
            case IS_EQUAL:
                return true;
            default:
                return false;
        }
    }

    public static Criteria getUnaryInstance(CriteriaType type, @NonNull String subject, @NonNull List<Object> values) {
        Assert.isTrue(isUnaryOperation(type), "type should be Unary operation");

        final Criteria criteria = new Criteria(type);

        criteria.subject = subject;
        criteria.subjectValues = values;

        return criteria;
    }

    public static Criteria getBinaryInstance(CriteriaType type, @NonNull Criteria left, @NonNull Criteria right) {
        Assert.isTrue(isBinaryOperation(type), "type should be Binary operation");

        final Criteria criteria = new Criteria(type);

        criteria.subCriteria.add(left);
        criteria.subCriteria.add(right);

        Assert.isTrue(criteria.getSubCriteria().size() == 2, "Binary should contains 2 subCriteria");

        return criteria;
    }
}
