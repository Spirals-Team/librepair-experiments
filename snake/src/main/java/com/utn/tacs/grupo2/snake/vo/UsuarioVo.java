package com.utn.tacs.grupo2.snake.vo;

import com.utn.tacs.grupo2.snake.controller.UsuarioRestController;
import com.utn.tacs.grupo2.snake.domain.Usuario;
import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Data
public class UsuarioVo extends ResourceSupport {

    private Long usuarioId;
    private String user;

    public UsuarioVo(Usuario usuario) {

        this.user = usuario.getUsername();
        this.usuarioId = usuario.getId();

        final Link selfLink = linkTo(methodOn(UsuarioRestController.class).obtener(this.usuarioId)).withRel("self");
        this.add(selfLink);

        Link portfolioLink = linkTo(methodOn(UsuarioRestController.class).obtenerPortfolio(this.usuarioId)).withRel("portfolio");
        this.add(portfolioLink);

    }

}
