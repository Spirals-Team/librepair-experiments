package br.com.una.projeto.controller;

import java.util.List;

import br.com.una.projeto.entity.Bairro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.una.projeto.entity.Item;
import br.com.una.projeto.repository.ItemRepository;

@Controller
@RequestMapping("/item")
public class itemController {

	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("/listar")
	public List<Item> list() {
		return itemRepository.findAll();
	}

	@PostMapping("/editar/{item}")
	public Item editar(@PathVariable("item") Item item) {
		return itemRepository.save(item);
	}

	@PostMapping("/excluir/{id}")
	public void excluir(@PathVariable("id") Integer id) {
		itemRepository.delete(id);
	}
	@PostMapping("/salvar")
	public Item salvar(@RequestBody Item item) {
		return itemRepository.save(item);
	}
}
