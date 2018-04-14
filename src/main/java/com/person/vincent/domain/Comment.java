package com.person.vincent.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * Author: vincent
 * Date: 2018-04-05 17:01:00
 * Comment: 产品评论
 */

@Data
@Alias("Comment")
public class Comment {
    private Long id;
    private String content;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Product product;

    public Comment() {
    }

    public Comment(String content, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
