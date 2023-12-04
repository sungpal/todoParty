package com.sparta.todoparty.comment.service;

import com.sparta.todoparty.comment.dto.CommentRequestDTO;
import com.sparta.todoparty.comment.dto.CommentResponseDTO;
import com.sparta.todoparty.comment.entity.Comment;
import com.sparta.todoparty.comment.repository.CommentRepository;
import com.sparta.todoparty.todo.entity.Todo;
import com.sparta.todoparty.todo.repository.TodoRepository;
import com.sparta.todoparty.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    CommentService commentService;

    Todo todo;
    User user;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository, todoRepository);
        user = new User("username", "password");
        user.setId(123L);
        todo = new Todo("제목", "내용", user);
        todo.setId(123L);
    }


    @Test
    void createComment() {
        // Given

        CommentRequestDTO requestDTO = new CommentRequestDTO(todo.getId(),"내용");

        given(todoRepository.findById(todo.getId())).willReturn(Optional.of(todo));

        // When
        CommentResponseDTO result = commentService.createComment(requestDTO, user);

        // Then
        verify(commentRepository, times(1)).save(any(Comment.class));
        assertEquals(user.getUsername(),result.getUser().getUsername());
    }

    @Test
    void updateComment() {
        // Given
        Comment comment = new Comment("내용", user, todo);
        comment.setId(123L);
        CommentRequestDTO requestDTO = new CommentRequestDTO(todo.getId(),"내용");
        requestDTO.setText("수정 내용");
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));

        // When
        CommentResponseDTO result = commentService.updateComment(comment.getId(), requestDTO, user);

        // Then
        assertEquals(123L, result.getId());
        assertEquals("updatecomment", result.getText());
       }

    @Test
    void deleteComment() {
        // Given
        Comment comment = new Comment("내용", user, todo);
        comment.setId(123L);
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));

        // When
        commentService.deleteComment(comment.getId(), user);

        // Then
        verify(commentRepository,times(1)).delete(any(Comment.class));
    }
}
