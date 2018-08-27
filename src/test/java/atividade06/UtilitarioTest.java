package atividade06;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CINTIA
 */
public class UtilitarioTest {

    public UtilitarioTest() {
    }

    /**
     * - Depois de todas as configurações, faça ao menos 4 commits. Envie um txt
     * com o link do projeto do Travis-ci e Sonar. * Considere um vetor de
     * double com entrada. Valide para valor nulo e vazio.
     **/
    
    /** (i) Encontre a mediana dos números.**/
    @Test
    public void testaVetorOrdenadoImpar() throws Exception {
        double v[] = {1, 2, 3, 4, 5};
        Utilitario m = new Utilitario();
        double res = m.VerificaEstatistica(v);
        assertEquals(3.0, res, 0.01);
    }

    /**
     * (ii) Encontre a média, o menor valor, o maior valor, o número de valores
     * acima da média e o o número de valores abaixo da média.*
     */
    
    @Test
    public void testaVetor() throws Exception {
        double v[] = {3, 5, 8, 9, 5, 1, 4};
        Utilitario m = new Utilitario();
        double res = m.VerificaEstatistica(v);
        assertEquals(5, res, 0.01);
        assertEquals(1, res, 0.01);
        assertEquals(9, res, 0.01);
        assertEquals(3, res, 0.01);
        assertEquals(2, res, 0.01);   
    }
    
    /**
     * (iii) Encontre o desvio padrão dos números.*
     */
    
    /**(iv) Encontre os n maiores e os n menores número do vetor, assumindo que n>=1.**/
}
