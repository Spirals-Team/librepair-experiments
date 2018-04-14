package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Author: vincent
 * Date: 2018-04-05 20:48:00
 * Comment: 产品颜色
 */

@Data
@Alias("ProductColor")
public class ProductColor {
    private Long id;
    private String name;
    private BigDecimal originalAdditionalPrice;
    private BigDecimal currentAdditionalPrice;
    private Integer stocks = 0;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Product product;

    public ProductColor() {
    }

    public ProductColor(String name, BigDecimal originalAdditionalPrice, BigDecimal currentAdditionalPrice, Integer stocks, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.originalAdditionalPrice = originalAdditionalPrice;
        this.currentAdditionalPrice = currentAdditionalPrice;
        this.stocks = stocks;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
