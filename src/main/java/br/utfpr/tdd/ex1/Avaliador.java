package br.utfpr.tdd.ex1;

/**
 *
 * @author andreendo
 */
public class Avaliador {
    int limiteFaltas;
    
    public void setLimiteFaltas(int limite) {
        limiteFaltas = limite;
    }

    public String getStatusFinal(Aluno aluno) {
        boolean reprovadoPorNota = aluno.getNF() < 6.0;
        boolean reprovadoPorFalta = aluno.getFaltas() > limiteFaltas;
        
        String status = "aprovado";
        if(reprovadoPorNota) {
            status = "reprovado por nota";
            if(reprovadoPorFalta)
                status = status + " e falta";
        }
        else if(reprovadoPorFalta)
            status = "reprovado por falta";
        
        return status;
    }    
    
}
