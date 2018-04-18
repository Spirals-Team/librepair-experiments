package br.com.una.projeto.controller;

import br.com.una.projeto.entity.Categoria;
import br.com.una.projeto.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/listar")
    public List<Categoria> list() {
        return categoriaRepository.findAll();
    }

    @PostMapping("/editar/{categoria}")
    public Categoria editar(@PathVariable("categoria") Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PostMapping("/excluir/{id}")
    public void excluir(@PathVariable("id") Integer id) {
        categoriaRepository.delete(id);
    }

    @PostMapping("/salvar")
    public Categoria salvar(@RequestBody Categoria estado) {
        return categoriaRepository.save(estado);
    }
}
