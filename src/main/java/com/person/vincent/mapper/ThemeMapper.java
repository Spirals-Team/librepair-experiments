package com.person.vincent.mapper;

import com.person.vincent.domain.Theme;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:25:00
 * Comment:
 */

public interface ThemeMapper {
    @Select("select * from theme where status = 0 order by created_at desc")
    @Results(id = "theme", value = {
            @Result(column = "created_at", property = "createdAt", javaType = Timestamp.class),
            @Result(column = "updated_at", property = "updatedAt", javaType = Timestamp.class),
            @Result(column = "id", property = "products", many = @Many(select = "com.person.vincent.mapper.ProductMapper.findByThemeId"))
    })
    List<Theme> all();
}
