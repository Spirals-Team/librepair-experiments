package br.utfpr.tdd.ex1;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author andreendo
 */
class LeitorCSV {

    List<Aluno> getAlunosDe(String arqCsv) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        try ( Reader reader = Files.newBufferedReader(Paths.get(arqCsv)) )
        {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withDelimiter(',')
                        .withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();        
            for (CSVRecord rec : csvRecords) {
                Aluno a = new Aluno(rec.get("RA"), rec.get("Nome"));
                a.setFaltas( Integer.parseInt(rec.get("NumeroDeFaltas")) );
                String[] naps = rec.get("NotasAtividadesPraticasSeparadas").split(";");
                for(String nap : naps) {
                    a.addNotaAtividadePratica(Double.parseDouble(nap));
                }
                
                a.setNotaProjeto( Double.parseDouble(rec.get("NotaDoProjeto")) );
                
                if(!rec.get("NotaDaRAA").equals("-"))
                    a.setNotaRAA(Double.parseDouble(rec.get("NotaDaRAA")) );
                
                alunos.add(a);
            }
        
            return alunos;
        } 
        catch(IOException|IllegalArgumentException e){
            String error = "ERROR READING FILE: " + e.getMessage();
            alunos.add(new Aluno(error, error));
            return alunos;
        }
    }
    
}
