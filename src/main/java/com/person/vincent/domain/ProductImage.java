package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * Author: vincent
 * Date: 2018-04-10 16:13:00
 * Comment:
 */

@Data
@Alias("ProductImage")
public class ProductImage {
    private Long id;
    private String url;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Product product;

    public ProductImage() {
    }

    public ProductImage(Long id, String url, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
