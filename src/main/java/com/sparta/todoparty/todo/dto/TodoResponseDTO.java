package com.sparta.todoparty.todo.dto;

import java.time.LocalDateTime;

import com.sparta.todoparty.todo.entity.Todo;
import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.user.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDTO extends CommonResponseDto {
    private Long id;
    private String title;
    private String content;
    private Boolean isCompleted;
    private UserDTO user;
    private LocalDateTime createDate;

    public TodoResponseDTO(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isCompleted = todo.getIsCompleted();
        this.user = new UserDTO(todo.getUser());
        this.createDate = todo.getCreateDate();
    }
}