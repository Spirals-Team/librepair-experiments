package com.hedvig.productPricing.service.web.dto;


public enum InsuranceCompaniesSE {
    LANSFORSAKRINGAR,
    IF,
    FOLKSAM,
    TRYGG_HANSA,
    MODERNA,
    ICA,
    GJENSIDIGE,
    VARDIA,
    OTHER;

    public static InsuranceCompaniesSE create(final String name) {
        switch (name) {
            case "if":
                return IF;
            case "Folksam":
                return FOLKSAM;
            case "Trygg-Hansa":
                return TRYGG_HANSA;
            case "Länsförsäkringar":
                return LANSFORSAKRINGAR;
            case "Moderna":
                return MODERNA;
            case "ICA":
                return ICA;
            case "Gjensidige":
                return GJENSIDIGE;
            case "Vardia":
                return VARDIA;
            default:
                return OTHER;
        }
    }
}
