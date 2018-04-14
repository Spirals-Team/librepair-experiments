package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: vincent
 * Date: 2018-04-08 11:55:00
 * Comment: 产品标签
 */

@Data
@Alias("Tag")
public class Tag {
    private Long id;
    private String name;
    private String url;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<Product> products = new HashSet<>();

    public Tag() {
    }

    public Tag(String name, String url, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.url = url;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
