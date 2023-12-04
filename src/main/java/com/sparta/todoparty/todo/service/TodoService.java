package com.sparta.todoparty.todo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import com.sparta.todoparty.todo.repository.TodoRepository;
import com.sparta.todoparty.todo.dto.TodoRequestDTO;
import com.sparta.todoparty.todo.dto.TodoResponseDTO;
import com.sparta.todoparty.todo.entity.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.todoparty.user.entity.User;
import com.sparta.todoparty.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDTO createTodo(TodoRequestDTO dto, User user) {
        Todo todo = new Todo(dto);
        todo.setUser(user);

        todoRepository.save(todo);

        return new TodoResponseDTO(todo);
    }

    public TodoResponseDTO getTodoDto(Long todoId) {
        Todo todo = getTodo(todoId);
        return new TodoResponseDTO(todo);
    }

    public Map<UserDTO, List<TodoResponseDTO>> getUserTodoMap() {
        Map<UserDTO, List<TodoResponseDTO>> userTodoMap = new HashMap<>();

        List<Todo> todoList = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // 작성일 기준 내림차순

        todoList.forEach(todo -> {
            var userDto = new UserDTO(todo.getUser());
            var todoDto = new TodoResponseDTO(todo);
            if (userTodoMap.containsKey(userDto)) {
                // 유저 할일목록에 항목을 추가
                userTodoMap.get(userDto).add(todoDto);
            } else {
                // 유저 할일목록을 새로 추가
                userTodoMap.put(userDto, new ArrayList<>(List.of(todoDto)));
            }
        });

        return userTodoMap;
    }

    @Transactional
    public TodoResponseDTO updateTodo(Long todoId, TodoRequestDTO todoRequestDTO, User user) {
        Todo todo = getUserTodo(todoId, user);

        todo.setTitle(todoRequestDTO.getTitle());
        todo.setContent(todoRequestDTO.getContent());

        return new TodoResponseDTO(todo);
    }

    @Transactional
    public TodoResponseDTO competeTodo(Long todoId, User user) {
        Todo todo = getUserTodo(todoId, user);

        todo.complete(); // 완료 처리

        return new TodoResponseDTO(todo);
    }

    public Todo getTodo(Long todoId) {

        return todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));
    }

    public Todo getUserTodo(Long todoId, User user) {
        Todo todo = getTodo(todoId);

        if(!user.getId().equals(todo.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return todo;
    }
}