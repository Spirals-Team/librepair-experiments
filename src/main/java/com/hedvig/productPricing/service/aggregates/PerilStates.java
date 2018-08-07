package com.hedvig.productPricing.service.aggregates;

public enum PerilStates {
    ADD_REQUESTED,
    REMOVE_REQUESTED,
    ADD_PENDING,
    REMOVE_PENDING,
    WAITING_FOR_PAYMENT,
    NOT_COVERED,
    COVERED,
}
