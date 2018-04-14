package com.prussia.play.spring.web.controller;

import java.util.List;

import com.prussia.play.spring.domain.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prussia.play.spring.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
  @Autowired
  private ItemService service;
  
  @RequestMapping(method = RequestMethod.GET)
  public List<Item> findItems() {
    return service.findAll();
  }
  
  @RequestMapping(method = RequestMethod.POST)
  public Item addItem(@RequestBody Item item) {
    item.setId(null);
    return service.saveAndFlush(item);
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Item updateItem(@RequestBody Item updatedItem, @PathVariable Integer id) {
    updatedItem.setId(id);
    return service.saveAndFlush(updatedItem);
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void deleteItem(@PathVariable Integer id) {
    service.delete(id);
  }
}
