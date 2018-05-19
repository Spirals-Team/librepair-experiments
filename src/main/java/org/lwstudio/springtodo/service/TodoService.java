package org.lwstudio.springtodo.service;

import org.lwstudio.springtodo.model.entity.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> getTodosByUserId(Long userId);

    boolean saveTodo(Todo todo);

    Todo getTodoById(Long id);

    boolean modifyTodoDescriptionById(Todo todo);

    Todo completeTodoById(Long id);

    boolean deleteTodoById(Long id);

}
