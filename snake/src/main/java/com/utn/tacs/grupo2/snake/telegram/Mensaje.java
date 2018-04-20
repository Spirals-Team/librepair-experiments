/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tacs.grupo2.snake.telegram;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

/**
 *
 * @author fiok
 */
public class Mensaje {

    MensajeDeTelegram nuevoMensaje;

    public Mensaje(Update nuevoMensaje) {
        this.nuevoMensaje = new MensajeDeTelegram(nuevoMensaje);
    }

    private String getRespuesta() {
        String resultado = "";

        try {

            if (nuevoMensaje.esUnaOperacion()) {
                resultado = nuevoMensaje.getOperacion().getResultado(nuevoMensaje.getPartes());
            } else {
                resultado = "Hola**" + nuevoMensaje.getPartes().getTexto() + ": \n" + OperacionDeTelegram.OPERACIONES;
            }

        } catch (Exception e) {
            //TODO: Cuando este estable no mostrar el error
            resultado = nuevoMensaje.getPartes().getTexto() + ":Rompiste todo!:" + e.getMessage() + OperacionDeTelegram.OPERACIONES;
        }

        return resultado;
    }

    public SendMessage returnMessage() {
        SendMessage message = new SendMessage()
                .setChatId(nuevoMensaje.getUpdateObject().getMessage().getChatId())
                .setText(getRespuesta());
        return message;
    }
}
