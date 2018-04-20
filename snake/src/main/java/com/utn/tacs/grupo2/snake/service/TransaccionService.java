package com.utn.tacs.grupo2.snake.service;

import com.utn.tacs.grupo2.snake.domain.Transaccion;
import com.utn.tacs.grupo2.snake.repository.MonedaRepository;
import com.utn.tacs.grupo2.snake.repository.TransaccionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final MonedaRepository monedaRepository;
    private final TransaccionRepository transaccionRepository;

    public Transaccion registrar(Transaccion transaccion) {

        transaccion.setCotizacion(monedaRepository.obtener(transaccion.getMonedaNombre()).getCotizacionDolar());
        transaccion.setFecha(LocalDateTime.now());

        return transaccionRepository.save(transaccion);

    }

}
