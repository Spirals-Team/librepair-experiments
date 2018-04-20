package com.utn.tacs.grupo2.snake.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String monedaNombre;

    @JoinColumn
    @ManyToOne
    private Billetera billetera;

    private LocalDateTime fecha;
    private BigDecimal cantidad;
    private BigDecimal cotizacion;
    private TipoTransaccion tipo;

}
