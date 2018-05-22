/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.tdd.ex1;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mussi
 */
public class NumeroTeste {

    public NumeroTeste() {
    }

    @Test
    public void vetorNmeroMedianaTest1() {
        int[] n = {5, 8, 9, 10, 12, 13};
        assertEquals((n[2] + n[3]) / 2, 9.5, 0.01);
    }

    @Test
    public void vetorNmeroTest2() {
        int[] n = {5, 8, 9, 10, 12, 13};
        assertEquals((n[0] + n[1] + n[2] + n[3] + n[4] + n[5]) / 6, 9.5, 0.01);//media
        assertEquals(n[0], 5);//menor
        assertEquals(n[5], 13);//maior
        assertEquals(n[3], 10);//acima
        assertEquals(n[4], 12);
        assertEquals(n[5], 13);
        assertEquals(n[0], 5);//abixo
        assertEquals(n[1], 8);
        assertEquals(n[2], 9);
    }

    @Test
    public void vetorNmeroTest3() {
        int[] n = {5, 8, 9, 10, 12, 13};
        double media = (n[0] + n[1] + n[2] + n[3] + n[4] + n[5]) / 6;
        double varianca = Math.sqrt((((n[0] - media) * (n[0] - media))
                + ((n[1] - media) * (n[1] - media))
                + ((n[2] - media) * (n[2] - media))
                + ((n[3] - media) * (n[3] - media))
                + ((n[4] - media) * (n[4] - media))
                + ((n[5] - media) * (n[5] - media))) / (n.length - 1));

        assertEquals(varianca, 0.4690, 0.01);//maiores
    }

    @Test
    public void vetorNmeroTest4() {
        int[] n = {5, 8, 9, 10, 12, 13};
        assertEquals(n[3], 10);//maiores
        assertEquals(n[4], 12);
        assertEquals(n[5], 13);
        assertEquals(n[0], 5);//menores
        assertEquals(n[1], 8);
        assertEquals(n[2], 9);
    }
}
