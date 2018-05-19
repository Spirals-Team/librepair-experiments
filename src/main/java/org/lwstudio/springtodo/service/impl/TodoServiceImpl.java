package org.lwstudio.springtodo.service.impl;

import org.lwstudio.springtodo.model.entity.Todo;
import org.lwstudio.springtodo.repository.TodoRepository;
import org.lwstudio.springtodo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.selectTodosByUserId(userId);
    }

    @Override
    @Transactional
    public boolean saveTodo(Todo todo) {
        return todoRepository.insertTodo(todo) > 0;
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.selectTodoById(id);
    }

    @Override
    @Transactional
    public boolean modifyTodoDescriptionById(Todo todo) {
        return todoRepository.updateTodoOnDescriptionById(todo) > 0;
    }

    @Override
    @Transactional
    public Todo completeTodoById(Long id) {
        todoRepository.completeTodoById(id);

        return todoRepository.selectTodoById(id);
    }

    @Override
    @Transactional
    public boolean deleteTodoById(Long id) {
        return todoRepository.deleteTodoById(id) > 0;
    }
}
