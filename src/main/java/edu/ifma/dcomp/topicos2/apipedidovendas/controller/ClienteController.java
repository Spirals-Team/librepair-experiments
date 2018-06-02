package edu.ifma.dcomp.topicos2.apipedidovendas.controller;


import edu.ifma.dcomp.topicos2.apipedidovendas.controller.event.RecursoCriadoEvent;
import edu.ifma.dcomp.topicos2.apipedidovendas.model.Cliente;
import edu.ifma.dcomp.topicos2.apipedidovendas.model.Cliente;
import edu.ifma.dcomp.topicos2.apipedidovendas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ApplicationEventPublisher publisher;

   private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listaDeClientes() {
        return clienteService.obterTodosClientes();
    }


   @PostMapping
    public ResponseEntity<?> cria(@Validated @RequestBody Cliente cliente, HttpServletResponse response) {

        Cliente clienteSalvo = clienteService.salva(cliente );
        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId() ));

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualiza(@PathVariable Integer id, @Validated @RequestBody Cliente cliente ) {
        Cliente clienteManager = clienteService.atualiza(id, cliente );
        return ResponseEntity.ok(clienteManager );
    }

}
