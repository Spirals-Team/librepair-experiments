/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tacs.grupo2.snake.telegram;

import java.util.List;
import org.hibernate.sql.Update;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 *
 * @author fiok
 */
public class Bot extends TelegramLongPollingBot {    
    //TODO: Esto deberia estar en un archivo externo y que no se suba al repo
    //BOT DE MARTIN P.
    private final String TOKEN_PROD = "517166907:AAGfamZ2PBV22lQa0308JqbVT-ANcjBVVZo";
    private final String USER_PROD = "fiokaombot";
    
    //BOT DE MARTIN P - DEV
    private final String TOKEN_DESA = "573734744:AAHilpuD8OC9B9FNcBiEwd3URTOtoRNrMjY";
    private final String USER_DESA = "snaketacsbot";
    private String TOKEN;
    private String USER;
    
    public Bot(Boolean estaEnProduccion){
        if(estaEnProduccion){
            TOKEN = TOKEN_PROD;
            USER = USER_PROD;
        }else{
            TOKEN = TOKEN_DESA;
            USER = USER_DESA;
        }            
    }
    

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USER;
    }
    
    @Override
    public void onUpdateReceived(org.telegram.telegrambots.api.objects.Update nuevoMensaje) {        
        // We check if the update has a message and the message has text
        if (nuevoMensaje.hasMessage() && nuevoMensaje.getMessage().hasText()) {            
            try {
                Mensaje mensaje = new Mensaje(nuevoMensaje);
                
                execute(mensaje.returnMessage()); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

   
}
