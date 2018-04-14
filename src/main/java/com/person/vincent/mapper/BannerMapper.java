package com.person.vincent.mapper;

import com.person.vincent.domain.Banner;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:18:00
 * Comment:
 */
public interface BannerMapper {

    @Select("select * from banner where status = 0 order by created_at desc")
    @Results(value = {
            @Result(column = "created_at", property = "createdAt", javaType = Timestamp.class),
            @Result(column = "updated_at", property = "updatedAt", javaType = Timestamp.class)
        }
    )
    List<Banner> all();
}
