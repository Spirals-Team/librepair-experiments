package com.tangly.base;

import com.github.pagehelper.PageInfo;
import com.tangly.bean.PageRequest;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 所有的接口继承该类
 *
 * @author Administrator
 * @since JDK 1.7
 */
public interface IBaseInterface<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * @param t
     * @return
     */
    T insert(T t);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param t
     * @return
     */
    T insertSelective(T t);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     * @param t
     * @return
     */
    int delete(T t);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据主键更新属性不为null的值
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);


    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);


    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param t
     * @return
     */
    T selectOne(T t);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     * @param t
     * @return
     */
    int selectCount(T t);

    /**
     * 查询全部结果
     * @return
     */
    List<T> selectAll();

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param pageRequest
     * @return
     */
    PageInfo<T> selectByPage(PageRequest pageRequest);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param t
     * @return
     */
    List<T> select(T t);


    /**
     * 根据Example 查找对象
     * @param e
     * @return
     */
    List<T> selectByExample(Example e);

}
