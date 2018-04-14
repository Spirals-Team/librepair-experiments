package com.person.vincent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * Author: vincent
 * Date: 2018-04-05 16:43:00
 * Comment: 频道
 */

@Data
@Alias("Channel")
public class Channel {
    private Long id;
    private String name;
    private Integer status = 0;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Channel() {
    }

    public Channel(String name, Integer status, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
