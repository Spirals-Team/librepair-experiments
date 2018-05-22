package br.utfpr.tdd.ex1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.mockito.Mockito.*;

/**
 *
 * @author andreendo
 */
public class ProcessadorDeNotasTest {
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    File tempFolder;
    
    @Before
    public void before() throws IOException {
        tempFolder = folder.newFolder();
    }
    
    @Test
    public void testProcessar() {
        LeitorCSV leitorMock = mock(LeitorCSV.class);
        when(leitorMock.getAlunosDe("./res/algumArq.csv"))
                .thenReturn( getAlunos() );
        
        EscritorCSV escritorMock = mock(EscritorCSV.class);
        
        Avaliador avaliador = new Avaliador();
        avaliador.setLimiteFaltas(15);
        
        ProcessadorDeNotas p = new ProcessadorDeNotas();
        p.setLeitor(leitorMock);
        p.setEscritor(escritorMock);
        p.setAvaliador(avaliador);
        
        p.processar("./res/algumArq.csv", tempFolder.getAbsolutePath() + "/algumArqSaida.csv");        
        
        verify(escritorMock).setArquivoSaida(tempFolder.getAbsolutePath() + "/algumArqSaida.csv");
        verify(escritorMock).escrever("1234", "aaa bbb", (6.0 + (10.0 + 5.0)/2.0)/2, "aprovado");
        verify(escritorMock).escrever("12345", "aaavv bbbvv", (1.0 + (10.0 + 5.0)/2.0 + 5.7)/3, "reprovado por nota e falta");
    }

    @Test
    public void testProcessarErro() {
        LeitorCSV leitorMock = mock(LeitorCSV.class);
        when(leitorMock.getAlunosDe("./res/algumArq.csv"))
                .thenReturn( getErro() );
        
        EscritorCSV escritorMock = mock(EscritorCSV.class);
        
        Avaliador avaliador = new Avaliador();
        avaliador.setLimiteFaltas(15);
        
        ProcessadorDeNotas p = new ProcessadorDeNotas();
        p.setLeitor(leitorMock);
        p.setEscritor(escritorMock);
        p.setAvaliador(avaliador);
        
        p.processar("./res/algumArq.csv", tempFolder.getAbsolutePath() + "/algumArqSaida.csv");        
        
        verify(escritorMock).setArquivoSaida(tempFolder.getAbsolutePath() + "/algumArqSaida.csv");
        verify(escritorMock).escrever("ERROR READING FILE: aaaaa", "ERROR READING FILE: aaaaa", 0.0, "ERROR READING FILE: aaaaa");
    }    
    
    private List<Aluno> getAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        Aluno a1 = new Aluno("1234", "aaa bbb");
        a1.setFaltas(0);
        a1.setNotaProjeto(6.0);
        a1.addNotaAtividadePratica(10.0);
        a1.addNotaAtividadePratica(5.0);
        alunos.add(a1);

        Aluno a2 = new Aluno("12345", "aaavv bbbvv");
        a2.setFaltas(17);
        a2.setNotaProjeto(1.0);
        a2.addNotaAtividadePratica(10.0);
        a2.addNotaAtividadePratica(5.0);
        a2.setNotaRAA(5.7);
        alunos.add(a2);
        
        return alunos;
    }
    
    private List<Aluno> getErro() {
        List<Aluno> alunos = new ArrayList<>();
        Aluno a1 = new Aluno("ERROR READING FILE: aaaaa", "ERROR READING FILE: aaaaa");
        alunos.add(a1);
        
        return alunos;
    }
    
}
