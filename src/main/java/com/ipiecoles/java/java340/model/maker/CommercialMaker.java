package com.ipiecoles.java.java340.model.maker;

import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.builder.CommercialBuilder;
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