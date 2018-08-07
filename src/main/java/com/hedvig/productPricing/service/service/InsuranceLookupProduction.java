package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.web.dto.InsuranceCompaniesSE;

public class InsuranceLookupProduction implements InsuranceLookup {



    @Override
    public String extractEmailAddress(InsuranceCompaniesSE currentInsurer) {

        //TODO Replace
        //return "johan@hedvig.com";

        switch (currentInsurer) {
            case IF:
            {
                return "kundservice@if.se";
            }
            case ICA:
            {
                return "forsakring@ica.se";
            }
            case VARDIA: {
                return "kundservice@vardia.se ";
            }
            case FOLKSAM: {
                return "annullationer-sak@folksam.se";
            }
            case MODERNA: {
                return "avslut@modernaforsakringar.se";
            }
            case GJENSIDIGE: {
                return "info@gjensidige.se";
            }
            case TRYGG_HANSA: {
                return "annullation-privat@trygghansa.se";
            }
            case LANSFORSAKRINGAR: {
                return "stockholm@lansforsakringar.se";
            }
            case OTHER:
            default:{
                return null;
            }
        }
    }
}
