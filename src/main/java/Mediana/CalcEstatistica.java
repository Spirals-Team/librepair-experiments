/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mediana;

/**
 *
 * @author renat
 */
public class CalcEstatistica {

    public double getMediana(double[] v) {
        int meio = v.length / 2;
        if (v.length % 2 == 1) {
            return v[meio];
        } else {
            return (v[meio] + v[meio - 1]) / 2;
        }
    }

    public double getMedia(double[] v) {
        double res = 0;
        for (int i = 0; i < v.length; i++) {
            res += v[i];
        }
        return res / v.length;
    }

    public double getMenor(double[] v) {
        double menor = v[0];
        for (int i = 0; i < v.length; i++) {
            menor = menor > v[i] ? v[i] : menor;
        }
        return menor;
    }

    public double getMaior(double[] v) {
        double maior = v[0];
        for (int i = 0; i < v.length; i++) {
            maior = maior < v[i] ? v[i] : maior;
        }
        return maior;
    }

    public double getAcimaMedia(double[] v) {
        double acima = 0;
        double med = getMedia(v);
        for (int i = 0; i < v.length; i++) {
            if (v[i] > med) {
                acima++;
            }
        }
        return acima;
    }

    public double getAbaixoMedia(double[] v) {
        double abaixo = 0;
        double med = getMedia(v);
        for (int i = 0; i < v.length; i++) {
            if (v[i] < med) {
                abaixo++;
            }
        }
        return abaixo;
    }

    public double getDesvioPadrao(double[] v) {
        int tamanhoVetor = v.length;
        double soma = 0;
        double soma2 = 0d;
        double desvioPadrao;
        for (int i = 1; i <= tamanhoVetor; i++) {
            soma = soma + i;
            soma2 = soma2 + elevarAoQuadrado(i);
        }
        desvioPadrao = tirarRaizQuadrada(
                (soma2 - elevarAoQuadrado(soma) / tamanhoVetor)
                / tamanhoVetor - 1);
        return desvioPadrao;
    }

    private double elevarAoQuadrado(double valor) {
        return Math.pow(valor, 2);
    }

    private double tirarRaizQuadrada(double valor) {
        return Math.sqrt(valor);
    }
}
