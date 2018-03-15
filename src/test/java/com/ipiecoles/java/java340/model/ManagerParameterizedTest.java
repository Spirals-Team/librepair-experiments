package com.ipiecoles.java.java340.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RunWith(value = Parameterized.class)
public class ManagerParameterizedTest {

    @Parameterized.Parameter(value = 0)
    public Double salaire;

    @Parameterized.Parameter(value = 1)
    public Double expectedSalaire;

    @Parameterized.Parameters(name = "salaire {0} set {1}")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {0d,0d},
                {1000d,1300d},
                {2000d,2600d}               
        });
    }

    @Test
    public void testSetSalaire(){
        //Given
    	Manager manager = new Manager();
    	/*Manager manager2 = new Manager();
    	Set<Technicien> equipe = new HashSet<Technicien>();
        Technicien tech = new Technicien();
        equipe.add(tech);
        manager.setEquipe(equipe);*/
    	
        //When
        manager.setSalaire(salaire);
        
        //Then
        Assertions.assertThat(manager.getSalaire()).isEqualTo(expectedSalaire);
        //Assertions.assertThat(manager2.getSalaire()).isEqualTo(expectedSalaire);
    }
}
