package com.sparta.todoparty.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import com.sparta.todoparty.todo.dto.TodoListResponseDTO;
import com.sparta.todoparty.todo.dto.TodoRequestDTO;
import com.sparta.todoparty.todo.dto.TodoResponseDTO;
import com.sparta.todoparty.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.user.dto.UserDTO;
import com.sparta.todoparty.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDTO> postTodo(@RequestBody TodoRequestDTO todoRequestDTO,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDTO responseDTO = todoService.createTodo(todoRequestDTO, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId) {
        try {
            TodoResponseDTO responseDTO = todoService.getTodoDto(todoId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDTO>> getTodoList() {
        List<TodoListResponseDTO> response = new ArrayList<>();

        Map<UserDTO, List<TodoResponseDTO>> responseDTOMap = todoService.getUserTodoMap();

        responseDTOMap.forEach((key, value) -> response.add(new TodoListResponseDTO(key, value)));

        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> putTodo(@PathVariable Long todoId,
            @RequestBody TodoRequestDTO todoRequestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDTO responseDTO = todoService.updateTodo(todoId, todoRequestDTO,
                    userDetails.getUser());
            return ResponseEntity.ok().body(responseDTO);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<CommonResponseDto> patchTodo(@PathVariable Long todoId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDTO responseDTO = todoService.competeTodo(todoId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDTO);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}