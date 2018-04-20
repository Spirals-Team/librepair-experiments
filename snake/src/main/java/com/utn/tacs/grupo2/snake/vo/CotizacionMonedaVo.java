/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tacs.grupo2.snake.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * La cotizacion de la moneda no se persiste sino que se consulta en
 * https://coinmarketcap.com/api/.
 */
public class CotizacionMonedaVo extends ResourceSupport {

    private String monedaNombre;
    private String symbol;
    private BigDecimal cotizacionDolar;

    @JsonProperty("id")
    public void setMonedaNombre(String monedaNombre) {
        this.monedaNombre = monedaNombre;
    }

    @JsonProperty("price_usd")
    public void setCotizacionDolar(BigDecimal cotizacionDolar) {
        this.cotizacionDolar = cotizacionDolar;
    }

    @JsonProperty("monedaNombre")
    public String getMonedaNombre() {
        return this.monedaNombre;
    }

    @JsonProperty("cotizacionDolar")
    public BigDecimal getCotizacionDolar() {
        return this.cotizacionDolar;
    }
}
