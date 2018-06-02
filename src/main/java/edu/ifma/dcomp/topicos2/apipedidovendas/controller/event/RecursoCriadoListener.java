package edu.ifma.dcomp.topicos2.apipedidovendas.controller.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {


    @Override
    public void onApplicationEvent(RecursoCriadoEvent eventoRecursoCriado) {
       this.adcionaHeaderLocation( eventoRecursoCriado.getResponse(), eventoRecursoCriado.getId() );
    }


    private void adcionaHeaderLocation(HttpServletResponse response, Integer id) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        response.setHeader("Location", uri.toString() );
    }
}
