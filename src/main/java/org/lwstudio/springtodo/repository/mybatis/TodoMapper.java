package org.lwstudio.springtodo.repository.mybatis;

import org.lwstudio.springtodo.repository.TodoRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper extends TodoRepository {
}
