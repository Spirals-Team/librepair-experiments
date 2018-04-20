package com.utn.tacs.grupo2.snake.vo;

import com.utn.tacs.grupo2.snake.controller.TransaccionRestController;
import com.utn.tacs.grupo2.snake.controller.UsuarioRestController;
import com.utn.tacs.grupo2.snake.domain.Billetera;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Data
public class BilleteraVo extends ResourceSupport {

    private MonedaVo moneda;
    private BigDecimal cantidad;

    public BilleteraVo(Billetera billetera, Long usuarioId) {
        this.moneda = new MonedaVo(billetera.getMonedaNombre());
        this.cantidad = billetera.getCantidad();

        this.add(linkTo(methodOn(TransaccionRestController.class).obtenerTodas(usuarioId, billetera.getMonedaNombre())).withRel("transacciones"));
        this.add(linkTo(methodOn(UsuarioRestController.class).obtener(usuarioId)).withRel("usuario"));

    }

}
