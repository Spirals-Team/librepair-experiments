package com.prussia.play.spring.service;

import java.util.List;

import com.prussia.play.spring.domain.vo.Item;


public interface ItemService {

	public List<Item> findAll();

	public Item saveAndFlush(Item item);

	public void delete(Integer id);
}
