package com.sparta.todoparty.todo.service;

import com.sparta.todoparty.todo.dto.TodoRequestDTO;
import com.sparta.todoparty.todo.entity.Todo;
import com.sparta.todoparty.todo.repository.TodoRepository;
import com.sparta.todoparty.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void createTodo() {
        // Given
        String title = "제목";
        String content = "내용";
        User user = new User();

        TodoRequestDTO requestDTO = new TodoRequestDTO(title,content);

        // When
        todoService.createTodo(requestDTO, user);

        // Then
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

}
