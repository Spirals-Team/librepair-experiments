package com.prussia.play.spring.repository;

import com.prussia.play.spring.domain.po.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
