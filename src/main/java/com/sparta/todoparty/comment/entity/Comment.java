package com.sparta.todoparty.comment.entity;

import com.sparta.todoparty.comment.dto.CommentRequestDTO;
import com.sparta.todoparty.todo.entity.Todo;
import com.sparta.todoparty.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @Column
    private LocalDateTime createDate;

    public Comment(CommentRequestDTO dto) {
        this.text = dto.getText();
        this.createDate = LocalDateTime.now();
    }

    public Comment(String text, User user, Todo todo) {
        this.text = text;
        this.user = user;
        this.todo = todo;
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
        todo.getComments().add(this);
    }

    // 서비스 메서드
    public void setText(String text) {
        this.text = text;
    }
}