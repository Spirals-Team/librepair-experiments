/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tacs.grupo2.snake.telegram;

/**
 *
 * @author fiok
 */
public interface OperacionDeTelegram {
    
    public static final String OPERACIONES = "Operaciones disponibles: \n/monedas: lista de monedas, \n/comprar b 10: Compra 10 Bitcoins\n/vender e 10: Vende 10 Ethereum\n/precio b: Precio(Cotizacion) del Bitcoin";
    
    public String getResultado(PartesMensajeTelegram parametros);
}
