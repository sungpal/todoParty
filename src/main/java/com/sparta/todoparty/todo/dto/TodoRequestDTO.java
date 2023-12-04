package com.sparta.todoparty.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TodoRequestDTO {
    private String title;
    private String content;
}