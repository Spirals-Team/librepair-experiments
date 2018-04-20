package com.utn.tacs.grupo2.snake.controller;

import com.utn.tacs.grupo2.snake.domain.Billetera;
import com.utn.tacs.grupo2.snake.domain.TipoTransaccion;
import com.utn.tacs.grupo2.snake.domain.Transaccion;
import com.utn.tacs.grupo2.snake.service.TransaccionService;
import com.utn.tacs.grupo2.snake.vo.TransaccionVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransaccionRestController {

    private final TransaccionService transaccionService;

    @PostMapping("/transacciones")
    public Transaccion realizar(@RequestBody Transaccion transaccion) {

        return transaccionService.registrar(transaccion);
    }

    @GetMapping("/transacciones/usuarios/{idUsuario}/monedas/{moneda}")
    public List<TransaccionVo> obtenerTodas(@PathVariable Long idUsuario, @PathVariable String moneda) {
        List<TransaccionVo> transacciones = new ArrayList<>();
        transacciones.add(new TransaccionVo(new Transaccion(1L, moneda, new Billetera(), LocalDateTime.now(), new BigDecimal("33.0"), new BigDecimal("3.55"), TipoTransaccion.COMPRA)));
        transacciones.add(new TransaccionVo(new Transaccion(2L, moneda, new Billetera(), LocalDateTime.now(), new BigDecimal("20.0"), new BigDecimal("3.55"), TipoTransaccion.VENTA)));
        transacciones.add(new TransaccionVo(new Transaccion(3L, moneda, new Billetera(), LocalDateTime.now(), new BigDecimal("15.0"), new BigDecimal("3.55"), TipoTransaccion.COMPRA)));

        return transacciones;
    }
}
