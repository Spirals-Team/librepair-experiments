/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDD01;

import org.junit.Test;
import static org.junit.Assert.*;
import Mediana.*;

/**
 *
 * @author renat
 */
public class EstatisticaTest {

    public EstatisticaTest() {
    }

    @Test
    public void testeVetorOrdenadoImpar() {
        double[] vector = {1, 2, 3, 4, 5};
        
        CalcEstatistica calcula = new CalcEstatistica();
        
        
        double res = calcula.getMediana(vector);
        assertEquals(res, 3, 0.01);
    }
    
    @Test
    public void testeVetorOrdenadoPar() {
        double[] vector = {1, 2, 3, 5, 5, 6};
        
        CalcEstatistica calcula = new CalcEstatistica();
        
        double res = calcula.getMediana(vector);
        assertEquals(res, 4, 0.01);
                
    }
    
    @Test
    public void testeMedia() {
        double[] vector = {1, 2, 3, 6};

        CalcEstatistica calcula = new CalcEstatistica();

        double res = calcula.getMedia(vector);
        assertEquals(res, 3, 0.01); 
    }
    @Test
    public void testeMenor() {
        double[] vector = {1, 2, 3, 6};

        CalcEstatistica calcula = new CalcEstatistica();

        double res = calcula.getMenor(vector);
        assertEquals(res, 1, 0.01);
    }
    @Test
    public void testeMaior() {
        double[] vector = {1, 2, 3, 6};

        CalcEstatistica calcula = new CalcEstatistica();

        double res = calcula.getMaior(vector);
        assertEquals(res, 6, 0.01);
    }
    @Test
    public void testeAbaixoMedia() {
        double[] vector = {1, 2, 3, 6};

        CalcEstatistica calcula = new CalcEstatistica();

        double res = calcula.getAbaixoMedia(vector);
        assertEquals(res, 2, 0.01);
    }
    @Test
    public void testeAcimaMedia() {
        double[] vector = {1, 2, 3, 6};

        CalcEstatistica calcula = new CalcEstatistica();

        double res = calcula.getAcimaMedia(vector);
        assertEquals(res, 1, 0.01);
    }
    @Test
    public void testeDesvioPadrao() {
        double[] vector = {1, 2, 3, 6};

        CalcEstatistica calcula = new CalcEstatistica();

        double res = calcula.getDesvioPadrao(vector);
        assertEquals(res, 1.870829f, 0.01);
    }
}
