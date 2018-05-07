package com.happut.fei.mapper;

import com.happut.fei.pojo.TBlog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogMapper {


    @Select("select * from T_BLOG  order by N_PINNED desc, DT_CREATETIME desc limit 10")
    List<TBlog> getTop10Blogs();

    @Select("select * from T_BLOG where N_ID=#{id}")
    TBlog findBlogById(Integer id);

    @Select("select * from T_BLOG order by N_PINNED desc, DT_CREATETIME desc limit #{pageNo},#{pageSize}")
    List<TBlog> getPagableBlogs(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    @Select("select count(*) from T_BLOG")
    long getBlogsCount();
}
