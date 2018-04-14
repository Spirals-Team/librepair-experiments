package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: vincent
 * Date: 2018-04-05 16:47:00
 * Comment: 产品
 */

@Data
@Alias("Product")
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal originalSellingPrice;
    private BigDecimal currentSellingPrice;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Theme theme;
    private Set<Comment> comments = new HashSet<>();
    private Set<ProductColor> colors = new HashSet<>();
    private Set<ProductSpecification> specifications = new HashSet<>();
    private Set<Banner> banners = new HashSet<>();
    private Set<ProductImage> images = new HashSet<>();
    private Set<Tag> tags = new HashSet<>();

    public Product() {
    }

    public Product(String name, String description, BigDecimal originalSellingPrice, BigDecimal currentSellingPrice, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.description = description;
        this.originalSellingPrice = originalSellingPrice;
        this.currentSellingPrice = currentSellingPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
