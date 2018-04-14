package com.prussia.play.spring.service;

import java.util.ArrayList;
import java.util.List;

import com.prussia.play.spring.domain.vo.Item;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prussia.play.spring.repository.ItemRepository;
/**
 * to conert po to vo then introduce dozer
 * @author N551JM
 *
 */
@Service
public class ItemServiceBean implements ItemService {
	@Autowired
	private ItemRepository repo;
	

	@Override
	public List<Item> findAll() {
		Mapper mapper = new DozerBeanMapper();
		List<Item> destObject = new ArrayList<Item>();
		mapper.map(repo.findAll(), destObject);
		
		return destObject;
	}

	@Override
	public Item saveAndFlush(Item item) {
		Mapper mapper = new DozerBeanMapper();
		com.prussia.play.spring.domain.po.Item destObject = new com.prussia.play.spring.domain.po.Item();
		mapper.map(item, destObject);
		destObject = repo.saveAndFlush(destObject);
		mapper.map(destObject, item);
		
		return item;
	}

	@Override
	public void delete(Integer id) {
		repo.delete(id);
	}
}
