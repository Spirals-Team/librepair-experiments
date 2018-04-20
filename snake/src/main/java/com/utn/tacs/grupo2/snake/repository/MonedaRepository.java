package com.utn.tacs.grupo2.snake.repository;

import com.utn.tacs.grupo2.snake.vo.CotizacionMonedaVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MonedaRepository {

    private final RestTemplate restTemplate;

    public CotizacionMonedaVo obtener(String monedaNombre) {

        CotizacionMonedaVo[] cotizacionMonedaVo = restTemplate.getForObject("https://api.coinmarketcap.com/v1/ticker/" + monedaNombre, CotizacionMonedaVo[].class);

        return cotizacionMonedaVo[0];
    }

}
