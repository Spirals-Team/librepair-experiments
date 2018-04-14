package com.person.vincent.mapper;

import com.person.vincent.domain.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: vincent
 * Date: 2018-04-10 15:20:00
 * Comment:
 */
public interface ProductMapper {

    @Select("select * from product where status = 0 order by created_at desc")
    List<Product> all();

    @Select("select * from product where status = 0 and theme_id = #{theme_id} order by created_at desc")
    List<Product> findByThemeId(@Param("theme_id") Long theme_id);

    @Select("select * from product where status = 0 and theme_id in #{theme_ids} order by created_at desc")
    List<Product> findByThemeIds(@Param("theme_ids") Long[] theme_ids);

    @Select("select * from product where status = 0")
    Product find(@Param("id") Long id);
}
