package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-06 16:57:00
 * Comment: 主题推荐
 */

@Data
@Alias("Theme")
public class Theme {
    private Long id;
    private String name;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<Product> products = new ArrayList<>();

    public Theme() {
    }

    public Theme(String name, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
