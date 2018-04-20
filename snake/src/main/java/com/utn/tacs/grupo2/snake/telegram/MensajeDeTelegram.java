/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tacs.grupo2.snake.telegram;

import com.utn.tacs.grupo2.snake.telegram.operaciones.OperacionDeTelegramPrecio;
import com.utn.tacs.grupo2.snake.telegram.operaciones.OperacionDeTelegramCompra;
import com.utn.tacs.grupo2.snake.telegram.operaciones.OperacionDeTelegramInvalida;
import com.utn.tacs.grupo2.snake.telegram.operaciones.OperacionDeTelegramVenta;
import com.utn.tacs.grupo2.snake.telegram.operaciones.OperacionDeTelegramMonedas;
import org.telegram.telegrambots.api.objects.Update;

/**
 *
 * @author fiok
 */
public class MensajeDeTelegram {
   private final Update updateObject;
   private PartesMensajeTelegram partes  = null;
   
    public MensajeDeTelegram(Update nuevoMensaje){
        this.updateObject = nuevoMensaje;
        partes = new PartesMensajeTelegram(nuevoMensaje);                
    }
    public Update getUpdateObject(){
        return updateObject;
    }
    
    public PartesMensajeTelegram getPartes(){
        return partes;
    }
    
    public boolean esUnaOperacion(){
        return partes.getTexto().startsWith("/");
    }
    
    public OperacionDeTelegram getOperacion(){
        String op = partes.getOperacionString();
        switch(op){
            case "m": return new OperacionDeTelegramMonedas();
            case "c": return new OperacionDeTelegramCompra();
            case "v": return new OperacionDeTelegramVenta();
            case "p": return new OperacionDeTelegramPrecio();            
            default: return new OperacionDeTelegramInvalida();
        }
    }
    
    
}
