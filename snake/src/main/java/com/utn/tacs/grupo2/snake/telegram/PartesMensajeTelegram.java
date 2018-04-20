/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tacs.grupo2.snake.telegram;

import org.telegram.telegrambots.api.objects.Update;

/**
 *
 * @author fiok
 */
public class PartesMensajeTelegram {
    private int cantidad = 0;
    private String moneda = "";
    
   private String texto;
   private String[] mensajeSeparado;
   private String mensajeSinBarra;
   public PartesMensajeTelegram(Update nuevoMensaje){     
        texto = nuevoMensaje.getMessage().getText();   
        mensajeSinBarra = texto.replace("/", "");
        mensajeSeparado = mensajeSinBarra.split(" ");
             
        if(mensajeSeparado.length>1)
            moneda= mensajeSeparado[1].substring(0, 1);
        if(mensajeSeparado.length>2)
            cantidad = Integer.parseInt(mensajeSeparado[2]);

    }
    public String getTexto(){
        return texto;
    }

    public String getOperacionString(){
        return mensajeSeparado[0].substring(0, 1);
    }
    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the moneda
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * @param moneda the moneda to set
     */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
