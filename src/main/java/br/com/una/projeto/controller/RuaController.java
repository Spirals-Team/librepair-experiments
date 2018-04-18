package br.com.una.projeto.controller;

import br.com.una.projeto.entity.Rua;
import br.com.una.projeto.repository.RuaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rua")
public class RuaController {

    @Autowired
    private RuaRepository ruaRepository;

    @GetMapping("/listar")
    public List<Rua> list() {
        return ruaRepository.findAll();
    }

    @PostMapping("/editar/{rua}")
    public Rua editar(@PathVariable("rua") Rua rua) {
        return ruaRepository.save(rua);
    }

    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") String cep) {
        ruaRepository.delete(cep);
    }

    @PostMapping("/salvar")
    public Rua salvar(@RequestBody Rua rua) {
        return ruaRepository.save(rua);
    }
}
