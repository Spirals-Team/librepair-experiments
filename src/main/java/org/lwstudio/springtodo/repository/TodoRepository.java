package org.lwstudio.springtodo.repository;

import org.lwstudio.springtodo.model.entity.Todo;

import java.util.List;

public interface TodoRepository {

    List<Todo> selectTodosByUserId(Long userId);

    Todo selectTodoById(Long id);

    Integer insertTodo(Todo todo);

    Integer deleteTodosByUserId(Long userId);

    Integer updateTodoOnDescriptionById(Todo todo);

    boolean completeTodoById(Long id);

    Integer deleteTodoById(Long id);

}
