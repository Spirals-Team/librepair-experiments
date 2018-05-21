package com.hipermidia.campeonato.controller;

import com.hipermidia.campeonato.model.Jogador;
import com.hipermidia.campeonato.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    @Autowired
    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @PostMapping
    public ResponseEntity<?> cria(@Validated @RequestBody Jogador jogador) {
        Jogador jogadorSalva = jogadorService.salva(jogador);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{/id}")
                .buildAndExpand(jogadorSalva.getId())
                .toUri();

        return ResponseEntity.created(uri).body(jogadorSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void excluir(@PathVariable Integer id) {
        jogadorService.excluir(id);
    }

    @GetMapping
    public List<Jogador> listaDeJogadores() {
        return jogadorService.todos();
    }

    @GetMapping("/{id}")
    public Jogador buscarPor(@PathVariable Integer id) {
        return jogadorService.buscaPor(id);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Jogador> atualiza(@PathVariable Integer id,
                                            @Validated @RequestBody Jogador jogador) {
        Jogador jogadorManager = jogadorService.atualiza(id, jogador);
        return ResponseEntity.ok(jogadorManager);

    }

    @PutMapping ("/{id}/ativo")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void atualizaAtributoAtivo(@PathVariable Integer id, @RequestBody Boolean ativo) {
        jogadorService.atualizarAtributoAtivo(id, ativo);
    }


}
