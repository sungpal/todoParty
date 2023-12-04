package com.sparta.todoparty.comment.dto;

import java.time.LocalDateTime;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.comment.entity.Comment;
import com.sparta.todoparty.user.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDTO extends CommonResponseDto {
    private Long id;
    private String text;
    private UserDTO user;
    private LocalDateTime createDate;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDTO(comment.getUser());
        this.createDate = comment.getCreateDate();
    }
}