package com.sparta.todoparty.comment.service;

import com.sparta.todoparty.comment.dto.CommentRequestDTO;
import com.sparta.todoparty.comment.dto.CommentResponseDTO;
import com.sparta.todoparty.comment.entity.Comment;
import com.sparta.todoparty.comment.repository.CommentRepository;
import com.sparta.todoparty.todo.entity.Todo;
import com.sparta.todoparty.todo.repository.TodoRepository;
import com.sparta.todoparty.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public CommentService(CommentRepository commentRepository, TodoRepository todoRepository) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
    }


    public CommentResponseDTO createComment(CommentRequestDTO dto, User user) {
        Todo todo = todoRepository.findById(dto.getTodoId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ToDo 카드입니다"));

        Comment comment = new Comment(dto);
        comment.setUser(user);
        comment.setTodo(todo);

        commentRepository.save(comment);

        return new CommentResponseDTO(comment);
    }

    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO, User user) {
        Comment comment = getUserComment(commentId, user);

        comment.setText(commentRequestDTO.getText());

        return new CommentResponseDTO(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = getUserComment(commentId, user);

        commentRepository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

        if(!user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}