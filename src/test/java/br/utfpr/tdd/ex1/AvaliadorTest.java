package br.utfpr.tdd.ex1;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;


/**
 *
 * @author andreendo
 */
public class AvaliadorTest {
    
    Avaliador avaliador;
    Aluno alunoSpy;
    
    @Before
    public void before() {
        avaliador = new Avaliador();
        avaliador.setLimiteFaltas(15);
        alunoSpy = spy( new Aluno("1234", "Joao da Silva") );    
    }
    
    @Test
    public void testAlunoAprovado() {
        alunoSpy.setFaltas(0);
        when(alunoSpy.getNF()).thenReturn(7.0);
        
        assertEquals("aprovado", avaliador.getStatusFinal(alunoSpy));
    }
    
    @Test
    public void testAlunoReprovadoPorFalta() {
        alunoSpy.setFaltas(17);
        when(alunoSpy.getNF()).thenReturn(7.0);
        
        assertEquals("reprovado por falta", avaliador.getStatusFinal(alunoSpy));
    }    
    
    @Test
    public void testAlunoReprovadoPorNota() {
        alunoSpy.setFaltas(0);
        when(alunoSpy.getNF()).thenReturn(5.5);
        
        assertEquals("reprovado por nota", avaliador.getStatusFinal(alunoSpy));
    }
    
    @Test
    public void testAlunoReprovadoPorNotaEFalta() {
        alunoSpy.setFaltas(17);
        when(alunoSpy.getNF()).thenReturn(5.5);
        
        assertEquals("reprovado por nota e falta", avaliador.getStatusFinal(alunoSpy));
    }    
}
