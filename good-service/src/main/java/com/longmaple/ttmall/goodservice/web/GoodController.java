package com.longmaple.ttmall.goodservice.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.ttmall.goodservice.data.TMallGood;
import com.longmaple.ttmall.goodservice.data.TMallGoodImg;
import com.longmaple.ttmall.goodservice.service.GoodService;

@RestController
@RequestMapping("/good")
public class GoodController {
	
	@Autowired
	private GoodService goodsService;

	/**
	 * 获取单个商品详情
	 * 
	 * @param gId - id of goods
	 * @return map containing product and its photos
	 */
	@GetMapping("/get-good-by-id")
	public Map<String, Object> getGoodsById(long gId) {
		Map<String, Object> goodsMap = new HashMap<String, Object>();
		TMallGood tMallGood = goodsService.findOneById(gId).get();
		List<TMallGoodImg> imgList = goodsService.findImgByGId(gId);
		goodsMap.put("tMallGood", tMallGood);
		goodsMap.put("imageList", imgList);
		return goodsMap;
	}
}
