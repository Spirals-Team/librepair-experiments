package com.api.campeonatofutebol.controller;

import com.api.campeonatofutebol.service.TimeService;
import com.api.campeonatofutebol.model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/api/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping ("/{id}")
    public Time buscarPor(@PathVariable Integer id) {
        return timeService.buscarPor(id);
    }

    @PostMapping
    public ResponseEntity<?> cria(@Validated @RequestBody Time time) {

        Time timeSalva = this.timeService.salva(time);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(timeSalva.getId())
                .toUri();

        return ResponseEntity.created(uri).body(timeSalva);
    }

    @GetMapping ("/buscapornome")
    public Time buscaPeloNome(@RequestParam String nome) {
        return timeService.buscarPor(nome);
    }

    @GetMapping ("/listapornome")
    public List<Time> buscarPeloNome(@RequestParam String nome) {
        return timeService.listarPor(nome);
    }

    @GetMapping ("/listatodos")
    public List<Time> listaDeTime() {
        return timeService.todos();
    }

    @DeleteMapping ("/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public @ResponseBody void excluir(@PathVariable Integer id) {
        timeService.excluir(id);
    }


    @PutMapping ("/{id}")
    public ResponseEntity<Time> atualiza(@PathVariable Integer id,
                                            @Validated @RequestBody  Time time) {
        Time timeManager = timeService.atualiza(id, time);

        return ResponseEntity.ok(timeManager);
    }


    @PutMapping ("/{id}/ativo")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void atualizarAtributoAtivo (@PathVariable Integer id, @RequestBody boolean ativo) {
        timeService.atualizarAtributoAtivo(id, ativo);
    }


}
