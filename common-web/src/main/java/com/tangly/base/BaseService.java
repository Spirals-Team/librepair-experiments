package com.tangly.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangly.bean.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 通用Service
 *
 * @param <T>
 * @author tangly
 */
public abstract class BaseService<T> implements IBaseInterface<T> {

    @Autowired
    protected Mapper<T> mapper;

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param t
     * @return
     */
    @Override
    public T insert(T t) {
        mapper.insert(t);
        return t;
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param t
     * @return
     */
    @Override
    public T insertSelective(T t) {
        mapper.insertSelective(t);
        return t;
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param t
     * @return
     */
    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param t
     * @return
     */
    @Override
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param t
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param t
     * @return
     */
    @Override
    public T selectOne(T t) {
        return mapper.selectOne(t);
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param t
     * @return
     */
    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    /**
     * 查询全部结果
     *
     * @return
     */
    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param pageRequest
     * @return
     */
    @Override
    public PageInfo<T> selectByPage(PageRequest pageRequest) {

        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Map<String, Object> orderBys = pageRequest.getOrderBys();
        Map<String, Object> searchParams = pageRequest.getSearchParams();

        Example example = new Example(pageRequest.getClazz());

        if (!ObjectUtils.isEmpty(orderBys)) {
            Iterator<Map.Entry<String, Object>> sortIterator = orderBys.entrySet().iterator();
            Map.Entry<String, Object> sortEntry;
            while (sortIterator.hasNext()) {
                sortEntry = sortIterator.next();
                sortEntry.getKey();
                sortEntry.getValue();
                if("asc".equals(sortEntry.getValue())){
                    example.orderBy(sortEntry.getKey()).asc();
                }else if ("desc".equals(sortEntry.getValue())){
                    example.orderBy(sortEntry.getKey()).desc();
                }
            }
        }

        if (!ObjectUtils.isEmpty(searchParams)) {
            Iterator<Map.Entry<String, Object>> searchIterator = searchParams.entrySet().iterator();
            Map.Entry<String, Object> searchEntity;
            while (searchIterator.hasNext()) {
                searchEntity = searchIterator.next();
                example
                        .or()
                        .andLike(searchEntity.getKey(), String.valueOf(searchEntity.getValue()));
            }
        }

        PageHelper.startPage(pageNum, pageSize);

        List<T> list = mapper.selectByExample(example);

        return new PageInfo<T>(list);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param t
     * @return
     */
    @Override
    public List<T> select(T t) {
        return mapper.select(t);
    }

    /**
     * 根据Example 查找对象
     *
     * @param t
     * @return
     */
    @Override
    public List<T> selectByExample(Example e) {
        return mapper.selectByExample(e);
    }

}