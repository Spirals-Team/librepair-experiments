package com.longmaple.ttmall.goodservice.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GoodImgRepo extends CrudRepository<TMallGoodImg, Long> {

	List<TMallGoodImg> findAllByGId(long gId);

}
