package com.sparta.todoparty.comment.repository;

import com.sparta.todoparty.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}