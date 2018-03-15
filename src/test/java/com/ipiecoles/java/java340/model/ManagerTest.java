package com.ipiecoles.java.java340.model;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ManagerTest {

    @Test
    public void augmenterSalaireWith5And1Technicien(){
        //Given
        Manager manager = new Manager();
        Set<Technicien> equipe = new HashSet<Technicien>();
        Technicien tech = new Technicien();
        equipe.add(tech);
        manager.setEquipe(equipe);
        manager.augmenterSalaire(0.05);       

        //When
        Double result = manager.getSalaire();
        Set<Technicien> result2 = manager.getEquipe();
        
        //Then
        Assertions.assertThat(result).isEqualTo(1554.2835);
        Assertions.assertThat(result2.size()).isEqualTo(1);
        Assertions.assertThat(result2).contains(tech);
        for (Technicien t : result2) {
       	 Assertions.assertThat(t.getSalaire()).isEqualTo(1554.2835);
        }
    }
    
//    public void augmenterSalaireWithNull(){
//        //Given
//        Manager manager = new Manager();
//        Set<Technicien> equipe = new HashSet<Technicien>();
//        Technicien tech = new Technicien();
//        equipe.add(tech);
//        manager.setEquipe(equipe);
//        manager.augmenterSalaire(null);
//        
//        //When
//        Double result = manager.getSalaire();
//        Set<Technicien> result2 = manager.getEquipe();
//        
//        //Then
//        Assertions.assertThat(result).isEqualTo(Entreprise.SALAIRE_BASE);
//        Assertions.assertThat(result2.size()).isEqualTo(1);
//        Assertions.assertThat(result2).contains(tech);
//        for (Technicien t : result2) {
//       	 Assertions.assertThat(t.getSalaire()).isEqualTo(Entreprise.SALAIRE_BASE);
//        }
//    }
    
}
