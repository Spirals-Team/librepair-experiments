package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * Author: vincent
 * Date: 2018-04-03 13:35:00
 * Comment: 大图
 */

@Data
@Alias("Banner")
public class Banner {
    private Long id;
    private String url;
    private Integer version;
    private Integer category;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Product product;

    public Banner() {
    }

    public Banner(String url, Integer version, Integer category, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.url = url;
        this.version = version;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
