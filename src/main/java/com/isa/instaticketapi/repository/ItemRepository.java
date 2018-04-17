package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

	Item findOneById(Long id);
	
	//@Query("SELECT it.fanZone as id FROM Item it where it.fanZone = ?1")
	//ist<Item> findAll();
	
	
}
