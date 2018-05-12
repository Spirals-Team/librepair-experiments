package com.tangly.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 * mapper类继承该类实现默认的mapper
 * @author
 * @since 2017-06-26 21:53
 */
public interface BaseMybatisMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}