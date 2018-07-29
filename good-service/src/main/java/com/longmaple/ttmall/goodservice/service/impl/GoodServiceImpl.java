package com.longmaple.ttmall.goodservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longmaple.ttmall.goodservice.data.GoodImgRepo;
import com.longmaple.ttmall.goodservice.data.GoodRepo;
import com.longmaple.ttmall.goodservice.data.TMallGood;
import com.longmaple.ttmall.goodservice.data.TMallGoodImg;
import com.longmaple.ttmall.goodservice.service.GoodService;

@Service
public class GoodServiceImpl implements GoodService {
	
	@Autowired
	private GoodRepo goodRepo;
	
	@Autowired
	private GoodImgRepo goodImgRepo;

	@Override
	public Optional<TMallGood> findOneById(long gId) {
		return goodRepo.findById(gId);
	}

	@Override
	public List<TMallGoodImg> findImgByGId(long gId) {
		return goodImgRepo.findAllByGId(gId);
	}

}
