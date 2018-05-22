package br.utfpr.tdd.ex1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author andreendo
 */
public class EscritorCSVTest {
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Test
    public void testEscrita() throws IOException {
        File tempFolder = folder.newFolder();
        EscritorCSV escritor = new EscritorCSV();
        escritor.setArquivoSaida(tempFolder.getAbsolutePath() + "/saida.csv");
        escritor.escrever("1234", "Nome do aluno1", 7.555, "aprovado");
        escritor.escrever("12345", "Nome do aluno3", 1.43, "reprovado por falta");
        
        File csvFileCreated = new File(tempFolder.getAbsolutePath() + "/saida.csv");
        assertTrue( csvFileCreated.exists() );
        
        List<String> lines = FileUtils.readLines(csvFileCreated, "UTF-8");
        assertEquals(3, lines.size());
        assertEquals("RA,Nome,NF,Situacao", lines.get(0));
        assertEquals("1234,Nome do aluno1,7.5,aprovado", lines.get(1));
        assertEquals("12345,Nome do aluno3,1.4,reprovado por falta", lines.get(2));
    }
}
