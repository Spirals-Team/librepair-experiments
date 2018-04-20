package com.utn.tacs.grupo2.snake.service;

import com.utn.tacs.grupo2.snake.SnakeApplicationTests;
import com.utn.tacs.grupo2.snake.domain.Billetera;
import com.utn.tacs.grupo2.snake.domain.TipoTransaccion;
import com.utn.tacs.grupo2.snake.domain.Transaccion;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class TransaccionServiceTest extends SnakeApplicationTests {

    //autowired para que spring nos provea una instancia del service
    @Autowired
    private TransaccionService transaccionService;

    /**
     * Nomenclatura de los test nombreMetodo_contexto_retorno ()
     */
    @Test
    public void registrar_transaccionValida_transaccion() throws IOException {

        String cotizacionBitcoinResponse = obtenerContenidoArchivo("jsons/response_cotizacionBitcoin.json");

        //set up
        mockRestServiceServer.expect(requestTo("https://api.coinmarketcap.com/v1/ticker/bitcoin"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(cotizacionBitcoinResponse, MediaType.APPLICATION_JSON));

        Transaccion transaccion = new Transaccion();
        transaccion.setBilletera(new Billetera(1L, "bitcoin", null, BigDecimal.ONE));
        transaccion.setMonedaNombre("bitcoin");
        transaccion.setCantidad(BigDecimal.TEN);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setTipo(TipoTransaccion.COMPRA);

        // ejercitamos
        transaccionService.registrar(transaccion);

        // validamos
        assertThat(transaccion).isNotNull();
        assertThat(transaccion.getId()).isNotNull();
        assertThat(transaccion.getCotizacion()).isNotNull();
        assertThat(transaccion.getCotizacion()).isEqualTo(new BigDecimal("8503.46"));

    }

}
