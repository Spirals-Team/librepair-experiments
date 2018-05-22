package br.utfpr.tdd.ex1;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author andreendo
 */
public class LeitorCSVTest {
    
    @Test
    public void testCSVBemFormado() {
        String arqCsv01 = "./res/csv01.csv";
        LeitorCSV leitor = new LeitorCSV();
        List<Aluno> alunos = leitor.getAlunosDe(arqCsv01);
        assertEquals(3, alunos.size());

        assertEquals("1234", alunos.get(0).getRA());
        assertEquals("Joao Da Silva", alunos.get(0).getNome());
        assertEquals(5, alunos.get(0).getFaltas());
        assertEquals(6, alunos.get(0).getNumeroAtividadesPraticas());
        assertEquals((5.0+7.0+1.0+0+0+10)/6.0, alunos.get(0).getNAP(), 0.0000001);
        assertEquals(7.0, alunos.get(0).getNotaProjeto(), 0.0000001);
        assertTrue(alunos.get(0).getNotaRAA() < 0);
        
        assertEquals("4567", alunos.get(1).getRA());
        assertEquals("Maria Souza", alunos.get(1).getNome());
        assertEquals(0, alunos.get(1).getFaltas());
        assertEquals(6, alunos.get(1).getNumeroAtividadesPraticas());
        assertEquals((10+10+10+0+10+10.0)/6.0, alunos.get(1).getNAP(), 0.0000001);
        assertEquals(9.5, alunos.get(1).getNotaProjeto(), 0.0000001);
        assertTrue(alunos.get(1).getNotaRAA() < 0);
        
        assertEquals("4568", alunos.get(2).getRA());
        assertEquals("Maria Souza Prima", alunos.get(2).getNome());
        assertEquals(2, alunos.get(2).getFaltas());
        assertEquals(6, alunos.get(2).getNumeroAtividadesPraticas());
        assertEquals((0+0+5+0+5+10)/6.0, alunos.get(2).getNAP(), 0.0000001);
        assertEquals(5.5, alunos.get(2).getNotaProjeto(), 0.0000001);
        assertEquals(6.2, alunos.get(2).getNotaRAA(), 0.0000001);
    }

    @Test
    public void testCSVNaoExiste() {
        String arqCsv01 = "./res/csv02-naoexiste.csv";
        LeitorCSV leitor = new LeitorCSV();
        List<Aluno> alunos = leitor.getAlunosDe(arqCsv01);
        assertEquals(1, alunos.size());
        assertTrue( alunos.get(0).getNome().startsWith("ERROR READING FILE:") );
    }
    
    @Test
    public void testCSVOutroFormato() {
        String arqCsv01 = "./res/csv02-bad.csv";
        LeitorCSV leitor = new LeitorCSV();
        List<Aluno> alunos = leitor.getAlunosDe(arqCsv01);
        assertEquals(1, alunos.size());
        assertTrue( alunos.get(0).getNome().startsWith("ERROR READING FILE:") );
    }

    @Test
    public void testCSVOutroTipoDeArquivo() {
        String arqCsv01 = "./res/someotherFile.txt";
        LeitorCSV leitor = new LeitorCSV();
        List<Aluno> alunos = leitor.getAlunosDe(arqCsv01);
        assertEquals(1, alunos.size());
        assertTrue( alunos.get(0).getNome().startsWith("ERROR READING FILE:") );
    }
}
