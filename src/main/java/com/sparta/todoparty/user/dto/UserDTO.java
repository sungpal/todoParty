package com.sparta.todoparty.user.dto;

import com.sparta.todoparty.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;

    public UserDTO(User user) {
        this.username = user.getUsername();
    }
}