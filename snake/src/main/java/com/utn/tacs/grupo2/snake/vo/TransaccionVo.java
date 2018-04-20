package com.utn.tacs.grupo2.snake.vo;

import com.utn.tacs.grupo2.snake.domain.Transaccion;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class TransaccionVo extends ResourceSupport {

    private MonedaVo moneda;
    private LocalDateTime fecha;
    private BigDecimal cantidad;
    private BigDecimal cotizacion;
    private String tipo;

    public TransaccionVo(Transaccion transaccion) {
        this.moneda = new MonedaVo(transaccion.getMonedaNombre());
        this.fecha = transaccion.getFecha();
        this.cantidad = transaccion.getCantidad();
        this.cotizacion = transaccion.getCotizacion();
        this.tipo = transaccion.getTipo().toString();

    }

}
