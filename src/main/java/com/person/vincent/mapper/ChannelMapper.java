package com.person.vincent.mapper;

import com.person.vincent.domain.Channel;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 14:39:00
 * Comment:
 */
public interface ChannelMapper {

    @Select("select * from channel where status = 0")
    @Results(value = {
            @Result(column = "created_at", property = "createdAt", javaType = Timestamp.class),
            @Result(column = "updated_at", property = "updatedAt", javaType = Timestamp.class)
        }
    )
    List<Channel> all();
}
