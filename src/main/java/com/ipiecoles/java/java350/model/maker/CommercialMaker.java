package com.ipiecoles.java.java350.model.maker;

import com.ipiecoles.java.java350.model.builder.CommercialBuilder;
import org.joda.time.LocalDate;

public class CommercialMaker {


    public static CommercialBuilder aCommercial(){
        return CommercialBuilder.aCommercial()
            .withNom("Durand")
            .withPrenom("Pierre")
            .withMatricule("C12345")
            .withDateEmbauche(new LocalDate())
            .withSalaire(1500d)
            .withCaAnnuel(15000d)
            .withPerformance(150);
    }


}
