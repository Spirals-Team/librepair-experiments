package br.utfpr.tdd.ex1;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author andreendo
 */
public class AlunoTest {
    
    @Test
    public void testNotaAtividadesTresNotas() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        aluno.addNotaAtividadePratica(10.0);
        aluno.addNotaAtividadePratica(5.0);
        aluno.addNotaAtividadePratica(10.0);
        assertEquals((10.0+5.0+10.0)/3.0, aluno.getNAP(), 0.01);
    }
    
    @Test
    public void testNotaAtividadesSemNota() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        assertEquals(0.0, aluno.getNAP(), 0.01);
    }

    @Test
    public void testNotaFinal() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        
        Aluno alunoSpy = spy(aluno);
        when(alunoSpy.getNAP()).thenReturn(7.0);
        
        alunoSpy.setNotaProjeto(7.0);
        assertEquals((7.0 + 7.0) / 2.0, alunoSpy.getNF(), 0.01);
    }
    
    @Test
    public void testNotaFinalIntegrandoComAtividadesPraticas() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        
        aluno.addNotaAtividadePratica(5.0);
        aluno.addNotaAtividadePratica(10.0);
        aluno.addNotaAtividadePratica(7.0);
        aluno.addNotaAtividadePratica(0.0);
        aluno.addNotaAtividadePratica(1.0);
        
        aluno.setNotaProjeto(7.0);
        
        assertEquals((7.0 + (5.0 + 10.0 + 7.0 + 0.0 + 1.0)/5.0) / 2.0, aluno.getNF(), 0.01);
    }    
    
    @Test
    public void testNotaFinalComRAANaoSalvando() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        
        //4.6 media
        aluno.addNotaAtividadePratica(5.0);
        aluno.addNotaAtividadePratica(10.0);
        aluno.addNotaAtividadePratica(7.0);
        aluno.addNotaAtividadePratica(0.0);
        aluno.addNotaAtividadePratica(1.0);
        double mediaRAA = (5.0 + 10.0 + 7.0 + 0.0 + 1.0)/5.0;
        
        aluno.setNotaProjeto(5.0);
        aluno.setNotaRAA(6.0);
        
        assertEquals((5.0 + 6.0 + mediaRAA) / 3.0, aluno.getNF(), 0.01);
    }
    @Test
     public void testNotaFinalComRAA() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        
        //6.3 media
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        double mediaRAA = (6.0 + 6.0 + 6.0 + 6.0 + 6.0)/6.0;
        
        aluno.setNotaProjeto(5.0);
        aluno.setNotaRAA(8.0);
        
        assertEquals(6.0 , aluno.getNF(), 0.01);
    }
    @Test
     public void testArredondarNotaFinal() {
        Aluno aluno = new Aluno("RA", "Joao Da Silva");
        
        //6.3 media
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        aluno.addNotaAtividadePratica(6.0);
        double mediaRAA = (6.0 + 6.0 + 6.0 + 6.0 + 6.0)/6.0;
        
        aluno.setNotaProjeto(4.0);
        aluno.setNotaRAA(7.8);
        
        assertEquals(6.0 , aluno.getNF(), 0.01);
    }
}
